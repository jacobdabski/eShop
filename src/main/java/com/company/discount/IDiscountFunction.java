package com.company.discount;

import com.company.basket.AppliedDiscounts;
import com.company.basket.BasketProduct;
import com.company.product.ProductBatch;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface IDiscountFunction extends Function<List<BasketProduct>, Map<ProductBatch, AppliedDiscounts>> {
    @Override
    Map<ProductBatch, AppliedDiscounts> apply(List<BasketProduct> basketItems);
}
