package nl.IPWRC.persistance;

import nl.IPWRC.models.Item;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Arrays;
import java.util.List;

public class ItemDao extends GenericDao<Item> {

    private static ItemDao mInstance;

    public ItemDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public static ItemDao getInstance() {
        return mInstance;
    }

    public static void initInstance(SessionFactory sessionFactory){
        mInstance = new ItemDao(sessionFactory);
    }

    public Item[] getAllByIds(Integer[] ids){
        Item[] items = new Item[ids.length];
        for (int i = 0; i < ids.length; i++) {
            items[i] = getById(ids[i]);
        }
        return items;
    }

    @Override
    protected Class<Item> getTypeClass() {
        return Item.class;
    }

    private String listFromIds(Integer[] ids) {
        String[] idsStr = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            idsStr[i] = String.valueOf(ids[i]);
        }
        return String.join(", ", idsStr);
    }
}
