package repository;

import entities.Client;
import entities.Product;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Statement;

@Primary
@Repository
@Transactional
@AllArgsConstructor
public class DBCartRepository implements CartRepository {

    private final String ADD_PRODUCT_TO_CART_SQL = "INSERT INTO products_carts (id_product, amount, id_cart) VALUES (?,?,?)";

    private final String DELETE_FROM_CART_SQL = "DELETE FROM products_carts WHERE id_cart = ? AND id_product = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean addProductToCart(Client client, Product product) {
        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(ADD_PRODUCT_TO_CART_SQL, Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setLong(1, product.getId());
            prepareStatement.setInt(2, 1);
            prepareStatement.setLong(3, client.getCart().getId());
            return prepareStatement;
        };

        return jdbcTemplate.update(preparedStatementCreator) > 0;
    }

    @Override
    public boolean deleteProductFromCart(Client client, long productId) {
        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(DELETE_FROM_CART_SQL);
            prepareStatement.setLong(1, client.getCart().getId());
            prepareStatement.setLong(2, productId);
            return prepareStatement;
        };

        return jdbcTemplate.update(preparedStatementCreator) > 0;
    }

}
