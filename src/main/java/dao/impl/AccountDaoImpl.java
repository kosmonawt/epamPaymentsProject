package dao.impl;

import controller.database.DBManager;
import dao.DAO;
import dto.AccountDTO;
import entity.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * Account DAO
 * communication with database
 */

public class AccountDaoImpl implements DAO<AccountDTO> {

    private static final Logger LOGGER = Logger.getLogger(AccountDaoImpl.class.getSimpleName());
    private final DBManager dbManager = DBManager.getInstance();

    @Override
    public List<AccountDTO> getAll() {
        return null;
    }

    @Override
    public AccountDTO getById(Long id) {
        return null;
    }

    @Override
    public AccountDTO getByName(String name) {
        return null;
    }

    @Override
    public void create(AccountDTO accountDTO) {

    }

    @Override
    public void update(AccountDTO accountDTO) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean exist(String name) {
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
}
