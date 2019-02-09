package nl.IPWRC.Services;

import nl.IPWRC.models.Item;
import nl.IPWRC.persistance.ItemDao;

import java.util.List;

public class ItemService implements CrudService<Item> {

    private final ItemDao itemDao;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public List<Item> getAll() {
        return itemDao.getAll();
    }

    @Override
    public Item getById(Integer id) {
        return itemDao.getById(id);
    }

    @Override
    public Item save(Item item) {
        return itemDao.save(item);
    }

    @Override
    public void update(Item item) {
        itemDao.update(item);
    }

    @Override
    public void delete(Item item) {
        itemDao.delete(item);
    }
}
