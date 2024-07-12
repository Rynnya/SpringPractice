package repository;

import entities.Cart;
import entities.Client;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public class LocalClientRepository implements ClientRepository {

    private long nextClientId = 1;

    private final List<Client> clients = new ArrayList<>();

    @Override
    public long save(Client client) {
        long id = generateId();

        Cart cart = new Cart();
        cart.setProducts(new HashSet<>());
        cart.setPromocode("");

        client.setId(id);
        client.setCart(cart);

        clients.add(client);
        return id;
    }

    @Override
    public Optional<Client> findById(long id) {
        return clients
                .stream()
                .filter(client -> client.getId() == id)
                .findFirst();
    }

    @Override
    public boolean deleteById(long id) {
        return clients.removeIf(client -> client.getId() == id);
    }

    private long generateId() {
        return nextClientId++;
    }

}
