package dao.impl;

import controller.database.DBManager;
import dao.PaymentDAO;
import dto.PaymentDTO;
import entity.Payment;
import entity.Status;
import exception.DBException;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * Payment DAO
 * communication with database
 */

public class PaymentDaoImpl implements PaymentDAO<PaymentDTO> {

    private static final Logger LOGGER = Logger.getLogger(PaymentDaoImpl.class);
    DBManager dbManager = DBManager.getInstance();


    @Override
    public void create(PaymentDTO paymentDTO) {
        PaymentConverter paymentConverter = new PaymentConverter();
        try {
            dbManager.createPayment(paymentConverter.convertTo(paymentDTO));
        } catch (DBException e) {
            print(e);
        }
    }

    private <T extends Exception> void print(T e) {
        LOGGER.warn(e.getMessage());
        e.printStackTrace();
    }

    @Override
    public void updateStatus(Status status) {

    }

    @Override
    public void delete(Long id) {
        try {
            dbManager.deletePayment(id);
        } catch (DBException e) {
            print(e);
        }
    }


    @Override
    public PaymentDTO getPaymentByAccountNumberFrom(BigInteger accNum) {
        try {
            Payment payment;
            PaymentConverter converter = new PaymentConverter();
            payment = dbManager.getPaymentByAccountFromId(accNum);
            return converter.convertFrom(payment);
        } catch (DBException e) {
            print(e);
        }
        return null;
    }


    @Override
    public PaymentDTO getPaymentByAccountNumberTo(BigInteger accNumber) {
        return null;
    }

    @Override
    public List<PaymentDTO> getAllPaymentsByUserEmail(String email) {
        List<PaymentDTO> paymentDTOs = new ArrayList<>();
        PaymentConverter converter = new PaymentConverter();
        List<Payment> payments;
        try {
            payments = dbManager.getAllPaymentsByUserEmail(email);
            for (Payment payment : payments) {
                paymentDTOs.add(converter.convertFrom(payment));
            }
        } catch (DBException e) {
            print(e);
        }
        return paymentDTOs;
    }
}
