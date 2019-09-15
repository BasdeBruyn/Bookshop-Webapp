package nl.bookshop.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static nl.bookshop.validators.ValidationStrings.NAME_REGEX;

public class NameValidator implements ConstraintValidator<Name, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return name.matches(NAME_REGEX);
    }
}
