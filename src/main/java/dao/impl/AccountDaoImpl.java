package dao.impl;

import controller.database.DBManager;
import dao.AccountDAO;
import dto.AccountDTO;
import entity.Account;
import entity.Status;
import exception.DBException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
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
            print(e);
        }

    }

    private <T extends Exception> void print(T e) {
        LOGGER.warn(e.getMessage());
        e.printStackTrace();
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
        List<Account> accounts;
        List<AccountDTO> accountDTOS = new ArrayList<>();
        try {
            accounts = dbManager.getAllUserAccountsById(id);
            for (Account account : accounts) {
                accountDTOS.add(converter.convertFrom(account));
            }
        } catch (DBException e) {
            print(e);
        }
        return accountDTOS;
    }


    public List<AccountDTO> getAccountsByUserEmail(String email) {
        List<AccountDTO> accountDTOS = new ArrayList<>();
        List<Account> accounts;
        AccountConverter converter = new AccountConverter();
        try {
            accounts = dbManager.getAllUserAccountsByEmail(email);
            for (Account account : accounts) {
                accountDTOS.add(converter.convertFrom(account));
            }
        } catch (DBException e) {
            LOGGER.debug("Can't get all users accounts");
            print(e);
        }
        return accountDTOS;
    }

    @Override
    public BigDecimal getAccountAmountByAccountNumber(Long accountNumber) {
        BigDecimal amount = BigDecimal.ZERO;
        try {
            amount = dbManager.getAmountByAccountNumber(accountNumber);
        } catch (DBException e) {
            print(e);
        }
        return amount;
    }

    @Override
    public boolean checkIfUserHaveAccount(String email, Long accountNumber) {
        try {
            List<Account> accounts = dbManager.getAllUserAccountsByEmail(email);
            for (Account account : accounts) {
                if (account.getAccountNumber().longValue() == accountNumber) {
                    return true;
                }
            }
            return false;
        } catch (DBException e) {
            print(e);
            return false;
        }
    }

    @Override
    public boolean checkIfAccountIsBlocked(String email, Long accountNumber) {
        try {
            List<Account> accounts = dbManager.getAllUserAccountsByEmail(email);
            for (Account account : accounts) {
                if (account.getAccountNumber().longValue() == accountNumber) {
                    return account.getStatus().name().equals(Status.BLOCKED.name());
                }
            }
        } catch (DBException e) {
            print(e);
            return true;
        }
        return true;
    }

    @Override
    public void topUpAccount(String email, Long accountNumber, BigDecimal amount) {
        try {
            List<Account> accounts = dbManager.getAllUserAccountsByEmail(email);
            Account accountToUpdate = null;
            for (Account account : accounts) {
                if (account.getAccountNumber().longValue() == accountNumber) {
                    accountToUpdate = account;
                    break;
                }
            }
            if (accountToUpdate != null) {
                BigDecimal newAmount = accountToUpdate.getAmount().add(amount);
                dbManager.updateAccountAmountByAccountNumber(accountToUpdate.getAccountNumber().longValue(), newAmount);
            }

        } catch (DBException e) {
            print(e);
        }
    }
}
