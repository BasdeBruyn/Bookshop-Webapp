package nl.bookshop.security;

import nl.bookshop.models.User;
import nl.bookshop.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static nl.bookshop.security.SecurityStrings.*;
import static nl.bookshop.security.SecurityStrings.ROLE_ADMIN;

@Service
public class DetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public DetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException(email + " was not found");
        }
        return user.createAuthUser(
                user.isAdmin() ? ROLE_ADMIN : ROLE_CUSTOMER);
    }

}
