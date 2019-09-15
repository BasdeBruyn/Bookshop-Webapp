package nl.bookshop.controllers;

import nl.bookshop.data.services.CartService;
import nl.bookshop.models.CartItem;
import nl.bookshop.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static nl.bookshop.security.SecurityStrings.IS_AUTHENTICATED;
import static nl.bookshop.security.SecurityStrings.ROLE_ADMIN;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @Secured(ROLE_ADMIN)
    public List<CartItem> getCartItems() {
        return cartService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize(IS_AUTHENTICATED)
    public CartItem getCartItem(@AuthenticationPrincipal UserDetails authUser,
                                @PathVariable Integer id) {
        return cartService.getById(authUser, id);
    }

    @GetMapping("/items/{userId}")
    @PreAuthorize(IS_AUTHENTICATED)
    public List<Item> getItemsFromCart(@AuthenticationPrincipal UserDetails authUser,
                                       @PathVariable Integer userId) {
        return cartService.getItemsFromCart(authUser, userId);
    }

    @GetMapping("/from/{userId}")
    @PreAuthorize(IS_AUTHENTICATED)
    public List<CartItem> getCart(@AuthenticationPrincipal UserDetails authUser,
                                  @PathVariable Integer userId) {
        return cartService.getCart(authUser, userId);
    }

    @PostMapping
    @PreAuthorize(IS_AUTHENTICATED)
    public void saveCartItem(@AuthenticationPrincipal UserDetails authUser,
                             @Valid @RequestBody CartItem cartItem) {
        cartService.save(authUser, cartItem);
    }

    @PutMapping
    @PreAuthorize(IS_AUTHENTICATED)
    public void updateCartItem(@AuthenticationPrincipal UserDetails authUser,
                               @Valid @RequestBody CartItem cartItem) {
        cartService.update(authUser, cartItem);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(IS_AUTHENTICATED)
    public void deleteCartItem(@AuthenticationPrincipal UserDetails authUser,
                               @PathVariable Integer id) {
        cartService.delete(authUser, id);
    }

    @DeleteMapping("/from/{userId}")
    @Transactional
    @PreAuthorize(IS_AUTHENTICATED)
    public void emptyCart(@AuthenticationPrincipal UserDetails authUser,
                          @PathVariable Integer userId) {
        cartService.emptyCart(authUser, userId);
    }

}
