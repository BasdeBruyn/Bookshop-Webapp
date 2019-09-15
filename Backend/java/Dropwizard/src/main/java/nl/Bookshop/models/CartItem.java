package nl.Bookshop.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @JsonProperty
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @JsonProperty
    private Integer userId;
    @JsonProperty
    @NotNull
    private Integer itemId;

    public CartItem() {}

    public CartItem(Integer id, Integer userId, Integer itemId) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CartItem cartItem = (CartItem) o;

        return new EqualsBuilder()
                .append(id, cartItem.id)
                .append(userId, cartItem.userId)
                .append(itemId, cartItem.itemId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(userId)
                .append(itemId)
                .toHashCode();
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getItemId() { return itemId; }
    public void setItemId(Integer itemId) { this.itemId = itemId; }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
}
