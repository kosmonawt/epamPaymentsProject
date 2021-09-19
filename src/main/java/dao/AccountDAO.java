package dao;

import java.util.List;

public interface AccountDAO<T> {

    void create(T t);

    void update(T t);

    void delete(Long id);

    void get(Long id);

    boolean existByAccountNumber(Long accNum);

    List<T> getAccountsByUserId(Long id);

    List<T> getAccountsByUserEmail(String email);

}
