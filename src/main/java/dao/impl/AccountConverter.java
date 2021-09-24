package dao.impl;

import dao.DaoConverter;
import dto.AccountDTO;
import entity.Account;
import entity.Status;

import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Convert Account entity to DTO and back
 */
public class AccountConverter implements DaoConverter<AccountDTO, Account> {


    @Override
    public Account convertTo(AccountDTO accountDTO) {

        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setAccountNumber(BigInteger.valueOf(accountDTO.getAccountNumber()));
        account.setUserLogin(accountDTO.getUserLogin());
        account.setAmount(accountDTO.getAmount().setScale(2, RoundingMode.HALF_UP));
        account.setCurrency(accountDTO.getCurrency());
        account.setStatus(Status.valueOf(accountDTO.getStatus().trim().toUpperCase()));
        return account;
    }

    @Override
    public AccountDTO convertFrom(Account account) {

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setAccountNumber(account.getAccountNumber().longValue());
        accountDTO.setUserLogin(account.getUserLogin());
        accountDTO.setCurrency(account.getCurrency());
        accountDTO.setAmount(account.getAmount().setScale(2, RoundingMode.HALF_UP));
        accountDTO.setStatus(account.getStatus().name());
        return accountDTO;
    }
}
