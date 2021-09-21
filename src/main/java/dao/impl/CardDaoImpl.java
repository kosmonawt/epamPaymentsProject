package dao.impl;

import controller.database.DBManager;
import dao.CardDAO;
import dto.CardDTO;
import entity.Card;
import exception.DBException;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Card Data Access Object
 * communication with database
 */

public class CardDaoImpl implements CardDAO<CardDTO> {
    private static final Logger LOGGER = Logger.getLogger(CardDaoImpl.class);
    DBManager dbManager = DBManager.getInstance();


    @Override
    public List<CardDTO> getAllCardsByAccountNumber(@NotNull Long accNumber) {
        CardDaoConverter converter = new CardDaoConverter();
        List<Card> cards;
        List<CardDTO> cardDTOs = new ArrayList<>();
        try {
            cards = dbManager.getAllCardsByAccountNumber(accNumber);
            for (Card card : cards) {
                cardDTOs.add(converter.convertFrom(card));
            }
        } catch (DBException e) {
            print(e);
        }
        return cardDTOs;
    }

    private <T extends Exception> void print(T e) {
        LOGGER.warn(e.getMessage());
        e.printStackTrace();
    }

    @Override
    public CardDTO getCardById(Long id) {

        CardDTO cardDTO = new CardDTO();
        try {
            CardDaoConverter converter = new CardDaoConverter();
            Card card = dbManager.getCardById(id);
            return converter.convertFrom(card);
        } catch (DBException e) {
            print(e);

        }
        return cardDTO;
    }

    @Override
    public CardDTO getByCardNumber(String cardNumber) {
        CardDTO cardDTO = new CardDTO();
        try {
            CardDaoConverter converter = new CardDaoConverter();
            Card card = dbManager.getCardByCardNumber(cardNumber);
            if (card != null) {
                return converter.convertFrom(card);
            }

        } catch (DBException e) {
            print(e);

        }
        return cardDTO;
    }

    /**
     * convert card from card DTO and save in database
     *
     * @param cardDTO
     */
    @Override
    public void create(CardDTO cardDTO) {
        CardDaoConverter converter = new CardDaoConverter();
        try {
            dbManager.createCard(converter.convertTo(cardDTO));
        } catch (DBException e) {
            print(e);
        }
    }

    @Override
    public void update(CardDTO cardDTO) {

        try {
            CardDaoConverter converter = new CardDaoConverter();
            dbManager.updateCard(converter.convertTo(cardDTO));
        } catch (DBException e) {
            print(e);
        }

    }

    @Override
    public void delete(Long id) {
        try {
            dbManager.deleteCard(id);
        } catch (DBException e) {
            print(e);
        }
    }

    /**
     *
     */
    @Override
    public boolean existByCardNumber(String cardNumber) {
        //TODO
        return false;
    }
}
