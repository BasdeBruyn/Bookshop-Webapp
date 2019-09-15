package nl.bookshop.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static nl.bookshop.validators.ValidationStrings.PHONE_NR_REGEX;

public class PhoneNrValidator implements ConstraintValidator<PhoneNr, String> {
    @Override
    public boolean isValid(String phoneNr, ConstraintValidatorContext context) {
        return phoneNr.matches(PHONE_NR_REGEX);
    }
}
