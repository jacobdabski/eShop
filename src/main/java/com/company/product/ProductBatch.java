package com.company.product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A ProductBatch represents a priced line of Items
 */
@Entity
public class ProductBatch {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_type_id", referencedColumnName = "id")
    private ProductType type;

    private int quantity;

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void amendQuantity(int quantity){
        setQuantity(getQuantity() + quantity);
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
}