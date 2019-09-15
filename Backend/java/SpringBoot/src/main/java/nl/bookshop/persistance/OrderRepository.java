package nl.bookshop.persistance;

import nl.bookshop.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    Order findFirstByUserIdOrderByCurDateDesc(Integer userId);
}
