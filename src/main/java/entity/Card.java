package entity;

import java.math.BigInteger;
import java.time.LocalDate;

public class Card extends Entity {

    private static final long serialVersionUID = 2386302708905518585L;

    private BigInteger cardNumber;
    private Integer pin;
    private Integer cvv;
    private LocalDate expiryDate;
    private CardType cardType;

    private Card(CardBuilder cardBuilder) {
        this.cardNumber = cardBuilder.cardNumber;
        this.pin = cardBuilder.pin;
        this.cvv = cardBuilder.cvv;
        this.expiryDate = cardBuilder.expiryDate;
        this.cardType = cardBuilder.cardType;
    }

    public static class CardBuilder {
        private BigInteger cardNumber;
        private Integer pin;
        private Integer cvv;
        private LocalDate expiryDate;
        private CardType cardType;

        public CardBuilder() {
        }

        public CardBuilder addCardNumber(BigInteger cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public CardBuilder addPin(Integer pin) {
            this.pin = pin;
            return this;
        }

        public CardBuilder addCVV(Integer cvv) {
            this.cvv = cvv;
            return this;
        }

        public CardBuilder addExpiryDate(LocalDate expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public CardBuilder addCardType(CardType cardType) {
            this.cardType = cardType;
            return this;
        }

        public Card build() {
            return new Card(this);
        }
    }

    public BigInteger getCardNumber() {
        return cardNumber;
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
        sb.append(", cardNumber=").append(cardNumber);
        sb.append(", expiryDate=").append(expiryDate);
        sb.append(", cardType=").append(cardType);
        sb.append('}');
        return sb.toString();
    }
}
