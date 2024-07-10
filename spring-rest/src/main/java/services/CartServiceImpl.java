package services;

import entities.Client;
import entities.Product;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private final ClientService clientService;

    @Autowired
    private final ProductService productService;

    @Override
    public boolean addToCartById(long userId, long productId) {
        return clientService
                .findById(userId)
                .map(client -> findProductAndAddToCart(client, productId))
                .orElse(false);
    }

    @Override
    public boolean deleteFromCartById(long userId, long productId) {
        return clientService
                .findById(userId)
                .map(client -> deleteFromCart(client, productId))
                .orElse(false);
    }

    private boolean findProductAndAddToCart(Client client, long productId) {
        return productService
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