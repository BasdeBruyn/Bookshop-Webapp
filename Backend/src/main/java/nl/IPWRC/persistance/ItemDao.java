package nl.IPWRC.persistance;

import nl.IPWRC.models.Item;
import org.hibernate.SessionFactory;

public class ItemDao extends GenericDao<Item> {

    public ItemDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Item> getTypeClass() {
        return Item.class;
    }
}
