package nl.bookshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import nl.bookshop.validators.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

import static nl.bookshop.models.ModelStrings.*;
import static nl.bookshop.validators.ValidationStrings.NEGATIVE_TOTAL_PRICE;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ORDERS)
public class Order {

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Name
    @NotEmpty
    private String firstName;

    @NonNull
    @Name
    @NotEmpty
    private String lastName;

    @NonNull
    @PostalCode
    @NotEmpty
    private String postalCode;

    @NonNull
    @HouseNr
    @NotEmpty
    private String houseNr;

    @NonNull
    @Name
    @NotEmpty
    private String streetName;

    @NonNull
    @Name
    @NotEmpty
    private String residence;

    @NonNull
    @PhoneNr
    @NotEmpty
    private String phoneNr;

    @NonNull
    @NotNull
    @Min(value = 0, message = NEGATIVE_TOTAL_PRICE)
    private Integer totalPrice;


    @NonNull
    @NotNull
    @UserExists
    private Integer userId;

    @JsonIgnore
    private Timestamp curDate = new Timestamp(System.currentTimeMillis());
}
