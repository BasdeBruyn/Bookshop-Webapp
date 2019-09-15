package nl.bookshop.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static nl.bookshop.validators.ValidationStrings.POSTAL_CODE_REGEX;

public class PostalCodeValidator implements ConstraintValidator<PostalCode, String> {
    @Override
    public boolean isValid(String postalCode, ConstraintValidatorContext context) {
        return postalCode.matches(POSTAL_CODE_REGEX);
    }
}
