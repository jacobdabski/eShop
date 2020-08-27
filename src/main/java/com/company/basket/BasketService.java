package com.company.basket;

import com.company.ValidationException;
import com.company.discount.DiscountsService;
import com.company.product.ProductBatch;
import com.company.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import java.util.ArrayList;
import java.util.List;

@Service
public class BasketService {

    @Autowired
    private ProductService productService;

    @Autowired
    private DiscountsService discountService;


    public synchronized Basket createBasket(List<BasketRequestDto> basketDetails) throws ValidationException {
        Basket basket = new Basket();

        List<BasketProduct> products = new ArrayList<>();

        for(BasketRequestDto request: basketDetails){
            List<ProductBatch> batches = productService.getProductBatches(request.getType());
            int itemsAvailable = batches.stream()
                    .reduce(0, (partialResult, req) -> partialResult + req.getQuantity(), Integer::sum);
            if(itemsAvailable - request.getQuantity() < 0){
                throw new ValidationException("Not enough items available for Product : " + request.getType());
            }
            Integer quantity = request.getQuantity();
            for(ProductBatch batch: batches){
                if(quantity == 0){
                    break;
                }
                quantity -= batch.getQuantity();
                BasketProduct product = new BasketProduct(batch, )
            }

        }
    }
}