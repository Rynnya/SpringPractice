package controllers;

import entities.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import services.ProductService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> addProduct(@Validated @RequestBody Product product) throws URISyntaxException {
        log.info("Добавление продукта {}", product.getId());

        long id = productService.save(product);

        return ResponseEntity
                .created(new URI("http://localhost:8080/products/" + id))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") long id) {
        log.info("Поиск продукта по идентификатору {}", id);

        return productService
                .findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Product> getProducts(@RequestParam(value = "name", required = false) String name) {
        log.info("Поиск продуктов по имени {}", name);

        return productService.findAll(name);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        log.info("Обновление продукта {}", product);

        productService.update(product);

        return product;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") long id) {
        log.info("Удаление продукта по идентификатору {}", id);

        return productService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
