package service;

import dto.AccountDTO;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class AccountServiceTest {

    final AccountService accountService = AccountService.getInstance();


    @Test
    public void createAccountForUserWithFixedCurrencyTest() {

        String email = "c@c.c";
        String currency = "UAH";
        int sizeBefore = accountService.getAccountsByUserEmail(email).size();
        accountService.createAccountForUser(email, currency);
        int sizeAfter = accountService.getAccountsByUserEmail(email).size();
        assertEquals(sizeAfter - sizeBefore, 1);

    }

    @Test
    public void getAccountByAccount() {

        long accountNumber = 25L;
        AccountDTO account = accountService.getAccountByAccountNumber(BigInteger.valueOf(accountNumber));
        assertSame(accountNumber, account.getAccountNumber());

    }


}
