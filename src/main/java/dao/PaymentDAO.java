package dao;

import entity.Status;

import java.math.BigInteger;

public interface PaymentDAO<T> {


    void create(T t);

    void updateStatus(Status status);

    void delete(Long id);

    T getPaymentByAccountNumberFrom(BigInteger accNumber);

    T getPaymentByAccountNumberTo(BigInteger accNumber);

}
