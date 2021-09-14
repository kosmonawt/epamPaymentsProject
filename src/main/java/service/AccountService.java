package service;

import dao.impl.AccountDaoImpl;
import dto.AccountDTO;
import dto.CardDTO;

import java.util.List;

/**
 * Account service
 */

public class AccountService {
    private static AccountService instance;
    private final AccountDaoImpl accountDao = new AccountDaoImpl();
    private final CardService cardService = CardService.getInstance();

    public static synchronized AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }


    public List<AccountDTO> getAccountsByUserId(Long id) {
        return accountDao.getAccountsByUserId(id);
    }

    public void createAccountForUser(Long userID) {

        AccountDTO accountDTO = new AccountDTO();
        CardDTO cardDTO = cardService.createDefaultCard();
        cardService.save(cardDTO);
        accountDTO.setCardId(cardService.getCardIdByCardNumber(cardDTO.getCardNumber()));

        accountDao.create(accountDTO);

    }
}

