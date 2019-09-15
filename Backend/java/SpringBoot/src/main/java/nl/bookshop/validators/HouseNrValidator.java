package nl.bookshop.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static nl.bookshop.validators.ValidationStrings.HOUSE_NR_REGEX;

public class HouseNrValidator implements ConstraintValidator<HouseNr, String> {
    @Override
    public boolean isValid(String houseNr, ConstraintValidatorContext context) {
        return houseNr.matches(HOUSE_NR_REGEX);
    }
}
