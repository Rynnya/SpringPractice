package repository;

import entities.Client;
import entities.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class LocalCartRepository implements CartRepository {

    private final ProductRepository productRepository;

    private final ClientRepository clientRepository;

    @Override
    public boolean addToCartById(long userId, long productId) {
        return clientRepository
                .findById(userId)
                .map(client -> findProductAndAddToCart(client, productId))
                .orElse(false);
    }

    @Override
    public boolean deleteFromCartById(long userId, long productId) {
        return clientRepository
                .findById(userId)
                .map(client -> deleteFromCart(client, productId))
                .orElse(false);
    }

    private boolean findProductAndAddToCart(Client client, long productId) {
        return productRepository
                .findById(productId)
                .map(product -> addProductToCart(client, product))
                .orElse(false);
    }

    private boolean addProductToCart(Client client, Product product) {
        return client.getCart().getProducts().add(product);
    }

    private boolean deleteFromCart(Client client, long productId) {
        return client.getCart().getProducts().removeIf(product -> product.getId() == productId);
    }

}
