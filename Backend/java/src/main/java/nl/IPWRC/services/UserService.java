package nl.IPWRC.services;

import nl.IPWRC.exceptions.InvalidInputException;
import nl.IPWRC.models.User;
import nl.IPWRC.persistance.UserDao;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserService implements CrudService<User> {

    private static UserService mInstance;
    private final UserDao userDao;

    public UserService() {
        userDao = UserDao.getInstance();
    }

    public static UserService getInstance() {
        if (mInstance == null) {
            mInstance = new UserService();
        }
        return mInstance;
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User getById(Integer id) {
        return userDao.getById(id);
    }

    public User getById(Integer id, User authUser) {
        User user = getById(id);

        AuthService.assertNull(user, authUser);
        AuthService.authorizeAction(id, authUser);

        return userDao.getById(id);
    }

    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public User getByEmail(String email, User authUser) {
        User user = getByEmail(email);

        AuthService.assertNull(user, authUser);
        AuthService.authorizeAction(user.getId(), authUser);

        return user;
    }

    @Override
    public User save(User user) throws InvalidInputException {
        validate(user, false);

        user.setPassword(
                BCrypt.hashpw(
                        user.getPassword(),
                        BCrypt.gensalt(10)
                )
        );
        user.setIsAdmin(false);
        user.setId(null);

        return userDao.save(user);
    }

    @Override
    public void update(User user) throws InvalidInputException {
        validate(user, true);

        User oldUser = getById(user.getId());

        if (! BCrypt.checkpw(user.getPassword(), oldUser.getPassword())){
            user.setPassword(
                    BCrypt.hashpw(
                            user.getPassword(),
                            BCrypt.gensalt(10)
                    )
            );
        }

        userDao.update(user);
    }

    public void update(User updatedUser, User authUser) throws InvalidInputException {
        AuthService.authorizeAction(updatedUser.getId(), authUser);

        update(updatedUser);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }

    public void delete(Integer id, User authUser) {
        AuthService.authorizeAction(id, authUser);

        delete(id);
    }

    public boolean emailExists(String email) {
        return userDao.getByEmail(email) != null;
    }

    @Override
    public void validate(User user, boolean update) throws InvalidInputException {
        ValidationService.validate(errors -> {
            if (! ValidationService.isValidEmail(user.getEmail()))
                errors.add("Invalid email");
            if (!update && emailExists(user.getEmail()))
                errors.add("Email already exists");
            if (!update && ! ValidationService.isValidPassword(user.getPassword()))
                errors.add("Invalid password");
        });
    }
}
