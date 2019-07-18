package nl.Bookshop.services;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;
import nl.Bookshop.exceptions.ActionUnauthorizedException;
import nl.Bookshop.models.User;
import nl.Bookshop.persistance.UserDao;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthService implements Authenticator<BasicCredentials, User>, Authorizer<User> {

    private final UserDao userDao;

    public AuthService() {
        this.userDao = UserDao.getInstance();
    }

    @Override
    @UnitOfWork
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        String email = basicCredentials.getUsername();
        String password = basicCredentials.getPassword();
        User user = userDao.getByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword()))
            return Optional.of(user);
        return Optional.empty();
    }

    @Override
    public boolean authorize(User user, String role) {
        return role.equals("admin") && user.getIsAdmin();
    }

    public static void authorizeAction(Integer userId, User authUser) {
        if (!authUser.getIsAdmin() && !authUser.getId().equals(userId))
            throw new ActionUnauthorizedException();
    }

    public static void assertNull(Object object, User authUser) {
        if (object == null && !authUser.getIsAdmin())
            throw new ActionUnauthorizedException();
    }
}
