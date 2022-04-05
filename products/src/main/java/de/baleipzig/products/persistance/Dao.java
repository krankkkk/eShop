package de.baleipzig.products.persistance;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(long id);

    List<T> getAll();

    T save(T t);

    T update(Product product);

    void delete(long id);
}
