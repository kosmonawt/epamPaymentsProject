package dao.impl;

import controller.database.DBManager;
import dao.DAO;
import dto.CardDTO;
import entity.Card;
import exception.DBException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Card Data Access Object
 * communication with database
 */

public class CardDaoImpl implements DAO<CardDTO> {
    private static final Logger LOGGER = Logger.getLogger(CardDaoImpl.class.getSimpleName());
    DBManager dbManager = DBManager.getInstance();

    @Override
    public List<CardDTO> getAll() {
        List<CardDTO> cardDTO = new ArrayList<>();
        List<Card> cards = new ArrayList<>();
        try {
            cards = dbManager.getAllCards();
            return cardDTO;
        } catch (DBException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public CardDTO getById(Long id) {

        CardDTO cardDTO = new CardDTO();
        try {
            CardDaoConverter converter = new CardDaoConverter();
            Card card = dbManager.getCardById(id);
            return converter.convertFrom(card);
        } catch (DBException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();

        }
        return cardDTO;
    }

    @Override
    public CardDTO getByName(String cardNumber) {
        CardDTO cardDTO = new CardDTO();
        try {
            CardDaoConverter converter = new CardDaoConverter();
            Card card = dbManager.getCardByCardNumber(cardNumber);
            if (card != null) {
                return converter.convertFrom(card);
            }

        } catch (DBException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();

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
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void update(CardDTO cardDTO) {

        try {
            CardDaoConverter converter = new CardDaoConverter();
            dbManager.updateCard(converter.convertTo(cardDTO));
        } catch (DBException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Long id) {
        try {
            dbManager.deleteCard(id);
        } catch (DBException e) {
            LOGGER.warning(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @Override
    public boolean exist(String cardNumber) {
        //TODO
        return false;
    }
}
