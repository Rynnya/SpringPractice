package controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.CartService;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("carts")
public class CartController {

    @Autowired
    private final CartService cartService;

    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<Void> addProductToCart(@PathVariable("userId") long userId, @PathVariable("productId") long productId) {
        log.info("Добавление продукта {} в корзину пользователя {}", productId, userId);

        return cartService.addToCartById(userId, productId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<Void> deleteProductFromCart(@PathVariable("userId") long userId, @PathVariable("productId") long productId) {
        log.info("Удаление продукта {} из корзины пользователя {}", productId, userId);

        return cartService.deleteFromCartById(userId, productId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
