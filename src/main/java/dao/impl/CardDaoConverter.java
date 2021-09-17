package dao.impl;

import dao.DaoConverter;
import dto.CardDTO;
import entity.Card;
import entity.CardType;

import java.time.LocalDate;


public class CardDaoConverter implements DaoConverter<CardDTO, Card> {
    @Override
    public Card convertTo(CardDTO cardDTO) {
        Card card = new Card();
        card.setId(cardDTO.getId());
        card.setCardNumber(cardDTO.getCardNumber());
        card.setPin(cardDTO.getPin());
        card.setCvv(cardDTO.getCvv());
        card.setExpiryDate(LocalDate.parse(cardDTO.getExpiryDate()));
        card.setCardType(CardType.valueOf(cardDTO.getCardType().trim().toUpperCase()));
        return card;
    }

    @Override
    public CardDTO convertFrom(Card card) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(card.getCardNumber());
        cardDTO.setPin(cardDTO.getPin());
        cardDTO.setCvv(card.getCvv());
        cardDTO.setExpiryDate(card.getExpiryDate().toString());
        cardDTO.setCardType(card.getCardType().name());
        return cardDTO;
    }

}
