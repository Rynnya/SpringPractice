package services;

public interface CartService {

    boolean addToCartById(long userId, long productId);

    boolean deleteFromCartById(long userId, long productId);

}