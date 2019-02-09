package nl.IPWRC.persistance;

import nl.IPWRC.models.Customer;
import org.hibernate.SessionFactory;

public class CustomerDao extends GenericDao<Customer> {

    public CustomerDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Customer> getTypeClass() {
        return Customer.class;
    }
}
