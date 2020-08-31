package com.company.discount.functions.conditions;

import com.company.basket.BasketProduct;

import java.time.LocalDate;
import java.util.List;

public class ExpiringCondition implements BasketCondition{

    private LocalDate expiry;

    public ExpiringCondition(LocalDate expiry){
        this.expiry = expiry;
    }

    @Override
    public boolean test(List<BasketProduct> basketItems) {
        return LocalDate.now().isBefore(expiry);
    }
}
