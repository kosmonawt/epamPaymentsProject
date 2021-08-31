package entity;

import java.math.BigDecimal;
import java.util.Currency;

public class Account {
    private final BigDecimal INIT_AMOUNT_CAPACITY = BigDecimal.ZERO;

    private long id;
    private User accountHolder;
    private Card card;
    private BigDecimal amount;
    private Currency currency;
    private Status status;

    public Account(Card card, Currency currency) {
        this.card = card;
        this.currency = currency;
        this.amount = INIT_AMOUNT_CAPACITY;
        this.status = Status.PENDING;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(User accountHolder) {
        this.accountHolder = accountHolder;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
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


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("id=").append(id);
        sb.append(", card=").append(card.toString());
        sb.append(", amount=").append(amount.toString());
        sb.append(", currency=").append(currency.toString());
        sb.append(", status=").append(status.toString());
        sb.append('}');
        return sb.toString();
    }
}