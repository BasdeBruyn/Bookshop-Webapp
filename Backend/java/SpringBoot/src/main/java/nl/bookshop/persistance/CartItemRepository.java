package nl.bookshop.persistance;

import nl.bookshop.models.CartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
    List<CartItem> findAllByUserId(Integer userId);
    void deleteAllByUserId(Integer userId);
}
