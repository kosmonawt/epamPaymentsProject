package service;

import dao.PaymentDAO;
import dao.impl.PaymentConverter;
import dao.impl.PaymentDaoImpl;
import dto.PaymentDTO;
import entity.Payment;
import entity.PaymentStatus;
import exception.HaveNotEnoughMoneyException;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {
    private final AccountService accountService = AccountService.getInstance();
    private static PaymentService instance;
    private final PaymentDAO<PaymentDTO> paymentDAO = new PaymentDaoImpl();
    private final Logger logger = Logger.getLogger(PaymentService.class);

    public static synchronized PaymentService getInstance() {
        if (instance == null) {
            instance = new PaymentService();
        }
        return instance;
    }

    public List<PaymentDTO> getAllPaymentsByUserEmail(String email) {
        List<PaymentDTO> payments = new ArrayList<>();
        try {
            payments = paymentDAO.getAllPaymentsByUserEmail(email);
        } catch (NullPointerException e) {
            logger.debug("NPE in getAllPaymentsByUserEmail");
            logger.warn(e.getMessage());
        }

        return payments;
    }

    public void create(@NotNull Long accNumberFrom, @NotNull Long accNumberTo, @NotNull BigDecimal amount, @NotNull String sender, @NotNull String recipient) {

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentNum(VaultService.getNumberFromVault());
        paymentDTO.setPaymentFromAccount(BigInteger.valueOf(accNumberFrom));
        paymentDTO.setPaymentToAccount(BigInteger.valueOf(accNumberTo));
        paymentDTO.setAmount(amount);
        paymentDTO.setPaymentStatus(PaymentStatus.PREPARED.name());
        paymentDTO.setSender(sender);
        paymentDTO.setRecipient(recipient);
        paymentDTO.setDateTime(LocalDateTime.now());

        try {
            if (checkIfOperationPossible(accNumberFrom, amount)) {
                paymentDAO.create(paymentDTO);
            } else throw new HaveNotEnoughMoneyException("Not enough money in the account");

        } catch (HaveNotEnoughMoneyException e) {
            logger.debug("NPE in create");
            logger.warn(e.getMessage());
        }

    }

    /**
     * Check if user have enough money to transfer in account
     *
     * @param accountNumber user account number
     * @param amount        amount to transfer
     * @return true if user hove enough money in account for transfer
     */
    public boolean checkIfOperationPossible(Long accountNumber, BigDecimal amount) {

        if (accountService.getAccountAmountByAccountNumber(accountNumber).signum() > 0) {
            BigDecimal amountFromAccount = accountService.getAccountAmountByAccountNumber(accountNumber);
            BigDecimal result = amountFromAccount.subtract(amount);
            logger.debug("amount from account number " + accountNumber + " is " + result);
            return result.signum() > 0;
        }
        return false;
    }

    public boolean isAccPresentInDB(Long accNumberTo) {
        return paymentDAO.isPresentInDB(accNumberTo);
    }

    public List<PaymentDTO> getAllPaymentsWithStatusSend() {
        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        List<Payment> payments = paymentDAO.getAllByPaymentStatus(PaymentStatus.SEND);
        PaymentConverter converter = new PaymentConverter();
        if (payments != null && !payments.isEmpty()) {
            for (Payment payment : payments) {
                paymentDTOs.add(converter.convertFrom(payment));
            }
            return paymentDTOs;
        }
        return paymentDTOs;
    }
}
