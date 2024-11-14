package repository;

import entities.Client;
import entities.Product;

public interface CartRepository {

    boolean addProductToCart(Client client, Product product);

    boolean deleteProductFromCart(Client client, long productId);

}
