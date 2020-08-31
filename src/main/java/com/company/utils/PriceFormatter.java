package com.company.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class PriceFormatter {

    private static NumberFormat ukCostFormat = NumberFormat.getCurrencyInstance(Locale.UK);
    static{
        ukCostFormat.setMinimumFractionDigits( 2 );
        ukCostFormat.setMaximumFractionDigits( 2 );
    }


    public static String format(BigDecimal price){
        return ukCostFormat.format(price.doubleValue());
    }

}
