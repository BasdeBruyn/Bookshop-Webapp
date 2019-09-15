package nl.bookshop.data.services;

import nl.bookshop.models.Order;
import nl.bookshop.persistance.OrderRepository;
import nl.bookshop.security.AuthService;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements CrudService<Order> {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAll() {
        Iterator<Order> orders = orderRepository.findAll().iterator();
        return IteratorUtils.toList(orders);
    }

    @Override
    public Order getById(Integer orderId) {
        return orderRepository
                .findById(orderId)
                .orElse(null);
    }

    public Order getLatest(Integer userId) {
        return orderRepository.findFirstByUserIdOrderByCurDateDesc(userId);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        save(order);
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public void delete(Integer orderId) {
        orderRepository.deleteById(orderId);
    }


    public Order getById(UserDetails authUser, Integer id) {
        Order order = getById(id);
        AuthService.assertNull(order, authUser);
        AuthService.authorizeAction(order.getUserId(), authUser);
        return order;
    }

    public Order getLatest(UserDetails authUser, Integer userId) {
        AuthService.authorizeAction(userId, authUser);
        return getLatest(userId);
    }

    public void save(UserDetails authUser, Order order) {
        AuthService.authorizeAction(order.getUserId(), authUser);
        save(order);
    }

    public void update(UserDetails authUser, Order order) {
        AuthService.authorizeAction(order.getUserId(), authUser);
        update(order);
    }

    public void delete(UserDetails authUser, Integer id) {
        Order order = getById(id);
        AuthService.assertNull(order, authUser);
        AuthService.authorizeAction(order.getUserId(), authUser);
        delete(id);
    }
}
