package dao.impl;

import controller.database.DBManager;
import dao.PaymentDAO;
import dto.PaymentDTO;
import entity.Payment;
import entity.Status;
import exception.DBException;

import java.math.BigInteger;
import java.util.logging.Logger;

/**
 * Payment DAO
 * communication with database
 */

public class PaymentDaoImpl implements PaymentDAO<PaymentDTO> {

    private static final Logger LOGGER = Logger.getLogger(PaymentDaoImpl.class.getSimpleName());
    DBManager dbManager = DBManager.getInstance();


    @Override
    public void create(PaymentDTO paymentDTO) {
        PaymentConverter paymentConverter = new PaymentConverter();
        try {
            dbManager.createPayment(paymentConverter.convertTo(paymentDTO));
        } catch (DBException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatus(Status status) {

    }

    @Override
    public void delete(Long id) {
        try {
            dbManager.deletePayment(id);
        } catch (DBException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
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
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public PaymentDTO getPaymentByAccountNumberTo(BigInteger accNumber) {
        return null;
    }
}
