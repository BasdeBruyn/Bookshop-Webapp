package nl.IPWRC.persistance;

import nl.IPWRC.models.CartItem;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class CartDao extends GenericDao<CartItem> {

    private static CartDao mInstance;

    public CartDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public static CartDao getInstance() {
        return mInstance;
    }

    public static void initInstance(SessionFactory sessionFactory){
        mInstance = new CartDao(sessionFactory);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        currentSession().save(cartItem);
        currentSession().flush();
        return null;
    }

    public Integer[] getItemIdsFromCart(Integer userId) {
        String cartQuery = "select ci.itemId from CartItem ci where ci.userId = :userId";
        Query<Integer> cartQueryObj = currentSession().createQuery(cartQuery, Integer.class);

        cartQueryObj.setParameter("userId", userId);

        List<Integer> itemIds = cartQueryObj.list();
        if (itemIds.size() == 0)
            return null;
        else
            return itemIds.toArray(new Integer[0]);
    }

    public void emptyCart(Integer userId) {
        String query = "delete from CartItem ci where ci.userId = :userId";
        Query queryObj = currentSession().createQuery(query);
        queryObj.setParameter("userId", userId);
        queryObj.executeUpdate();
    }

    @Override
    protected Class<CartItem> getTypeClass() {
        return CartItem.class;
    }
}
