package repository;

import entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    long save(Product product);

    Optional<Product> findById(long id);

    List<Product> findAll(String name);

    void update(Product product);

    boolean deleteById(long id);

}
