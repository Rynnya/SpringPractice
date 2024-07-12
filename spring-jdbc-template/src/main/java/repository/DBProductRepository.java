package repository;

import entities.Product;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Primary
@Repository
@AllArgsConstructor
public class DBProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public long save(Product product) {
        var insertSql = "INSERT INTO products (name, price, amount) VALUES (?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setString(1, product.getName());
            prepareStatement.setFloat(2, product.getPrice());
            prepareStatement.setInt(3, 1);
            return prepareStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return (long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    @Override
    @Transactional
    public Optional<Product> findById(long id) {
        var selectSql = "SELECT * FROM products WHERE id = ?;";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectSql);
            prepareStatement.setLong(1, id);
            return prepareStatement;
        };

        return jdbcTemplate
                .query(preparedStatementCreator, getProductMapper())
                .stream()
                .findFirst();
    }

    @Override
    @Transactional
    public List<Product> findAll(String name) {
        var selectSql = "SELECT * FROM products WHERE name LIKE ?;";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectSql);
            prepareStatement.setString(1, "%" + nullToEmptyString(name) + "%");
            return prepareStatement;
        };

        return jdbcTemplate.query(preparedStatementCreator, getProductMapper());
    }

    @Override
    @Transactional
    public void update(Product product) {
        var updateSql = "UPDATE products SET name = ?, price = ? WHERE id = ?;";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(updateSql);
            prepareStatement.setString(1, product.getName());
            prepareStatement.setFloat(2, product.getPrice());
            prepareStatement.setLong(3, product.getId());
            return prepareStatement;
        };

        jdbcTemplate.update(preparedStatementCreator);
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        var deleteSql = "DELETE FROM products WHERE id = ?;";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(deleteSql);
            prepareStatement.setLong(1, id);
            return prepareStatement;
        };

        return jdbcTemplate.update(preparedStatementCreator) > 0;
    }

    private static RowMapper<Product> getProductMapper() {
        return (resultSet, rowNum) -> {
            Product product = new Product();

            product.setId(resultSet.getLong("id"));
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getFloat("price"));

            return product;
        };
    }

    private String nullToEmptyString(String str) {
        return str == null ? "" : str;
    }

}
