package dao.impl;

import dao.DaoConverter;
import dto.AccountDTO;
import entity.Account;

public class AccountConverter implements DaoConverter<AccountDTO, Account> {
    @Override
    public Account convertTo(AccountDTO accountDTO) {
        return null;
    }

    @Override
    public AccountDTO convertFrom(Account account) {
        return null;
    }
}
