package nl.IPWRC.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "item_order")
public class Order {
    @Id
    @JsonProperty
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @NotNull @JsonProperty private String firstName;
    @NotNull @JsonProperty private String lastName;
    @NotNull @JsonProperty private String postalCode;
    @NotNull @JsonProperty private String houseNr;
    @NotNull @JsonProperty private String streetName;
    @NotNull @JsonProperty private String residence;
    @NotNull @JsonProperty private String phoneNr;
    @NotNull @JsonProperty private Integer totalPrice;
    @JsonIgnore private Timestamp curDate;
    @NotNull @JsonProperty private Integer userId;

    public Order() {}

    public Order(Integer id, String firstName, String lastName, String postalCode,
                 String houseNr, String streetName, String residence, String phoneNr, Integer totalPrice, Timestamp curDate, Integer userId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
        this.houseNr = houseNr;
        this.streetName = streetName;
        this.residence = residence;
        this.phoneNr = phoneNr;
        this.totalPrice = totalPrice;
        this.curDate = curDate;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return new EqualsBuilder()
                .append(id, order.id)
                .append(firstName, order.firstName)
                .append(lastName, order.lastName)
                .append(postalCode, order.postalCode)
                .append(houseNr, order.houseNr)
                .append(streetName, order.streetName)
                .append(residence, order.residence)
                .append(phoneNr, order.phoneNr)
                .append(totalPrice, order.totalPrice)
                .append(curDate, order.curDate)
                .append(userId, order.userId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(firstName)
                .append(lastName)
                .append(postalCode)
                .append(houseNr)
                .append(streetName)
                .append(residence)
                .append(phoneNr)
                .append(totalPrice)
                .append(curDate)
                .append(userId)
                .toHashCode();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getHouseNr() { return houseNr; }
    public void setHouseNr(String houseNr) { this.houseNr = houseNr; }
    public String getStreetName() { return streetName; }
    public void setStreetName(String streetName) { this.streetName = streetName; }
    public String getResidence() { return residence; }
    public void setResidence(String residence) { this.residence = residence; }
    public String getPhoneNr() { return phoneNr; }
    public void setPhoneNr(String phoneNr) { this.phoneNr = phoneNr; }
    public Integer getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Integer totalPrice) { this.totalPrice = totalPrice; }
    public Timestamp getCurDate() { return curDate; }
    public void setCurDate(Timestamp curDate) { this.curDate = curDate; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", houseNr='" + houseNr + '\'' +
                ", streetName='" + streetName + '\'' +
                ", residence='" + residence + '\'' +
                ", phoneNr='" + phoneNr + '\'' +
                ", totalPrice=" + totalPrice +
                ", curDate=" + curDate +
                ", userId=" + userId +
                '}';
    }
}
