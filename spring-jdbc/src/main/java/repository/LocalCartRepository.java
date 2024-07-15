package repository;

import entities.Client;
import entities.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class LocalCartRepository implements CartRepository {

    @Override
    public boolean addProductToCart(Client client, Product product) {
        return client.getCart().getProducts().add(product);
    }

    @Override
    public boolean deleteProductFromCart(Client client, long productId) {
        return client.getCart().getProducts().removeIf(product -> product.getId() == productId);
    }

}
