package com.company.discount.functions.conditions;

import com.company.basket.BasketProduct;

import java.util.List;
import java.util.function.Predicate;

public interface BasketCondition extends Predicate<List<BasketProduct>> {
}
