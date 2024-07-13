package repository;

import entities.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static repository.DBConstants.JDBC;

@Primary
@Repository
@Transactional
public class DBProductRepository implements ProductRepository {

    private final String SAVE_PRODUCT_SQL = "INSERT INTO products (name, price, amount) VALUES (?,?,?)";

    private final String FIND_BY_ID_SQL = "SELECT * FROM products WHERE id = ?";

    private final String SELECT_ALL_PRODUCTS_SQL = "SELECT * FROM products WHERE name LIKE ?";

    private final String UPDATE_PRODUCT_SQL = "UPDATE products SET name = ?, price = ? WHERE id = ?";

    private final String DELETE_PRODUCT_SQL = "DELETE FROM products WHERE id = ?";

    @Override
    public long save(Product product) {
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(SAVE_PRODUCT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, product.getName());
            prepareStatement.setFloat(2, product.getPrice());
            prepareStatement.setInt(3, 1);

            prepareStatement.executeUpdate();

            ResultSet rs = prepareStatement.getGeneratedKeys();
            if (!rs.next()) {
                throw new RuntimeException("Ошибка при получении идентификатора");
            }

            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Product> findById(long id) {
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setLong(1, id);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                Product product = new Product();

                product.setId(resultSet.getLong("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getFloat("price"));

                return Optional.of(product);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findAll(String name) {
        List<Product> products = new ArrayList<>();

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS_SQL)) {
            prepareStatement.setString(1, "%" + nullToEmptyString(name) + "%");

            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();

                product.setId(resultSet.getLong("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getFloat("price"));

                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Product product) {
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {
            prepareStatement.setString(1, product.getName());
            prepareStatement.setFloat(2, product.getPrice());
            prepareStatement.setLong(3, product.getId());

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(long id) {
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {
            prepareStatement.setLong(1, id);

            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String nullToEmptyString(String str) {
        return str == null ? "" : str;
    }

}
