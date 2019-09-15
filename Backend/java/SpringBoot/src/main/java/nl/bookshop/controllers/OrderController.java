package nl.bookshop.controllers;

import nl.bookshop.data.services.OrderService;
import nl.bookshop.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static nl.bookshop.security.SecurityStrings.IS_AUTHENTICATED;
import static nl.bookshop.security.SecurityStrings.ROLE_ADMIN;

@RestController
@RequestMapping("/order")
public class OrderController {
    
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Secured(ROLE_ADMIN)
    public List<Order> getOrders() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize(IS_AUTHENTICATED)
    public Order getOrder(@AuthenticationPrincipal UserDetails authUser,
                          @PathVariable("id") Integer id) {
        return orderService.getById(authUser, id);
    }

    @GetMapping("/latest/{userId}")
    @PreAuthorize(IS_AUTHENTICATED)
    public Order getLatest(@AuthenticationPrincipal UserDetails authUser,
                           @PathVariable Integer userId) {
        return orderService.getLatest(authUser, userId);
    }

    @PostMapping
    @PreAuthorize(IS_AUTHENTICATED)
    public void saveOrder(@AuthenticationPrincipal UserDetails authUser,
                          @Valid @RequestBody Order order) {
        orderService.save(authUser, order);
    }

    @PutMapping
    @PreAuthorize(IS_AUTHENTICATED)
    public void updateOrder(@AuthenticationPrincipal UserDetails authUser,
                            @Valid @RequestBody Order order) {
        orderService.update(authUser, order);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(IS_AUTHENTICATED)
    public void deleteOrder(@AuthenticationPrincipal UserDetails authUser,
                            @PathVariable Integer id) {
        orderService.delete(authUser, id);
    }

}
