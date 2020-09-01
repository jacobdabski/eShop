package com.company.product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A ProductBatch represents a priced line of Items
 * in stock,  with a shared expiry Date.
 * These items all are of the same ProductType.
 */
public class ProductBatch {

//  @Id
//  @GeneratedValue
    private UUID id;

//  @OneToOne(cascade = CascadeType.ALL)
//  @JoinColumn(name = "product_type_id", referencedColumnName = "id")
    private ProductType type;

    private LocalDate expiry;

    private int quantityInStock;


    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantityInStock;
    }

    public void setQuantity(int quantity) {
        this.quantityInStock = quantity;
    }

    public void amendQuantity(int modifier){
        this.quantityInStock += modifier;
    }

    public String getProductTypeName(){
        return getType().getName();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDate expiry) {
        this.expiry = expiry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductBatch product = (ProductBatch) o;
        return Objects.equals(type, product.type) &&
                Objects.equals(expiry, product.expiry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, expiry);
    }

    public BigDecimal getPrice(){
        return type.getPrice();
    }
}