package com.company.basket;

import com.company.product.ProductBatch;

import java.math.BigDecimal;
import java.util.*;

/**
 * A Basket represents all ProductBatches with their quantities added to the basket,
 * a list of discounts applied to each ProductBatch present,
 * With methods to calculate the subTotal and Total (with discounts applied)
 */
public class Basket {

    private List<BasketProduct> products;
    private Map<ProductBatch,AppliedDiscounts> discounts;
    private BigDecimal subTotal;
    private BigDecimal total;

    public Basket(){
        products = new ArrayList<>();
        discounts = new HashMap<>();
    }

    public void addProduct(BasketProduct product){
        this.products.add(product);
        subTotal = null;
        total = null;
    }

    public void applyDiscounts(IBasketDiscounter discounter){
        if(!products.isEmpty())
            discounter.apply(products, discounts);
    }

    /**
     * Returns a copy of the discounts data for viewing
     * @return
     */
    public Map<ProductBatch, AppliedDiscounts> getDiscounts(){
        return new HashMap<ProductBatch, AppliedDiscounts>(discounts);
    }

    /**
     * Calculates the total price of each products in basket
     * @return
     */
    public BigDecimal getSubTotal(){
        if(subTotal == null) {
            subTotal = products.stream()
                    .map(product -> product.getBatchPrice())
                    .reduce(new BigDecimal(0), (price1, price2) -> price1.add(price2));
        }
        return subTotal;
    }

    /**
     * Calculates the Total cost with of each Product in basket minus the
     * total discounts applied
     * @return
     */
    public BigDecimal getTotal(){
        final BigDecimal subTotal = getSubTotal();
        final BigDecimal discountedTotal = discounts.values().stream()
                .map(discount -> discount.getPriceModifier())
                .reduce(new BigDecimal(0), (price1, price2) -> price1.add(price2));
        BigDecimal result = subTotal.subtract(discountedTotal);
        if(result.floatValue() < 0){
            throw new IllegalStateException();
        }
        return result;
    }

}
