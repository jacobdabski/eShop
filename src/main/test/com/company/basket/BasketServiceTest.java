package com.company.basket;

import com.company.ValidationException;
import com.company.discount.Discounter;
import com.company.discount.DiscountsService;
import com.company.discount.functions.BuyGetReducedDiscount;
import com.company.discount.functions.ProductTypeDiscount;
import com.company.product.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasketServiceTest {

    @InjectMocks
    private BasketService service;

    @Mock
    private ProductService productService;

    @Mock
    private DiscountsService discountService;

    private ProductTypeDto bread = ProductTypeDto.builder().name("Bread").get();
    private ProductTypeDto milk = ProductTypeDto.builder().name("Milk").get();
    private ProductTypeDto soup = ProductTypeDto.builder().name("Soup").get();
    private ProductTypeDto apples = ProductTypeDto.builder().name("Apples").get();

    private List<BasketRequestDto> simpleRequest = new ArrayList() {{
        add(new BasketRequestDto(bread, 1));
        add(new BasketRequestDto(milk, 1));
        add(new BasketRequestDto(apples, 1));
    }};

    @BeforeEach
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void priceBasketInsufficientItems() {
        Mockito.when(productService.getProductBatches(bread))
                .thenReturn(Collections.singletonList(generateProductBatch(bread, 2, new BigDecimal(100))));

        List<BasketRequestDto> request = Collections.singletonList(new BasketRequestDto(bread, 3));
        assertThrows(ValidationException.class, () -> service.priceBasket(request));
    }

    @Test
    void priceBasketNoDiscounts(){
        prepareProductBatches(10);
        Basket basket = service.priceBasket(simpleRequest);
        assertEquals(0, basket.getSubTotal().compareTo(new BigDecimal("3.10")));
        assertEquals(0, basket.getTotal().compareTo(new BigDecimal("3.10")));
    }

    @Test
    void priceBasketWithDiscounts(){
        prepareProductBatches(10);

        List<Discounter> discounts = new ArrayList<>();
        discounts.add(new Discounter(new ProductTypeDiscount(ProductSelector.builder()
                .startsWith(ProductSelector.Field.Name, "Apples").get(), 10), 1));

        discounts.add(new Discounter(new BuyGetReducedDiscount(productTypeFromDTO(soup), 2, productTypeFromDTO(bread), 50), 2));

        Mockito.when(discountService.getDiscounts()).thenReturn(discounts);

        Basket basket = service.priceBasket(simpleRequest);

        assertEquals(0, basket.getSubTotal().compareTo(new BigDecimal("3.10")));
        assertEquals(0, basket.getTotal().compareTo(new BigDecimal("3.00")));
    }

    private void prepareProductBatches(int quantity){
        Mockito.when(productService.getProductBatches(bread))
                .thenReturn(Collections.singletonList(generateProductBatch(bread, quantity, new BigDecimal("0.8"))));

        Mockito.when(productService.getProductBatches(milk))
                .thenReturn(Collections.singletonList(generateProductBatch(milk, quantity, new BigDecimal("1.30"))));

        Mockito.when(productService.getProductBatches(soup))
                .thenReturn(Collections.singletonList(generateProductBatch(soup, quantity, new BigDecimal("0.65"))));

        Mockito.when(productService.getProductBatches(apples))
                .thenReturn(Collections.singletonList(generateProductBatch(apples, quantity, new BigDecimal(1))));
    }

    private ProductBatch generateProductBatch(ProductTypeDto type, int quantity, BigDecimal price){
        ProductType productType = new ProductType(type.getName(), "", "");
        productType.setPrice(price);

        ProductBatch batch = new ProductBatch();
        batch.setType(productType);
        batch.setExpiry(LocalDate.MAX);
        batch.setQuantity(quantity);
        return batch;
    }

    private ProductType productTypeFromDTO(ProductTypeDto type){
        return new ProductType(type.getName(), type.getType(), type.getUnit());
    }
}