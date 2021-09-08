package dao;

import java.util.List;

public interface DAO<T> {

    List<T> getAll();

    T getById(Long id);

    T getByName(String name);

    void create(T t);

    void update(T t);

    void delete(Long id);

    boolean exist(String name);
}
