package com.company.basket;

import com.company.product.ProductBatch;
import com.company.product.ProductType;

import java.math.BigDecimal;

public class AppliedDiscounts {

    private int discounts;
    private int percentageOff;
    private BigDecimal priceModifier;

    private  AppliedDiscounts(int discounts, int percentageOff, BigDecimal priceModifier){
        this.discounts = discounts;
        this.percentageOff = percentageOff;
        this.priceModifier = priceModifier;
    }

    public static AppliedDiscounts of(int discounts, int percentageOff, BigDecimal price){
        BigDecimal priceModifier = price.multiply(new BigDecimal((double)percentageOff/100)).multiply(new BigDecimal(discounts));
        return new AppliedDiscounts(discounts, percentageOff, priceModifier);
    }

    public int getDiscounts() {
        return discounts;
    }

    public void setDiscounts(int discounts) {
        this.discounts = discounts;
    }

    public int getPercentageOff() {
        return percentageOff;
    }

    public void setPercentageOff(int percentageOff) {
        this.percentageOff = percentageOff;
    }

    public BigDecimal getPriceModifier() {
        return priceModifier;
    }

    public void setPriceModifier(BigDecimal priceModifier) {
        this.priceModifier = priceModifier;
    }
}
