package service;

import dao.AccountDAO;
import dao.impl.AccountDaoImpl;
import dto.AccountDTO;
import dto.CardDTO;
import entity.Currency;
import entity.Status;

import java.math.BigDecimal;
import java.util.List;

/**
 * Account service
 */

public class AccountService {
    private static AccountService instance;
    private final AccountDAO<AccountDTO> accountDao = new AccountDaoImpl();
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

    public List<AccountDTO> getAccountsByUserEmail(String email) {
        return accountDao.getAccountsByUserEmail(email);
    }


    /**
     * Create account for user with selected currency
     *
     * @param userLogin - user that account created for
     * @param currency  - currency to account
     */

    public void createAccountForUser(String userLogin, String currency) {
        CardDTO cardDTO = cardService.createDefaultCard();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserLogin(userLogin);
        accountDTO.setAccountNumber(VaultService.getNumberFromVault().longValue());
        accountDTO.setAmount(BigDecimal.ZERO);
        accountDTO.setCurrency(Currency.valueOf(currency.trim().toUpperCase()));
        accountDTO.setStatus(Status.ACTIVE.name());
        cardDTO.setAccountNum(accountDTO.getAccountNumber());
        accountDao.create(accountDTO);
        cardService.save(cardDTO);
//        cardDTO = cardService.getCardByCardNumber(cardDTO.getCardNumber());

    }

    public BigDecimal getAccountAmountByAccountNumber(Long accountNumber) {
        return accountDao.getAccountAmountByAccountNumber(accountNumber);
    }

    public boolean checkIfUserHaveAccount(String email, Long accountNumber) {
        return accountDao.checkIfUserHaveAccount(email, accountNumber);
    }

    public boolean checkIfAccountIsBlocked(String email, Long accountNumber) {
        return accountDao.checkIfAccountIsBlocked(email, accountNumber);
    }

    public void topUpAccount(String email, Long accountNumber, BigDecimal amount) {
        accountDao.topUpAccount(email, accountNumber,amount);
    }
}

