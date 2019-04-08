package com.mrhmt.server.entities;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    private int id;

    @NotNull
    @Size(max=50)
    private String name;

    @NotNull
    @Size(max=150)
    private String description;

    @NotNull
    @Min(value = 0, message = "Must bigger than 0")
    @Max(value = Long.MAX_VALUE, message = "Must less than " + Long.MAX_VALUE)
    @Digits(integer = Integer.MAX_VALUE, fraction = 3)
    private double price;

    @NotNull
    @Min(value = 0)
    @Max(value = Long.MAX_VALUE, message = "Mus less than " + Long.MAX_VALUE)
    @Digits(integer = Integer.MAX_VALUE, fraction = 0)
    private int quantity = 0;

    @NotNull
    @Size(max = 150)
    private String thumbnail;

    @Column(name="is_valid")
    @NotNull
    private boolean isValid = true;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="supplier_id")
    private Supplier supplier;

    public Product(@NotNull @Size(max=50) String name, @NotNull @Size(max=150) String description, @NotNull @Min(value=0, message="Must bigger than 0") @Max(value=Long.MAX_VALUE, message="Must less than " + Long.MAX_VALUE) @Digits(integer=Integer.MAX_VALUE, fraction=3) double price, @NotNull @Min(value=0) @Max(value=Long.MAX_VALUE, message="Mus less than " + Long.MAX_VALUE) @Digits(integer=Integer.MAX_VALUE, fraction=0) int quantity, @NotNull @Size(max=150) String thumbnail, @NotNull boolean isValid, Date modified, Category category, Supplier supplier) {
        this.name=name;
        this.description=description;
        this.price=price;
        this.quantity=quantity;
        this.thumbnail=thumbnail;
        this.isValid=isValid;
        this.modified=modified;
        this.category=category;
        this.supplier=supplier;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price=price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity=quantity;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail=thumbnail;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid=valid;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified=modified;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category=category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier=supplier;
    }
}
