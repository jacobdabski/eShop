package com.company;

import com.company.basket.AppliedDiscounts;
import com.company.basket.Basket;
import com.company.basket.BasketRequestDto;
import com.company.basket.BasketService;
import com.company.discount.Discounter;
import com.company.discount.DiscountsService;
import com.company.discount.functions.BuyGetReducedDiscount;
import com.company.discount.functions.Discount;
import com.company.discount.functions.ProductTypeDiscount;
import com.company.discount.functions.conditions.ExpiringCondition;
import com.company.product.*;
import com.company.utils.PriceFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@ComponentScan(basePackages = "com.company")
@SpringBootApplication
public class Application {

    /**
     * As per assignment requirements, takes Input in the Form of 'PriceBasket item1 item2 item2'
     * and prints the basket to the console.
     * If PriceBasket is not the first argument, the input is ignored.
     * If the input is entirely not parsable it throws an exception.
     * @param args
     */
    public static void main(String[] args) {
        if(args.length < 2){
            throw new ValidationException("Bad Request");
        }
        ApplicationContext context = SpringApplication.run(Application.class, args);

        BasketService basketService = context.getBean(BasketService.class);
        DiscountsService discountsService = context.getBean(DiscountsService.class);
        ProductService productService = context.getBean(ProductService.class);

        bootstrapData(productService, discountsService);

        List<String> arguments = new ArrayList<>(Arrays.asList(args));
        if(arguments.remove(0).equals("PriceBasket")){
            final List<BasketRequestDto> requests = parseBasketRequestsFromNames(arguments);
            Basket basket = basketService.priceBasket(requests);
            printBasket(basket);
        }

    }

    private static void printBasket(Basket basket){
        StringBuilder builder = new StringBuilder("Subtotal: ");
        builder.append(PriceFormatter.format(basket.getSubTotal())).append('\n');

        Map<ProductBatch, AppliedDiscounts> discounts = basket.getDiscounts();
        if(discounts.isEmpty()){
            builder.append("No offers available \n");
        } else{
            discounts.entrySet().forEach((entry)->{
                AppliedDiscounts appliedDiscounts = entry.getValue();
                builder.append(entry.getKey().getProductTypeName());
                builder.append(" ").append(appliedDiscounts.getPercentageOff());
                builder.append(" off: ");
                if(appliedDiscounts.getDiscounts() > 0){
                    builder.append(" (x ").append(appliedDiscounts.getDiscounts()).append(")");
                }
                builder.append("-").append(PriceFormatter.format(appliedDiscounts.getPriceModifier()));
            });
        }

        builder.append(PriceFormatter.format(basket.getTotal()));
        System.out.println(builder);
    }

    private static List<BasketRequestDto> parseBasketRequestsFromNames(List<String> productNames){
        Map<String, Integer> itemQuantities = new HashMap<>();
        for(String item: productNames){
            Integer quantity = itemQuantities.getOrDefault(item, 0);
            itemQuantities.put(item, ++quantity);
        }

        return itemQuantities.entrySet().stream().map(entry -> {
            ProductTypeDto productType = ProductTypeDto.builder().name(entry.getKey()).get();
            return new BasketRequestDto(productType, entry.getValue());
        }).collect(Collectors.toList());
    }

    /**
     * Bootstrapping the data necessary to complete the assignment requirements
     * @param products
     * @param discounts
     */
    private static void bootstrapData(ProductService products, DiscountsService discounts){
        ProductType bread = new ProductType("Bread", "white", "loaf");
        products.addProduct(products.addProductType(bread, new BigDecimal("0.8")),10, LocalDate.MAX);

        ProductType soup = new ProductType("Soup", "vegetable", "tin");
        products.addProduct(products.addProductType(soup, new BigDecimal("0.65")), 10, LocalDate.MAX);

        ProductType milk = new ProductType("Milk", "semi-skimmed", "2 pint bottle");
        products.addProduct(products.addProductType(milk, new BigDecimal("1.30")), 10, LocalDate.MAX);

        ProductType apples = new ProductType("Apples", "bramley", "bag");
        products.addProduct(products.addProductType(apples, new BigDecimal(1)), 10, LocalDate.MAX);

        discounts.addDiscount(new Discounter(new BuyGetReducedDiscount(soup, 2, bread, 50), 1));

        ProductSelector selector = ProductSelector.builder().equals(ProductSelector.Criterion.Name, "Apples").get();
        Discount applesDiscount = new ProductTypeDiscount(selector, 10);
        applesDiscount.setBasketCondition(new ExpiringCondition(LocalDate.of(2020, 8, 6)));

        discounts.addDiscount(new Discounter(applesDiscount, 2));
    }

}
