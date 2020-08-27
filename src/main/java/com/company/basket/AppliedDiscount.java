package com.company.basket;

import com.company.product.ProductType;

import java.math.BigDecimal;

public class AppliedDiscount {

    private ProductType type;
    private BigDecimal modifier;

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public BigDecimal getModifier() {
        return modifier;
    }

    public void setModifier(BigDecimal modifier) {
        this.modifier = modifier;
    }
}
