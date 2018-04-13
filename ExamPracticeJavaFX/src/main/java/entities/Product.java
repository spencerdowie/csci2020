package entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {
    //Since we are specifying a unique, non-zero ID ourselves, we'll eschew the
    //sequence generator here in favour of the "@Id"-only annotation.
    @Id
    private long upc;
    private String name;
    private double price;
    private String description;

    public Product() {}

    public Product(long upc, String name, double price, String description) {
        this.upc = upc;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public long getUpc() { return upc; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
}
