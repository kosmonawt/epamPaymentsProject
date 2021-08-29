package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {

    private long id;
    private Account paymentFromAccount;
    private Account paymentToAccount;
    private LocalDate date;
    private BigDecimal amount;

    public Payment(Account paymentFromAccount, Account paymentToAccount, LocalDate date, BigDecimal amount) {
        this.paymentFromAccount = paymentFromAccount;
        this.paymentToAccount = paymentToAccount;
        this.date = date;
        this.amount = amount;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getPaymentFromAccount() {
        return paymentFromAccount;
    }

    public void setPaymentFromAccount(Account paymentFromAccount) {
        this.paymentFromAccount = paymentFromAccount;
    }

    public Account getPaymentToAccount() {
        return paymentToAccount;
    }

    public void setPaymentToAccount(Account paymentToAccount) {
        this.paymentToAccount = paymentToAccount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Payment{");
        sb.append("id= ").append(id);
        sb.append(", paymentFromAccount= ").append(paymentFromAccount.getId());
        sb.append(", paymentToAccount= ").append(paymentToAccount.getId());
        sb.append(", date= ").append(date.toString());
        sb.append(", amount= ").append(amount.toString());
        sb.append('}');
        return sb.toString();
    }
}
