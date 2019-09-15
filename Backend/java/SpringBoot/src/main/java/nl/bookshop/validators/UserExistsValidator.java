package nl.bookshop.validators;

import nl.bookshop.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UserExistsValidator implements ConstraintValidator<UserExists, Integer> {

    private final UserService userService;

    public UserExistsValidator() {
        userService = null;
    }

    @Autowired
    public UserExistsValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(Integer userId, ConstraintValidatorContext context) {
        if (userService == null)
            return true;

        return userService.existsById(userId);
    }
}
