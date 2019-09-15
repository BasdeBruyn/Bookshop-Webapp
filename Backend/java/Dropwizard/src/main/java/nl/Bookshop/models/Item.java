package nl.Bookshop.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Item {
    @Id
    @JsonProperty
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @JsonProperty
    private Integer price;
    @NotNull
    @JsonProperty
    private String name;
    @JsonProperty
    private String description;
    @NotNull
    @JsonProperty
    private String url;
    @NotNull
    @JsonProperty
    private boolean available;

    public Item() {}

    public Item(Integer price, String name, String description, String url, boolean available) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.url = url;
        this.available = available;
    }

    public Item(Integer id, Integer price, String name, String description, String url, boolean available) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.url = url;
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        return new EqualsBuilder()
                .append(available, item.available)
                .append(id, item.id)
                .append(price, item.price)
                .append(name, item.name)
                .append(description, item.description)
                .append(url, item.url)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(price)
                .append(name)
                .append(description)
                .append(url)
                .append(available)
                .toHashCode();
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) { this.id = id; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", available=" + available +
                '}';
    }
}
