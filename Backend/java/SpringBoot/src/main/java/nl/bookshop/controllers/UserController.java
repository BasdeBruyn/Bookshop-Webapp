package nl.bookshop.controllers;

import nl.bookshop.models.User;
import nl.bookshop.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static nl.bookshop.security.SecurityStrings.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Secured(ROLE_ADMIN)
    public List<User> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize(IS_AUTHENTICATED)
    public User getUser(@AuthenticationPrincipal UserDetails authUser,
                        @PathVariable Integer id) {
        return userService.getById(authUser, id);
    }
    
    @GetMapping("/email/{email}")
    @PreAuthorize(IS_AUTHENTICATED)
    public User getUserByEmail(@AuthenticationPrincipal UserDetails authUser,
                               @PathVariable String email) {
        return userService.getByEmail(authUser, email);
    }

    @PostMapping
    public void saveUser(@Valid @RequestBody User user) {
        userService.save(user);
    }

    @PutMapping
    @PreAuthorize(IS_AUTHENTICATED)
    public void updateUser(@AuthenticationPrincipal UserDetails authUser,
                           @Valid @RequestBody User user) {
        userService.update(authUser, user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(IS_AUTHENTICATED)
    public void deleteUser(@AuthenticationPrincipal UserDetails authUser,
                           @PathVariable Integer id) {
        userService.delete(authUser, id);
    }

    @GetMapping("/authenticate")
    @PreAuthorize(IS_AUTHENTICATED)
    public User authenticate(@AuthenticationPrincipal UserDetails authUser) {
        return User.fromUserDetails(authUser);
    }

}
