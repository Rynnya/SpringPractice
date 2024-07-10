package services;

import entities.Client;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.ClientRepository;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private final ClientRepository clientRepository;

    @Override
    public long save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> findById(long id) {
        return clientRepository.findById(id);
    }

    @Override
    public boolean deleteById(long id) {
        return clientRepository.deleteById(id);
    }

}