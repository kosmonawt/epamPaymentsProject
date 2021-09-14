package service;

import dao.DAO;
import dao.impl.PaymentDaoImpl;
import dto.PaymentDTO;

public class PaymentService {
    private static  PaymentService instance;
    private final DAO<PaymentDTO> paymentDTO = new PaymentDaoImpl();


    public static synchronized PaymentService getInstance() {
        if (instance == null) {
            instance = new PaymentService();
        }
        return instance;
    }
}
