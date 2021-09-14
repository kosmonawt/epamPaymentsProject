package dao.impl;

import controller.database.DBManager;
import dao.DAO;
import dto.PaymentDTO;
import entity.Payment;
import exception.DBException;

import java.util.List;
import java.util.logging.Logger;

/**
 * Payment DAO
 * communication with database
 */

public class PaymentDaoImpl implements DAO<PaymentDTO> {

    private static final Logger LOGGER = Logger.getLogger(PaymentDaoImpl.class.getSimpleName());
    DBManager dbManager = DBManager.getInstance();

    @Override
    public List<PaymentDTO> getAll() {
        return null;
    }

    @Override
    public PaymentDTO getById(Long id) {
        return null;
    }

    /**
     * return null because deprecated and can't find payment in database by payment name
     * rudimentary method from unification
     * use methods getPaymentByAccountIdFrom or getPaymentByAccountIdTo
     *
     * @param name
     * @return null
     */
    @Deprecated
    @Override
    public PaymentDTO getByName(String name) {
        return null;
    }

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

    /**
     * Payment can't be updated
     *
     * @param paymentDTO
     */
    @Deprecated
    @Override
    public void update(PaymentDTO paymentDTO) throws DBException {
        throw new DBException("Payment can't be updated");
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


    /**
     * @param id
     * @return payment that was sent from Account id
     */

    private PaymentDTO getPaymentByAccountIdFrom(Long id) {
        try {
            Payment payment;
            PaymentConverter converter = new PaymentConverter();
            payment = dbManager.getPaymentByAccountFromId(id);
            return converter.convertFrom(payment);
        }catch (DBException e){
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param id
     * @return payment that was sent to Account id
     */
    private PaymentDTO getPaymentByAccountIdTo(Long id) {
        //TODO
        return null;
    }


    /**
     * rudimentary method from unification
     * <p>
     * use methods existPaymentByAccountIdFrom() or existPaymentByAccountIdTo()
     *
     * @param name
     * @return false
     */
    @Deprecated
    @Override
    public boolean exist(String name) {
        return false;
    }
}
