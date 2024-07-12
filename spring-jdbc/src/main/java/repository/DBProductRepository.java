package repository;

import entities.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

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
public class DBProductRepository implements ProductRepository {

    @Override
    public long save(Product product) {
        var insertSql = "INSERT INTO products (name, price, amount) VALUES (?,?,?);";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
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
        var selectSql = "SELECT * FROM products WHERE id = ?;";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
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
        var selectSql = "SELECT * FROM products WHERE name LIKE ?;";
        List<Product> products = new ArrayList<>();

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
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
        var uodateSql = "UPDATE products SET name = ?, price = ? WHERE id = ?;";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(uodateSql)) {
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
        var deleteSql = "DELETE FROM products WHERE id = ?;";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(deleteSql)) {
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
