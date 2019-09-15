package nl.bookshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.*;

import static nl.bookshop.models.ModelStrings.*;
import static nl.bookshop.validators.ValidationStrings.NEGATIVE_PRICE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ITEMS)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Min(value = 0, message = NEGATIVE_PRICE)
    private Integer price;

    @NotNull
    @Length(max = 127)
    private String name;

    @NotNull
    @Length(max = 4095)
    private String description;

    @NotEmpty
    @URL
    @Length(max = 511)
    private String url;

    @NotNull
    private boolean available;
}
