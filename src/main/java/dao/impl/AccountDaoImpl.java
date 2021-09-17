package dao.impl;

import dao.DAO;
import entity.Account;

import java.util.List;

public class AccountDaoImpl implements DAO<Account> {
    @Override
    public List<Account> getAll() {
        return null;
    }

    @Override
    public Account getById(Long id) {
        return null;
    }

    @Deprecated
    @Override
    public Account getByName(String name) {
        return null;
    }

    @Override
    public void create(Account account) {

    }

    @Override
    public void update(Account account) {

    }

    @Override
    public void delete(Long id) {

    }

    @Deprecated
    @Override
    public boolean exist(String name) {
        return false;
    }
}
