package nl.bookshop.persistance;

import nl.bookshop.models.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer> {
    List<Item> findAllByIdIn(List<Integer> ids);
}
