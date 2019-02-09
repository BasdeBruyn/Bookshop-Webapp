package nl.IPWRC.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Item {
    @Id
    @JsonProperty
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @JsonProperty
    private Integer price;
    @NotNull
    @JsonProperty
    private String name;
    @JsonProperty
    private String description;
    @NotNull
    @JsonProperty
    private String uri;
    @NotNull
    @JsonProperty
    private boolean available;

    public Item() {}

    public Item(Integer price, String name, String description, String uri, boolean available) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.uri = uri;
        this.available = available;
    }

    public Item(Integer id, Integer price, String name, String description, String uri, boolean available) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.description = description;
        this.uri = uri;
        this.available = available;
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
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
