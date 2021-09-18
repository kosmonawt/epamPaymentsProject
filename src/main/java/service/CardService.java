package service;

import dao.DAO;
import dao.impl.CardDaoImpl;
import dto.CardDTO;
import entity.CardType;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Card service
 */
public class CardService {

    private static CardService instance;
    private final DAO<CardDTO> cardDao = new CardDaoImpl();

    public static synchronized CardService getInstance() {
        if (instance == null) {
            instance = new CardService();
        }
        return instance;
    }

    public void save(CardDTO cardDTO) {
        cardDao.create(cardDTO);
    }

    public CardDTO getCardByCardNumber(BigInteger cardNumber) {
        return cardDao.getByName(cardNumber.toString());
    }

    /**
     * DEFAULT CARD TYPE IS VISA, you can change type in user menu
     *
     * @return default card when user create account
     */
    public CardDTO createDefaultCard() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(generateRandomCardNumber());
        cardDTO.setPin(generatePin());
        cardDTO.setCvv(generateCvv());
        cardDTO.setExpiryDate(cardExpiryDateFromNow());
        cardDTO.setCardType(CardType.VISA.name());
        return cardDTO;
    }

    private String cardExpiryDateFromNow() {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusYears(4);
        return localDate.toString();

    }

    private Integer generateCvv() {
        return ThreadLocalRandom.current().nextInt(100,999);
    }

    private Integer generatePin() {
        return ThreadLocalRandom.current().nextInt(1000,9999);
    }

    private BigInteger generateRandomCardNumber() {
        long smallest = 1000_0000_0000_0000L;
        long biggest = 9999_9999_9999_9999L;
        long random = ThreadLocalRandom.current().nextLong(smallest, biggest);
        return BigInteger.valueOf(random);
    }

    public Long getCardIdByCardNumber(BigInteger cardNumber) {
        CardDTO cardDTO = getCardByCardNumber(cardNumber);
        return cardDTO.getId();
    }

    public CardDTO getCardByCardId(Long cardId) {

        return cardDao.getById(cardId);
    }
}
