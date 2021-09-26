package dao.impl;

import controller.database.DBManager;
import dao.AccountDAO;
import dto.AccountDTO;
import entity.Account;
import entity.Status;
import exception.DBException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
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
        AccountConverter converter = new AccountConverter();
        try {
            dbManager.updateAccount(converter.convertTo(accountDTO));
        } catch (DBException e) {
            LOGGER.warn("Update account is unsuccessful");
            print(e);
        }

    }

    @Override
    public void delete(Long id) {
//TODO
    }

    @Override
    public void get(Long id) {
//TODO
    }

    @Override
    public boolean existByAccountNumber(Long accNum) {
        //TODO
        return false;
    }

    /**
     * @param id user ID
     * @return all accounts that have user with id
     */
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

    /**
     * @param email user email
     * @return all accounts that have user with email
     */

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

    /**
     * @param accountNumber account number
     * @return amount in account by account number
     */

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

    /**
     * @param email         user email
     * @param accountNumber account number
     * @return true if user with email own this account number
     */

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

    /**
     * @param email         user email
     * @param accountNumber account number
     * @return return true if account is BLOCKED and true when get exception from DB
     */
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

    @Override
    public boolean checkIfAccountIsBlocked(Long accountNumber) {
        try {
            Account account = dbManager.getAccountByAccountNumber(accountNumber);
            return account.getStatus().name().equalsIgnoreCase(Status.BLOCKED.name());

        } catch (DBException e) {
            print(e);
            return true;
        }
    }

    @Override
    public boolean isCurrenciesEquals(Long accNumberFrom, Long accNumberTo) {
        try {
            Account accountFrom = dbManager.getAccountByAccountNumber(accNumberFrom);
            Account accountTo = dbManager.getAccountByAccountNumber(accNumberTo);
            return accountFrom.getCurrency().name().equalsIgnoreCase(accountTo.getCurrency().name());

        } catch (DBException e) {
            print(e);
            return false;
        }
    }

    @Override
    public Account getAccountByAccountNumber(BigInteger accountNumber) {
        Account account = new Account();
        try {
            account = dbManager.getAccountByAccountNumber(accountNumber.longValue());
        } catch (DBException e) {
            print(e);
        }
        return account;
    }
}
