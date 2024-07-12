package repository;

import entities.Cart;
import entities.Client;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static repository.DBConstants.JDBC;

@Primary
@Repository
public class DBClientRepository implements ClientRepository {

    @Override
    @Transactional
    public long save(Client client) {
        var cartInsertSql = "INSERT INTO carts (promocode) VALUES (?);";
        var clientInsertSql = "INSERT INTO clients (name, username, password, email, cart_id) VALUES (?,?,?,?,?);";

        try (var connection = DriverManager.getConnection(JDBC);
             var cartStatement = connection.prepareStatement(cartInsertSql, Statement.RETURN_GENERATED_KEYS);
             var clientStatement = connection.prepareStatement(clientInsertSql, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);

            cartStatement.setString(1, "");
            cartStatement.executeUpdate();

            ResultSet clientCartId = cartStatement.getGeneratedKeys();
            if (!clientCartId.next()) {
                throw new RuntimeException("Ошибка при получении идентификатора для корзины");
            }

            clientStatement.setString(1, client.getName());
            clientStatement.setString(2, client.getLogin());
            clientStatement.setString(3, client.getPassword());
            clientStatement.setString(4, client.getEmail());
            clientStatement.setLong(5, clientCartId.getLong(1));

            clientStatement.executeUpdate();

            connection.commit();

            ResultSet clientId = clientStatement.getGeneratedKeys();
            if (!clientId.next()) {
                throw new RuntimeException("Ошибка при получении идентификатора для пользователя");
            }

            return clientId.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public Optional<Client> findById(long id) {
        var selectSql = "SELECT * FROM clients JOIN carts ON clients.cart_id = carts.id WHERE clients.id = ?;";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setLong(1, id);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
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

                return Optional.of(client);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public boolean deleteById(long id) {
        var deleteSql = "DELETE FROM clients WHERE id = ?;";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(deleteSql)) {
            prepareStatement.setLong(1, id);

            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
