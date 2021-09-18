package entity;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Account extends Entity {
    private static final long serialVersionUID = 4716395168539434663L;
    private final BigDecimal INIT_AMOUNT_CAPACITY = BigDecimal.ZERO;
    private BigInteger accountNumber;
    private String userLogin;
    private BigDecimal amount;
    private Currency currency;
    private Status status;

    public Account() {
    }

    public Account(Currency currency) {
        this.currency = currency;
        this.amount = INIT_AMOUNT_CAPACITY;
        this.status = Status.PENDING;
    }

    public BigInteger getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(BigInteger accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) >= 0) {
            this.amount = amount;
        }
    }

}
