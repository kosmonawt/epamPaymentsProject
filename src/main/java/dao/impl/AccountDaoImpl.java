package dao.impl;

import controller.database.DBManager;
import dao.AccountDAO;
import dto.AccountDTO;
import entity.Account;
import exception.DBException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Account DAO
 * communication with database
 */

public class AccountDaoImpl implements AccountDAO<AccountDTO> {

    private static final Logger LOGGER = Logger.getLogger(AccountDaoImpl.class.getSimpleName());
    private final DBManager dbManager = DBManager.getInstance();



    @Override
    public void create(AccountDTO accountDTO) {

        AccountConverter converter = new AccountConverter();
        try {
            dbManager.createAccount(converter.convertTo(accountDTO));
        } catch (DBException e) {
            LOGGER.debug("Create account exception");
            LOGGER.warn(e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void update(AccountDTO accountDTO) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void get(Long id) {

    }

    @Override
    public boolean existByAccountNumber(Long accNum) {
        return false;
    }

    public List<AccountDTO> getAccountsByUserId(Long id) {
        AccountConverter converter = new AccountConverter();
        List<Account> accounts = dbManager.getAllUserAccountsById(id);
        List<AccountDTO> accountDTOS = new ArrayList<>();
        while (accounts.iterator().hasNext()) {
            accountDTOS.add(converter.convertFrom(accounts.iterator().next()));
        }
        return accountDTOS;
    }


    public List<AccountDTO> getAccountsByUserEmail(String email) {
        List<AccountDTO> accountDTOS = new ArrayList<>();
        List<Account> accounts;
        AccountConverter converter = new AccountConverter();
        try {
            accounts = dbManager.getAllUserAccountsByEmail(email);
            for (int i = 0; i < accounts.size(); i++) {
                accountDTOS.add(converter.convertFrom(accounts.get(i)));
            }
        } catch (DBException e) {
            LOGGER.debug("Can't get all users accounts");
            LOGGER.warn(e.getMessage());
            e.printStackTrace();
        }
        return accountDTOS;
    }
}
