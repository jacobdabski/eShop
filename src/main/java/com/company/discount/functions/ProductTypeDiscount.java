package com.company.discount.functions;

import com.company.basket.AppliedDiscounts;
import com.company.basket.BasketProduct;
import com.company.product.ProductBatch;
import com.company.product.ProductSelector;

import java.util.List;
import java.util.Map;

/**
 * A specific discount that is to be applied to any items that are of the given ProductType
 */
public class ProductTypeDiscount extends Discount {

    private ProductSelector selector;
    private int percentage;

    public ProductTypeDiscount(ProductSelector selector, int percentage){
        this.percentage = percentage;
        this.selector = selector;
    }

    @Override
    protected void doApply(List<BasketProduct> products, Map<ProductBatch, AppliedDiscounts> discounts) {
        products.stream().filter(product -> selector.matches(product.getProductType())).forEach(product -> {
            discounts.put(product.getProduct(), AppliedDiscounts.create(product.getQuantity(), percentage, product.getProductType().getPrice()));
        });
    }
}