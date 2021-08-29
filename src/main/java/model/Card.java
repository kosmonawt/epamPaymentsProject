package controller.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

public class Card {
    private long id;
    private User cardHolder;
    private BigInteger cardNumber;
    private BigDecimal amount;
    private Integer pin;
    private Integer cvv;
    private LocalDate expiryDate;
    private CardType cardType;

    private Card(CardBuilder cardBuilder) {
        this.cardHolder = cardBuilder.cardHolder;
        this.cardNumber = cardBuilder.cardNumber;
        this.amount = cardBuilder.amount;
        this.pin = cardBuilder.pin;
        this.cvv = cardBuilder.cvv;
        this.expiryDate = cardBuilder.expiryDate;
        this.cardType = cardBuilder.cardType;
    }

    public static class CardBuilder {
        private User cardHolder;
        private BigInteger cardNumber;
        private BigDecimal amount;
        private Integer pin;
        private Integer cvv;
        private LocalDate expiryDate;
        private CardType cardType;

        public CardBuilder(){}

        public CardBuilder addCardHolder(User cardHolder){
            this.cardHolder = cardHolder;
            return this;
        }
        public  CardBuilder addCardNumber(BigInteger cardNumber){
            this.cardNumber = cardNumber;
            return this;
        }
        public CardBuilder addAmount(BigDecimal amount){
            this.amount = amount;
            return this;
        }
        public CardBuilder addPin(Integer pin){
            this.pin = pin;
            return this;
        }
        public CardBuilder addCVV(Integer cvv){
            this.cvv = cvv;
            return this;
        }
        public CardBuilder addExpiryDate(LocalDate expiryDate){
            this.expiryDate = expiryDate;
            return this;
        }
        public CardBuilder addCardType(CardType cardType){
            this.cardType = cardType;
            return this;
        }

        public Card build(){
            return new Card(this);
        }
    }

    public long getId() {
        return id;
    }

    public User getCardHolder() {
        return cardHolder;
    }

    public BigInteger getCardNumber() {
        return cardNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getPin() {
        return pin;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public CardType getCardType() {
        return cardType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Card{");
        sb.append(", cardHolder=").append(cardHolder);
        sb.append(", cardNumber=").append(cardNumber);
        sb.append(", expiryDate=").append(expiryDate);
        sb.append(", cardType=").append(cardType);
        sb.append('}');
        return sb.toString();
    }
}
