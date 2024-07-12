package services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CartRepository;

@AllArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private final CartRepository cartRepository;

    @Override
    public boolean addToCartById(long userId, long productId) {
        return cartRepository.addToCartById(userId, productId);
    }

    @Override
    public boolean deleteFromCartById(long userId, long productId) {
        return cartRepository.deleteFromCartById(userId, productId);
    }

}