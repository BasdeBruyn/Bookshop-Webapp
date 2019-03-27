package nl.IPWRC.persistance;

import nl.IPWRC.models.Order;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDao extends GenericDao<Order> {

    private static OrderDao mInstance;

    public OrderDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public static OrderDao getInstance() {
        return mInstance;
    }

    public static void initInstance(SessionFactory sessionFactory){
        mInstance = new OrderDao(sessionFactory);
    }

    public Order getLatestOrder(Integer userId){
        String query = "from Order o where o.userId = :userId order by o.curDate desc";
        Query<Order> queryObj = currentSession().createQuery(query, Order.class);

        queryObj.setParameter("userId", userId);

        List<Order> orders = queryObj.list();
        if (orders.size() == 0)
            return null;
        else
            return orders.get(0);
    }

    @Override
    protected Class<Order> getTypeClass() {
        return Order.class;
    }
}
