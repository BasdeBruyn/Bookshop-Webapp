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
@Constraint(validatedBy = ItemExistsValidator.class)
@Documented
public @interface ItemExists {

    String message() default ITEM_DOES_NOT_EXIST;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
