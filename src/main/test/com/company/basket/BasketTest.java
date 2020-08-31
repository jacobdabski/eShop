package com.company.basket;

import com.company.product.ProductBatch;
import com.company.product.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BasketTest {

    private Basket basket;

    @BeforeEach
    public void setup(){
        basket = new Basket();
    }

    @Test
    void testEmptyBasket(){
        Basket basket = new Basket();
        assertTotals(new BigDecimal(0));
    }

    @Test
    void testTwoSubtotals(){
        BigDecimal firstPrice = new BigDecimal(("12.5"));
        basket.addProduct(mockBasketProduct(firstPrice));
        assertTotals(firstPrice);

        BigDecimal secondPrice = new BigDecimal("100.1");

        basket.addProduct(mockBasketProduct((secondPrice)));
        assertTotals(new BigDecimal("112.6"));
    }

    @Test
    void testDiscountedBasket(){
        basket.addProduct(mockBasketProduct(new BigDecimal((100))));
        basket.addProduct(mockBasketProduct(new BigDecimal(("10.5"))));

        basket.applyDiscounts((batches, appliedDiscounts)-> {
            appliedDiscounts.put(createProductStub("apples"), mockAppliedDiscount(new BigDecimal(25)));
            appliedDiscounts.put(createProductStub("bananas"), mockAppliedDiscount((new BigDecimal("15.5"))));
        });

        assertEquals(basket.getSubTotal().compareTo(new BigDecimal("110.5")), 0);
        assertEquals(basket.getTotal().compareTo(new BigDecimal(70)), 0);
    }

    @Test
    void testApplyDiscountsNoProducts(){
        IBasketDiscounter mock = Mockito.mock(IBasketDiscounter.class);
        basket.applyDiscounts(mock);
        Mockito.verifyNoInteractions(mock);
    }

    @Test
    void testInvalidDiscountsTotalLessThanZeroException(){
        //todo
    }

    private void assertTotals(BigDecimal result){
        assertEquals(basket.getSubTotal().compareTo(result), 0);
        assertEquals(basket.getTotal().compareTo(result), 0);
    }

    private ProductBatch createProductStub(String name){
        ProductBatch batch = new ProductBatch();
        ProductType type = new ProductType(name, null, null);
        batch.setType(type);
        return batch;
    }

    private AppliedDiscounts mockAppliedDiscount(BigDecimal priceModifier){
        AppliedDiscounts discounts = Mockito.mock(AppliedDiscounts.class);
        Mockito.when(discounts.getPriceModifier()).thenReturn(priceModifier);
        return discounts;
    }

    private BasketProduct mockBasketProduct(BigDecimal price){
        BasketProduct product = Mockito.mock(BasketProduct.class);
        Mockito.when(product.getBatchPrice()).thenReturn(price);
        return product;
    }
}