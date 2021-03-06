package dao;

import entity.Account;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface AccountDAO<T> {

    void create(T t);

    void update(T t);

    void delete(Long id);

    void get(Long id);

    boolean existByAccountNumber(Long accNum);

    List<T> getAccountsByUserId(Long id);

    List<T> getAccountsByUserEmail(String email);

    BigDecimal getAccountAmountByAccountNumber(Long accountNumber);

    boolean checkIfUserHaveAccount(String email, Long accountNumber);

    boolean checkIfAccountIsBlocked(String email, Long accountNumber);

    void topUpAccount(String email, Long accountNumber, BigDecimal amount);

    boolean checkIfAccountIsBlocked(Long accountNumber);

    boolean isCurrenciesEquals(Long accNumberFrom, Long accNumberTo);

    Account getAccountByAccountNumber(BigInteger accountNumber);
}
