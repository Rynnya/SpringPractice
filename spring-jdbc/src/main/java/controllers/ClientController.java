package controllers;

import entities.Client;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.ClientService;

import java.net.URI;
import java.net.URISyntaxException;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("clients")
public class ClientController {

    @Autowired
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<Void> createClient(@Validated @RequestBody Client client) throws URISyntaxException {
        log.info("Добавление клиента {}", client.getName());

        long id = clientService.save(client);

        return ResponseEntity
                .created(new URI("http://localhost:8080/clients/" + id))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") long id) {
        log.info("Поиск клиента по идентификатору {}", id);

        return clientService
                .findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") long id) {
        log.info("Удаление клиента по идентификатору {}", id);

        boolean isDeleted = clientService.deleteById(id);

        return isDeleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
