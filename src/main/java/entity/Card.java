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

    public Card() {

    }

    public BigInteger getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(BigInteger cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
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
