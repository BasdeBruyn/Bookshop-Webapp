package nl.bookshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.bookshop.validators.ItemExists;
import nl.bookshop.validators.UserExists;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static nl.bookshop.models.ModelStrings.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = CART_ITEMS)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @UserExists
    private Integer userId;

    @NotNull
    @ItemExists
    private Integer itemId;

}
