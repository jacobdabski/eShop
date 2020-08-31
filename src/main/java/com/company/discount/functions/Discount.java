package com.company.discount.functions;

import com.company.basket.AppliedDiscounts;
import com.company.basket.BasketProduct;
import com.company.discount.IDiscountFunction;
import com.company.discount.functions.conditions.BasketCondition;
import com.company.product.ProductBatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A Discount function can be used to build more complex discount logic, using
 * Predicates that are applied to either the entire list or each element in the list
 */
public abstract class Discount implements IDiscountFunction {

    private BasketCondition basketCondition = p -> true;
    private Predicate<BasketProduct> productCondition = p -> true;


    public void setBasketCondition(BasketCondition basketCondition) {
        this.basketCondition = basketCondition;
    }

    public void setSpecificCondition(Predicate<BasketProduct> productCondition) {
        this.productCondition = productCondition;
    }

    @Override
    public Map<ProductBatch, AppliedDiscounts> apply(List<BasketProduct> basketItems) {
        Map<ProductBatch, AppliedDiscounts> discounts = new HashMap<>();
        if(basketCondition.test(basketItems)){
            doApply(basketItems.stream()
                    .filter((product)-> productCondition.test(product))
                    .collect(Collectors.toList()), discounts);
        }
        return discounts;
    }

    protected abstract void doApply(List<BasketProduct> products, Map<ProductBatch, AppliedDiscounts> discounts);
}