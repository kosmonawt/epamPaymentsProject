package dao.impl;

import dao.DaoConverter;
import dto.PaymentDTO;
import entity.Account;
import entity.Payment;
import entity.PaymentStatus;

/**
 * Convert Payment DAO to DTO and back
 */

public class PaymentConverter implements DaoConverter<PaymentDTO, Payment> {
    private Account from = new Account();
    private Account to = new Account();

    @Override
    public Payment convertTo(PaymentDTO paymentDTO) {
        from.setId(paymentDTO.getPaymentFromAccount());
        to.setId(paymentDTO.getPaymentToAccount());
        Payment payment = new Payment();
        payment.setId(paymentDTO.getId());
        payment.setPaymentFromAccount(from);
        payment.setPaymentToAccount(to);
        payment.setAmount(paymentDTO.getAmount());
        payment.setDateTime(paymentDTO.getDateTime());
        payment.setPaymentStatus(PaymentStatus.valueOf(paymentDTO.getPaymentStatus().trim().toUpperCase()));
        return payment;
    }

    @Override
    public PaymentDTO convertFrom(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(paymentDTO.getId());
        paymentDTO.setPaymentFromAccount(payment.getPaymentFromAccount().getId());
        paymentDTO.setPaymentToAccount(payment.getPaymentToAccount().getId());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setDateTime(payment.getDateTime());
        paymentDTO.setPaymentStatus(payment.getPaymentStatus().name());
        return paymentDTO;
    }
}
