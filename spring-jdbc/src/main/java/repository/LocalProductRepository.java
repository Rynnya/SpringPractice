package repository;

import entities.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class LocalProductRepository implements ProductRepository {

    private final Random random = new Random();

    private final List<Product> products = new ArrayList<>();

    @Override
    public long save(Product product) {
        long id = generateId();
        product.setId(id);

        products.add(product);
        return id;
    }

    @Override
    public Optional<Product> findById(long id) {
        return products
                .stream()
                .filter(product -> product.getId() == id)
                .findFirst();
    }

    @Override
    public List<Product> findAll(String name) {
        if (name == null) {
            return products;
        }

        return products
                .stream()
                .filter(product -> product.getName().equals(name))
                .toList();
    }

    @Override
    public void update(Product product) {
        for (Product p : products) {
            if (p.getId() == product.getId()) {
                p.setName(product.getName());
                p.setPrice(product.getPrice());
                return;
            }
        }
    }

    @Override
    public boolean deleteById(long id) {
        return products.removeIf(product -> product.getId() == id);
    }

    private long generateId() {
        return random.nextLong(1, 1_000_000);
    }

}
