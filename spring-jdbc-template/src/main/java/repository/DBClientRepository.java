package repository;

import entities.Cart;
import entities.Client;
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
import java.util.Objects;
import java.util.Optional;

@Primary
@Repository
@Transactional
@AllArgsConstructor
public class DBClientRepository implements ClientRepository {

    private final String SAVE_CART_SQL = "INSERT INTO carts (promocode) VALUES (?)";

    private final String SAVE_CLIENT_SQL = "INSERT INTO clients (name, username, password, email, cart_id) VALUES (?,?,?,?,?)";

    private final String FIND_BY_ID_SQL = "SELECT * FROM clients JOIN carts ON clients.cart_id = carts.id WHERE clients.id = ?";

    private final String DELETE_BY_ID_SQL = "DELETE FROM clients WHERE id = ?;";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public long save(Client client) {
        KeyHolder cartKeyHolder = new GeneratedKeyHolder();
        KeyHolder clientKeyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator cartInsertCreator = connection -> {
            var cartStatement = connection.prepareStatement(SAVE_CART_SQL, Statement.RETURN_GENERATED_KEYS);
            cartStatement.setString(1, "");
            return cartStatement;
        };

        jdbcTemplate.update(cartInsertCreator, cartKeyHolder);

        PreparedStatementCreator clientInsertCreator = connection -> {
            var clientStatement = connection.prepareStatement(SAVE_CLIENT_SQL, Statement.RETURN_GENERATED_KEYS);
            clientStatement.setString(1, client.getName());
            clientStatement.setString(2, client.getLogin());
            clientStatement.setString(3, client.getPassword());
            clientStatement.setString(4, client.getEmail());
            clientStatement.setLong(5, (long) Objects.requireNonNull(cartKeyHolder.getKeys()).get("id"));
            return clientStatement;
        };

        jdbcTemplate.update(clientInsertCreator, clientKeyHolder);

        return (long) Objects.requireNonNull(clientKeyHolder.getKeys()).get("id");
    }

    @Override
    public Optional<Client> findById(long id) {
        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL);
            prepareStatement.setLong(1, id);
            return prepareStatement;
        };

        return jdbcTemplate
                .query(preparedStatementCreator, getClientMapper())
                .stream()
                .findFirst();
    }

    @Override
    public boolean deleteById(long id) {
        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(DELETE_BY_ID_SQL);
            prepareStatement.setLong(1, id);
            return prepareStatement;
        };

        return jdbcTemplate.update(preparedStatementCreator) > 0;
    }

    private static RowMapper<Client> getClientMapper() {
        return (resultSet, rowNum) -> {
            Client client = new Client();
            Cart cart = new Cart();

            cart.setId(resultSet.getLong("cart_id"));
            cart.setPromocode(resultSet.getString("promocode"));
            client.setId(resultSet.getLong("id"));
            client.setName(resultSet.getString("name"));
            client.setLogin(resultSet.getString("username"));
            client.setPassword(resultSet.getString("password"));
            client.setEmail(resultSet.getString("email"));
            client.setCart(cart);

            return client;
        };
    }

}
