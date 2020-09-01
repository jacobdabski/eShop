package com.company.product;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a specfic Product Type which is priced.
 *
 */
public class ProductType {

    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Represents the name of the type, ie 'Bread'
     */
    @NotNull
    private String name;

    /**
     * Represents the type  ie 'Granary'
     */
    @NotNull
    private String type;

    /**
     * Represents the brand if it there are separate brands
     */
    private String brand = "Default";

    /**
     * Represents the unit, ie 'Bag' or '10ml sachet'
     */
    @NotNull
    private String unit;

    @NotNull
    private BigDecimal price;

    public ProductType(String name, String type, String unit){
        this.name = name;
        this.type = type;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
        ProductType that = (ProductType) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, brand, unit);
    }
}