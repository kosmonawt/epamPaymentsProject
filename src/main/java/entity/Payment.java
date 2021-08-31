package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {

    private long id;
    private Account paymentFromAccount;
    private Account paymentToAccount;
    private LocalDateTime dateTime;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;

    public Payment() {
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Payment{");
        sb.append("id= ").append(id);
        sb.append(", paymentFromAccount= ").append(paymentFromAccount.getId());
        sb.append(", paymentToAccount= ").append(paymentToAccount.getId());
        sb.append(", date= ").append(dateTime.toString());
        sb.append(", amount= ").append(amount.toString());
        sb.append('}');
        return sb.toString();
    }
}