package nl.Bookshop.services;

import nl.Bookshop.exceptions.InvalidInputException;

import java.util.List;

public interface CrudService<T> {
    List<T> getAll();
    T getById(Integer id);
    T save(T t) throws InvalidInputException;
    void update(T t) throws InvalidInputException;
    void delete(T t);
    void delete(Integer id);
    void validate(T t, boolean update) throws InvalidInputException;
}
