package nl.IPWRC.services;

import nl.IPWRC.exceptions.InvalidInputException;
import nl.IPWRC.models.CartItem;
import nl.IPWRC.models.Item;
import nl.IPWRC.models.User;
import nl.IPWRC.persistance.CartDao;
import nl.IPWRC.persistance.ItemDao;
import nl.IPWRC.persistance.UserDao;

import javax.ws.rs.NotAuthorizedException;
import java.util.List;

public class CartService implements CrudService<CartItem> {
    private static CartService mInstance;
    private final CartDao cartDao;
    private final UserDao userDao;
    private final ItemDao itemDao;

    public CartService() {
        cartDao = CartDao.getInstance();
        userDao = UserDao.getInstance();
        itemDao = ItemDao.getInstance();
    }

    public static CartService getInstance() {
        if (mInstance == null) {
            mInstance = new CartService();
        }
        return mInstance;
    }

    @Override
    public List<CartItem> getAll() {
        return cartDao.getAll();
    }

    @Override
    public CartItem getById(Integer id) {
        return cartDao.getById(id);
    }

    public CartItem getById(Integer id, User authUser) {
        CartItem cartItem = getById(id);

        AuthService.assertNull(cartItem, authUser);
        AuthService.authorizeAction(cartItem.getUserId(), authUser);

        return cartItem;
    }

    public List<CartItem> getCart(Integer userId) {
        return cartDao.getCart(userId);
    }

    public List<CartItem> getCart(Integer userId, User authUser) {
        AuthService.authorizeAction(userId, authUser);

        return getCart(userId);
    }

    public Item[] getItemsFromCart(Integer userId){
        Integer[] itemIds = cartDao.getItemIdsFromCart(userId);
        if (itemIds == null)
            return null;
        return itemDao.getAllByIds(itemIds);
    }

    public Item[] getItemsFromCart(Integer userId, User authUser){
        AuthService.authorizeAction(userId, authUser);

        return getItemsFromCart(userId);
    }

    @Override
    public CartItem save(CartItem cartItem) throws InvalidInputException {
        validate(cartItem, false);

        return cartDao.save(cartItem);
    }

    public CartItem save(CartItem cartItem, User authUser) throws InvalidInputException {
        AuthService.authorizeAction(cartItem.getUserId(), authUser);

        return save(cartItem);
    }

    @Override
    public void update(CartItem cartItem) throws InvalidInputException {
        validate(cartItem, true);

        cartDao.update(cartItem);
    }

    public void update(CartItem cartItem, User authUser) throws InvalidInputException {
        AuthService.authorizeAction(cartItem.getUserId(), authUser);

        update(cartItem);
    }

    @Override
    public void delete(CartItem cartItem) {
        cartDao.delete(cartItem);
    }

    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }

    public void delete(Integer id, User authUser) {
        CartItem cartItem = getById(id);

        AuthService.authorizeAction(cartItem.getUserId(), authUser);

        delete(cartItem);
    }

    public void emptyCart(Integer userId, User authUser) {
        AuthService.authorizeAction(userId, authUser);

        cartDao.emptyCart(userId);
    }

    @Override
    public void validate(CartItem cartItem, boolean update) throws InvalidInputException {
        ValidationService.validate(errors -> {
            if (userDao.getById(cartItem.getUserId()) == null)
                errors.add("User id doesn't exist");
            if (itemDao.getById(cartItem.getItemId()) == null)
                errors.add("Item id doesn't exist");
        });
    }
}
