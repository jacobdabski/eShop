package com.company.basket;

import com.company.product.ProductBatch;

import java.util.List;
import java.util.Map;

public interface IBasketDiscounter {

    void apply(List<BasketProduct> products, Map<ProductBatch,AppliedDiscounts> appliedDiscounts);

}
