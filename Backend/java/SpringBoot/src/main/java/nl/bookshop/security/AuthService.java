package nl.bookshop.security;

import nl.bookshop.exceptions.ActionForbiddenException;
import nl.bookshop.models.User;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthService {
    public static void authorizeAction(Integer userId, UserDetails userDetails) {
        User authUser = User.fromUserDetails(userDetails);
        if (!authUser.isAdmin() && !authUser.getId().equals(userId))
            throw new ActionForbiddenException();
    }

    public static void assertNull(Object object, UserDetails userDetails) {
        User authUser = User.fromUserDetails(userDetails);
        if (object == null && !authUser.isAdmin())
            throw new ActionForbiddenException();
    }
}
