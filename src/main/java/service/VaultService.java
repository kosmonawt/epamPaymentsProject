package service;

import controller.database.DBManager;

import java.math.BigInteger;

public class VaultService {
    private final static DBManager dbmanager = DBManager.getInstance();

    public static synchronized BigInteger getNumberFromVault() {
        return dbmanager.getNumberFromVault();
    }
}
