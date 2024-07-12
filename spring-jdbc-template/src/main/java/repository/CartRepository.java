package repository;

public interface CartRepository {

    boolean addToCartById(long userId, long productId);

    boolean deleteFromCartById(long userId, long productId);

}
