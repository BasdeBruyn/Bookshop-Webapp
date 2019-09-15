package nl.bookshop.data.services;

import nl.bookshop.models.Item;
import nl.bookshop.persistance.ItemRepository;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ItemService implements CrudService<Item> {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAll() {
        Iterator<Item> items = itemRepository.findAll().iterator();
        return IteratorUtils.toList(items);
    }

    @Override
    public Item getById(Integer itemId) {
        return itemRepository
                .findById(itemId)
                .orElse(null);
    }

    public boolean existsById(Integer itemId) {
        return itemRepository.existsById(itemId);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void update(Item item) {
        save(item);
    }

    @Override
    public void delete(Item item) {
        itemRepository.delete(item);
    }

    @Override
    public void delete(Integer itemId) {
        itemRepository.deleteById(itemId);
    }

}
