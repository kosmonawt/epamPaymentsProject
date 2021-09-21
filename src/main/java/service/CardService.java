package service;

import dao.CardDAO;
import dao.impl.CardDaoImpl;
import dto.CardDTO;
import entity.CardType;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Card service
 */
public class CardService {
    private final Logger logger = Logger.getLogger(CardService.class);
    private static CardService instance;
    private final CardDAO<CardDTO> cardDao = new CardDaoImpl();

    public static synchronized CardService getInstance() {
        if (instance == null) {
            instance = new CardService();
        }
        return instance;
    }

    public void save(CardDTO cardDTO) {
        cardDao.create(cardDTO);
    }

    public CardDTO getCardByCardNumber(@NotNull BigInteger cardNumber) {
        return cardDao.getByCardNumber(cardNumber.toString());
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

    private @NotNull String cardExpiryDateFromNow() {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusYears(4);
        return localDate.toString();

    }

    private @NotNull Integer generateCvv() {
        return ThreadLocalRandom.current().nextInt(100, 999);
    }

    private @NotNull Integer generatePin() {
        return ThreadLocalRandom.current().nextInt(1000, 9999);
    }

    private @NotNull BigInteger generateRandomCardNumber() {
        long smallest = 1000_0000_0000_0000L;
        long biggest = 9999_9999_9999_9999L;
        long random = ThreadLocalRandom.current().nextLong(smallest, biggest);
        return BigInteger.valueOf(random);
    }

    public Long getCardIdByCardNumber(BigInteger cardNumber) {
        CardDTO cardDTO = new CardDTO();
        try {
            cardDTO = getCardByCardNumber(cardNumber);
        } catch (NullPointerException e) {
            logger.debug("NPE in getCardIdByCardNumber method ");
            logger.warn(e.getMessage());
        }
        return cardDTO.getId();
    }

    public CardDTO getCardByCardId(Long cardId) {

        return cardDao.getCardById(cardId);
    }

    public List<CardDTO> getCardsByAccountNumber(@NotNull Long accNumber) {
        List<CardDTO> cards = new ArrayList<>();
        try {
            cards = cardDao.getAllCardsByAccountNumber(accNumber);
        } catch (NullPointerException e) {
            logger.debug("NPE in getCardsByAccountNumber");
            logger.warn(e.getMessage());
        }
        return cards;
    }

    public void createCardForAccount(Long accNumber) {
        CardDTO cardDTO = createDefaultCard();
        cardDTO.setAccountNum(accNumber);
        save(cardDTO);
    }

    public void createCardForAccount(Long accNumber, String cardType) {
        CardType type = Arrays.stream(CardType.values()).filter(x -> x.name().equals(cardType))
                .findFirst()
                .orElse(CardType.VISA);
        CardDTO cardDTO = createDefaultCard();
        cardDTO.setAccountNum(accNumber);
        cardDTO.setCardType(type.name());
        save(cardDTO);
    }
}
