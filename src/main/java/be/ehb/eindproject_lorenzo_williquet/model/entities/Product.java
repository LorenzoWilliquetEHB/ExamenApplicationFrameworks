package be.ehb.eindproject_lorenzo_williquet.model.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 64)
    @NotNull(message = "Category can't be null")
    private String category;
    @Column(nullable = false, length = 64)
    @NotNull(message = "ProductName can't be null")
    private String productName;
    @Column(nullable = false, length = 64)
    @NotNull(message = "ProductDescription can't be null")
    private String productDescription;
    @Column(nullable = false, length = 64)
    @NotNull(message = "Price can't be null")
    private double price;

    public Product() {
    }

    public Product(String category, String productName, String productDescription, double price) {
        this.category = category;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
