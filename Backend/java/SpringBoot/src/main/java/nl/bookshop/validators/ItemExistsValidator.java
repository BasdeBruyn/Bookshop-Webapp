package nl.bookshop.validators;

import nl.bookshop.data.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ItemExistsValidator implements ConstraintValidator<ItemExists, Integer> {

    private final ItemService itemService;

    public ItemExistsValidator() {
        itemService = null;
    }

    @Autowired
    public ItemExistsValidator(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public boolean isValid(Integer itemId, ConstraintValidatorContext context) {
        if (itemService == null)
            return true;

        return itemService.existsById(itemId);
    }
}
