package dao;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @param <T> - card DTO as example
 */
public interface CardDAO<T> {


    List<T> getAll();

    T getCardById(Long id);

    T getByCardNumber(String cardNumber);

    public void create(T t);

    public void update(T t);


    void delete(Long id);

    boolean existByCardNumber(String cardNumber);

    List<T> getAllByAccountNumber(@NotNull Long accNumber);
}
