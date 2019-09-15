package nl.bookshop.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static nl.bookshop.validators.ValidationStrings.*;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PostalCodeValidator.class)
@Documented
public @interface PostalCode {

    String message() default INVALID_POSTAL_CODE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
