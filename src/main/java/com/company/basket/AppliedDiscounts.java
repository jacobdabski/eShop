package com.company.basket;

import com.company.product.ProductBatch;
import com.company.product.ProductType;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A helper semi-pojo that works out how much of the total cost should be decreased with the
 * given discount percentage and the amount of items that this discount will be applied to.
 */
public class AppliedDiscounts {

    private int discounts;
    private int percentageOff;
    private BigDecimal priceModifier;

    public static AppliedDiscounts create(int discounts, int percentageOff, BigDecimal price){
        BigDecimal priceModifier = percentageOff == 0 ? price :
                price.multiply(new BigDecimal((double)percentageOff/100)).multiply(new BigDecimal(discounts));

        priceModifier = priceModifier.setScale(2, RoundingMode.HALF_UP);
        return new AppliedDiscounts(discounts, percentageOff, priceModifier);
    }

    private  AppliedDiscounts(int discounts, int percentageOff, BigDecimal priceModifier){
        this.discounts = discounts;
        this.percentageOff = percentageOff;
        this.priceModifier = priceModifier;
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