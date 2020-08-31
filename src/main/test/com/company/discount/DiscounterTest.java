package com.company.discount;

import com.company.basket.AppliedDiscounts;
import com.company.basket.BasketProduct;
import com.company.product.ProductBatch;
import com.company.product.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DiscounterTest {

    private Discounter discounter;

    @BeforeEach
    void setUp(){
        IDiscountFunction globalDiscount = (products) -> {
            Map<ProductBatch, AppliedDiscounts> resultingDiscounts = new HashMap<>();
            products.stream().forEach((product) -> {
                resultingDiscounts.put(product.getProduct(), AppliedDiscounts.of(product.getQuantity(), 50, new BigDecimal(100)));
            });
            return resultingDiscounts;
        };
        discounter = new Discounter(globalDiscount, 1);
    }

    @Test
    void applyGlobalDiscountToAllProducts(){
        Map<ProductBatch, AppliedDiscounts> appliedDiscounts = new HashMap<>();
        String[] productNames = {"bread", "milk", "coffee"};
        List<BasketProduct> basketItems = generateBasket(productNames);
        discounter.apply(basketItems, appliedDiscounts);

        for(String productName: productNames){
            assertTrue(appliedDiscounts.get(generateProductBatch(productName)).getPercentageOff() == 50);
        }
    }

    @Test
    void applyGlobalDiscountOneProductAlreadyDiscounted(){
        Map<ProductBatch, AppliedDiscounts> appliedDiscounts = new HashMap<>();
        appliedDiscounts.put(generateProductBatch("bread"), AppliedDiscounts.of(1, 75, new BigDecimal(100)));

        String[] productNames = {"bread", "milk", "coffee"};
        List<BasketProduct> basketItems = generateBasket(productNames);
        discounter.apply(basketItems, appliedDiscounts);

        assertTrue(appliedDiscounts.get(generateProductBatch("bread")).getPercentageOff() == 75);
        assertTrue(appliedDiscounts.get(generateProductBatch("milk")).getPercentageOff() == 50);
        assertTrue(appliedDiscounts.get(generateProductBatch("coffee")).getPercentageOff() == 50);

    }

    private List<BasketProduct> generateBasket(String ... productNames){
        List<BasketProduct> products = new ArrayList<>();
        for(String name: productNames){
            products.add(new BasketProduct(generateProductBatch(name), 1));
        }
        return products;
    }

    private ProductBatch generateProductBatch(String name){
        ProductBatch batch = new ProductBatch();
        ProductType type = new ProductType(name, null, null);
        batch.setType(type);
        return batch;
    }
}