package repository;

import entities.Client;
import entities.Product;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import services.ClientService;
import services.ProductService;

import java.sql.Statement;

@Primary
@Repository
@AllArgsConstructor
public class DBCartRepository implements CartRepository {

    private final ProductService productService;

    private final ClientService clientService;

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public boolean addToCartById(long userId, long productId) {
        return clientService
                .findById(userId)
                .map(client -> findProductAndAddToCart(client, productId))
                .orElse(false);
    }

    @Override
    @Transactional
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
        var insertSql = "INSERT INTO products_carts (id_product, amount, id_cart) VALUES (?,?,?);";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setLong(1, product.getId());
            prepareStatement.setInt(2, 1);
            prepareStatement.setLong(3, client.getCart().getId());
            return prepareStatement;
        };

        return jdbcTemplate.update(preparedStatementCreator) > 0;
    }

    private boolean deleteFromCart(Client client, long productId) {
        var deleteSql = "DELETE FROM products_carts WHERE id_cart = ? AND id_product = ?;";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(deleteSql);
            prepareStatement.setLong(1, client.getCart().getId());
            prepareStatement.setLong(2, productId);
            return prepareStatement;
        };

        return jdbcTemplate.update(preparedStatementCreator) > 0;
    }

}
