package services;

import entities.Client;

import java.util.Optional;

public interface ClientService {

    long save(Client client);

    Optional<Client> findById(long id);

    boolean deleteById(long id);

}