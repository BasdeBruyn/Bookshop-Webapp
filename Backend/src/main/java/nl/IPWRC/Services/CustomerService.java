package nl.IPWRC.Services;

import nl.IPWRC.models.Customer;
import nl.IPWRC.persistance.CustomerDao;

import java.util.List;

public class CustomerService implements CrudService<Customer> {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public List<Customer> getAll() {
        return customerDao.getAll();
    }

    @Override
    public Customer getById(Integer id) {
        return customerDao.getById(id);
    }

    @Override
    public Customer save(Customer customer) {
//        customer.setPassword(BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt(10)));
        return customerDao.save(customer);
    }

    @Override
    public void update(Customer customer) {
        customerDao.update(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerDao.delete(customer);
    }
}
