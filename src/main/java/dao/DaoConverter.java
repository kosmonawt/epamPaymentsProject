package dao;

/**
 * Convert DTO to DAO and back
 * @param <T> - your DTO
 * @param <E> - your DAO
 */
public interface DaoConverter<T, E> {
    E convertTo(T t);
    T convertFrom(E e);
}
