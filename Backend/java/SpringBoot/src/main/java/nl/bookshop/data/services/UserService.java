package nl.bookshop.data.services;

import nl.bookshop.models.User;
import nl.bookshop.exceptions.BadRequestException;
import nl.bookshop.persistance.UserRepository;
import nl.bookshop.security.AuthService;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

import static nl.bookshop.data.services.ServiceStrings.*;

@Service
public class UserService implements CrudService<User> {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        Iterator<User> users = userRepository.findAll().iterator();
        return IteratorUtils.toList(users);
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        if (!userRepository.existsByEmail(user.getEmail()))
            return userRepository.save(user);
        else
            throw new BadRequestException(EMAIL_ALREADY_EXISTS);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }


    public User getById(UserDetails authUser, Integer id) {
        AuthService.authorizeAction(id, authUser);
        return getById(id);
    }

    public User getByEmail(UserDetails authUser, String email) {
        User user = getByEmail(email);
        AuthService.assertNull(user, authUser);
        AuthService.authorizeAction(user.getId(), authUser);
        return user;
    }

    public void update(UserDetails authUser, User user) {
        AuthService.authorizeAction(user.getId(), authUser);
        update(user);
    }

    public void delete(UserDetails authUser, Integer id) {
        AuthService.authorizeAction(id, authUser);
        delete(id);
    }
}
