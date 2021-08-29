package model;

import javax.security.enterprise.credential.Password;
import java.util.Arrays;

public class Client extends User {

    private final int DEFAULT_ACCOUNT_NUMBERS = 3;
    private Account[] accounts;
    private Status status;
    private String phoneNumber;

    public Client(String firstName, String lastName, String email, Password password) {
        super(firstName, lastName, email, password);
        accounts = new Account[DEFAULT_ACCOUNT_NUMBERS];
        status = Status.PENDING;
    }

    public boolean addAccount(Account account) {
        //TODO
        return false;
    }

    public boolean deleteAccount(Account account) {
        //TODO
        return false;
    }


    public Account[] getAccounts() {
        return accounts;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("accounts= ").append(Arrays.toString(accounts));
        sb.append(", status= ").append(status.toString());
        sb.append(", phoneNumber= '").append(phoneNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
