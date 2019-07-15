package nl.IPWRC.services;

import nl.IPWRC.exceptions.InvalidInputException;
import nl.IPWRC.models.Order;
import nl.IPWRC.models.User;
import nl.IPWRC.persistance.OrderDao;

import java.sql.Timestamp;
import java.util.List;

public class OrderService implements CrudService<Order> {
    private static OrderService mInstance;
    private final OrderDao orderDao;

    public OrderService() {
        orderDao = OrderDao.getInstance();
    }

    public static OrderService getInstance() {
        if (mInstance == null) {
            mInstance = new OrderService();
        }
        return mInstance;
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Order getById(Integer id) {
        return orderDao.getById(id);
    }

    public Order getById(Integer id, User authUser) {
        Order order = getById(id);

        AuthService.assertNull(order, authUser);
        AuthService.authorizeAction(order.getUserId(), authUser);

        return order;
    }

    public Order getLatestOrder(Integer userId){
        return orderDao.getLatestOrder(userId);
    }

    public Order getLatestOrder(Integer userId, User authUser){
        AuthService.authorizeAction(userId, authUser);

        return getLatestOrder(userId);
    }

    @Override
    public Order save(Order order) throws InvalidInputException {
        validate(order, false);

        if (order.getCurDate() == null) {
            order.setCurDate(new Timestamp(System.currentTimeMillis()));
        }

        return orderDao.save(order);
    }

    public Order save(Order order, User authUser) throws InvalidInputException {
        AuthService.authorizeAction(order.getUserId(), authUser);

        return save(order);
    }

    @Override
    public void update(Order order) throws InvalidInputException {
        validate(order, true);

        orderDao.update(order);
    }

    public void update(Order order, User authUser) throws InvalidInputException {
        AuthService.authorizeAction(order.getUserId(), authUser);

        update(order);
    }

    @Override
    public void delete(Order order) {
        orderDao.delete(order);
    }

    @Override
    public void delete(Integer id) {
        orderDao.delete(id);
    }

    public void delete(Integer id, User authUser) {
        Order order = getById(id);

        AuthService.assertNull(order, authUser);
        AuthService.authorizeAction(order.getUserId(), authUser);

        delete(order);
    }

    @Override
    public void validate(Order order, boolean update) throws InvalidInputException {
        ValidationService.validate(errors -> {
            if (! ValidationService.isValidName(order.getFirstName()))
                errors.add("Invalid first name");
            if (! ValidationService.isValidName(order.getLastName()))
                errors.add("Invalid last name");
            if (! ValidationService.isValidPostalCode(order.getPostalCode()))
                errors.add("Invalid postal code");
            if (! ValidationService.isValidHoueNr(order.getHouseNr()))
                errors.add("Invalid house number");
            if (! ValidationService.isValidName(order.getStreetName()))
                errors.add("Invalid street name");
            if (! ValidationService.isValidName(order.getResidence()))
                errors.add("Invalid residence");
            if (! ValidationService.isValidPhoneNr(order.getPhoneNr()))
                errors.add("Invalid phone number");
        });
    }
}
