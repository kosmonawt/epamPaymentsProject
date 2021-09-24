package dao;

import entity.Payment;
import entity.PaymentStatus;
import entity.Status;

import java.math.BigInteger;
import java.util.List;

public interface PaymentDAO<T> {


    void create(T t);

    void updateStatus(Status status);

    void delete(Long id);

    T getPaymentByAccountNumberFrom(BigInteger accNumber);

    T getPaymentByAccountNumberTo(BigInteger accNumber);

    List<T> getAllPaymentsByUserEmail(String email);

    boolean isPresentInDB(Long accountNumber);

    List<Payment> getAll();

    List<Payment> getAllByPaymentStatus(PaymentStatus status);
}
