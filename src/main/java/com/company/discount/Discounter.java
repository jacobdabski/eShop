package com.company.discount;

import com.company.basket.AppliedDiscounts;
import com.company.basket.BasketProduct;
import com.company.basket.IBasketDiscounter;
import com.company.product.ProductBatch;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A Discount of a given priority.
 * It is responsible for making sure that the discount
 * will not be applied to any ProductBatches that have already been
 * discounted.
 * The specific discounting logic is performed but the IDiscountFunction
 * implementation.
 */
public class Discounter implements IBasketDiscounter {

    private UUID discountID;
    private int priority;

    private IDiscountFunction function;

    public Discounter(@NotNull IDiscountFunction function, int priority){
        setIDiscountFunction(function);
        this.priority = priority;
    }

    /**
     * Applies discounts by delegating to the IDiscountFunction
     * @param products the products in basket
     * @param appliedDiscounts the modified map of discounts to be applied
     */
    public void apply(List<BasketProduct> products, Map<ProductBatch, AppliedDiscounts> appliedDiscounts){
        Map<ProductBatch, AppliedDiscounts> discountsToApply = function.apply(products);
        for (ProductBatch batch : discountsToApply.keySet()){
            if(!appliedDiscounts.containsKey(batch)){
                appliedDiscounts.put(batch, discountsToApply.get(batch));
            }
        }
    }

    public void setIDiscountFunction(@NotNull  IDiscountFunction function){
        if(function == null){
            throw new IllegalArgumentException();
        }
        this.function = function;
    }

    public UUID getDiscountID() {
        return discountID;
    }

    public void setDiscountID(UUID discountID) {
        this.discountID = discountID;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}