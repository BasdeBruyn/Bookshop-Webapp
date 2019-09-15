package nl.bookshop.controllers;

import nl.bookshop.models.Item;
import nl.bookshop.data.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static nl.bookshop.security.SecurityStrings.ROLE_ADMIN;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getItems() {
        return itemService.getAll();
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable("id") Integer id) {
        return itemService.getById(id);
    }

    @PostMapping
    @Secured(ROLE_ADMIN)
    public void saveItem(@Valid @RequestBody Item item) {
        itemService.save(item);
    }

    @PutMapping
    @Secured(ROLE_ADMIN)
    public void updateItem(@Valid @RequestBody Item item) {
        itemService.update(item);
    }

    @DeleteMapping("/{id}")
    @Secured(ROLE_ADMIN)
    public void deleteItem(@PathVariable Integer id) {
        itemService.delete(id);
    }
}
