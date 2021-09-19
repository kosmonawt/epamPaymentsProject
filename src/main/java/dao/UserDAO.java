package dao;

import java.util.List;

public interface UserDAO<T> {

    List<T> getAll();

    T getById(Long id);

    T getByEmail(String email);

    void create(T t);

    void update(T T);

    void delete(Long id);

    boolean existByEmail(String email);
}
