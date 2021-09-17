package dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentDTO {
    private Long id;
    private Long paymentFromAccount;
    private Long paymentToAccount;
    private LocalDateTime dateTime;
    private BigDecimal amount;
    private String paymentStatus;

    public PaymentDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaymentFromAccount() {
        return paymentFromAccount;
    }

    public void setPaymentFromAccount(Long paymentFromAccount) {
        this.paymentFromAccount = paymentFromAccount;
    }

    public Long getPaymentToAccount() {
        return paymentToAccount;
    }

    public void setPaymentToAccount(Long paymentToAccount) {
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

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
