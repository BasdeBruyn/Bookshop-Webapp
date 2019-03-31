package nl.IPWRC.services;

import nl.IPWRC.exceptions.InvalidInputException;
import nl.IPWRC.models.Item;
import nl.IPWRC.models.User;
import nl.IPWRC.persistance.ItemDao;

import java.util.List;

public class ItemService implements CrudService<Item> {

    private static ItemService mInstance;
    private final ItemDao itemDao;
    public static final int minNameLength = 5;

    public ItemService() {
        itemDao = ItemDao.getInstance();
    }

    public static ItemService getInstance() {
        if (mInstance == null) {
            mInstance = new ItemService();
        }
        return mInstance;
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
    public Item save(Item item) throws InvalidInputException {
        validate(item, false);

        return itemDao.save(item);
    }

    @Override
    public void update(Item item) throws InvalidInputException {
        validate(item, true);

        itemDao.update(item);
    }

    @Override
    public void delete(Item item) {
        itemDao.delete(item);
    }

    @Override
    public void delete(Integer id) {
        itemDao.delete(id);
    }

    @Override
    public void validate(Item item, boolean update) throws InvalidInputException {
        ValidationService.validate(errors -> {
            if (item.getPrice() <= 0)
                errors.add("Invalid price");
            if (item.getName().length() < minNameLength)
                errors.add("Name should be at least " + minNameLength + " characters long");
            if (!ValidationService.isValidUrl(item.getUrl()))
                errors.add("Invalid URL");
        });
    }
}
