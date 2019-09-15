package nl.bookshop.data.services;

import nl.bookshop.models.CartItem;
import nl.bookshop.models.Item;
import nl.bookshop.persistance.CartItemRepository;
import nl.bookshop.persistance.ItemRepository;
import nl.bookshop.persistance.UserRepository;
import nl.bookshop.security.AuthService;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CartService implements CrudService<CartItem> {
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public CartService(CartItemRepository cartItemRepository,
                       ItemRepository itemRepository) {
        this.cartItemRepository = cartItemRepository;
        this.itemRepository = itemRepository;
    }

    @Autowired


    @Override
    public List<CartItem> getAll() {
        Iterator<CartItem> cartItems = cartItemRepository.findAll().iterator();
        return IteratorUtils.toList(cartItems);
    }

    @Override
    public CartItem getById(Integer id) {
        return cartItemRepository.findById(id).orElse(null);
    }

    public CartItem getById(Integer id, UserDetails userDetails) {
        AuthService.authorizeAction(id, userDetails);
        return getById(id);
    }

    public List<Item> getItemsFromCart(Integer userId) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);

        List<Integer> itemIds = new ArrayList<>();
        cartItems.forEach(cartItem ->
                itemIds.add(cartItem.getItemId()));

        List<Item> items = new ArrayList<>();
        itemIds.forEach(id ->
                itemRepository
                        .findById(id)
                        .ifPresent(items::add)
        );

        return items;
    }

    public List<CartItem> getCart(Integer userId) {
        return cartItemRepository.findAllByUserId(userId);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void update(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public void delete(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void delete(Integer id) {
        cartItemRepository.deleteById(id);
    }

    public void emptyCart(Integer userId) {
        cartItemRepository.deleteAllByUserId(userId);
    }


    public CartItem getById(UserDetails authUser, Integer id) {
        CartItem cartItem = getById(id);
        AuthService.assertNull(cartItem, authUser);
        AuthService.authorizeAction(cartItem.getUserId(), authUser);
        return cartItem;
    }

    public List<Item> getItemsFromCart(UserDetails authUser, Integer userId) {
        AuthService.authorizeAction(userId, authUser);
        return getItemsFromCart(userId);
    }

    public List<CartItem> getCart(UserDetails authUser, Integer userId) {
        AuthService.authorizeAction(userId, authUser);
        return getCart(userId);
    }

    public void save(UserDetails authUser, CartItem cartItem) {
        AuthService.authorizeAction(cartItem.getUserId(), authUser);
        save(cartItem);
    }

    public void update(UserDetails authUser, CartItem cartItem) {
        AuthService.authorizeAction(cartItem.getUserId(), authUser);
        update(cartItem);
    }

    public void delete(UserDetails authUser, Integer id) {
        CartItem cartItem = getById(id);
        AuthService.assertNull(cartItem, authUser);
        AuthService.authorizeAction(cartItem.getUserId(), authUser);
        delete(id);
    }

    public void emptyCart(UserDetails authUser, Integer userId) {
        AuthService.authorizeAction(userId, authUser);
        emptyCart(userId);
    }
}
