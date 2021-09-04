package dao;

public interface DaoConverter<T, E> {
    E convertTo(T t);
    T convertFrom(E e);
}
