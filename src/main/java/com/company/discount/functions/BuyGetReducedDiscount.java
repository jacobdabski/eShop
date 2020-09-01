package com.company.discount.functions;

import com.company.basket.AppliedDiscounts;
import com.company.basket.BasketProduct;
import com.company.product.ProductBatch;
import com.company.product.ProductType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A ByXGet1Free discount
 */
public class BuyGetReducedDiscount extends Discount {

    private ProductType buy;
    private int quantity;
    private ProductType get;
    private int percentage;

    /**
     * @param buy - the type of Products that need to be present
     * @param quantity -the number of Products of the specified ProductType that need to be present
     *                 in order for the discount to be applied
     * @param get - the type of Products that are to be discounted
     * @param percentage - how much of discount to apply
     */
    public BuyGetReducedDiscount(ProductType buy, int quantity, ProductType get, int percentage){
        this.buy = buy;
        this.quantity = quantity;
        this.get = get;
        this.percentage = percentage;
    }

    @Override
    public void doApply(List<BasketProduct> basketItems, Map<ProductBatch, AppliedDiscounts> discounts) {
        int quantityInBasket = basketItems.stream()
                .filter((product)-> product.getProductType().equals(buy))
                .map(BasketProduct::getQuantity)
                .reduce(Integer::sum).orElse(0);

        int quantityToBeDiscounted = quantityInBasket / (buy != get ? quantity : quantity + 1);

        List<BasketProduct> getFreeProducts = basketItems.stream().
                filter((product)-> product.getProductType().equals(get)).collect(Collectors.toList());

        for(BasketProduct product : getFreeProducts){
            if(quantityToBeDiscounted == 0){
                return;
            }
            int quantityToBeDiscountedForProduct = Math.min(quantityToBeDiscounted, product.getQuantity());
            discounts.put(product.getProduct(), AppliedDiscounts.create(quantityToBeDiscountedForProduct, percentage, product.getProductPrice()));
            quantityToBeDiscounted -= quantityToBeDiscountedForProduct;
        }
    }
}