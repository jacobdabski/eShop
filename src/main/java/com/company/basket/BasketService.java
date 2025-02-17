package com.company.basket;

import com.company.ValidationException;
import com.company.discount.DiscountsService;
import com.company.product.ProductBatch;
import com.company.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService {

    @Autowired
    private ProductService productService;

    @Autowired
    private DiscountsService discountService;

    /**
     * Tries to build a basket based on the provided basket requests details,
     * given the amount of items available per each Product Batch in the inventory.
     * If the amount of items requested for any of the Product Types exceed the number
     * items available thereof, the method will throw a ValidationException
     *
     * Given the basket is build successfully, it will proceed to apply the discounts.
     * @param basketDetails the desired basket
     * @return the costed Basket
     * @throws ValidationException
     */
    public synchronized Basket priceBasket(List<BasketRequestDto> basketDetails) throws ValidationException {
        validateBasketRequest(basketDetails);
        Basket basket = new Basket();

        for(BasketRequestDto request: basketDetails){
            List<ProductBatch> batches = productService.getProductBatches(request.getType());

            int quantityToFulfil = request.getQuantity();
            for(ProductBatch batch: batches){
                int quantityToBasket = Math.min(batch.getQuantity(), quantityToFulfil);

                basket.addProduct(new BasketProduct(batch, quantityToBasket));
                quantityToFulfil -= quantityToBasket;
                if(quantityToFulfil == 0){
                    break;
                }
            }
        }
        basket.applyDiscounts(getDiscounter());
        return basket;
    }

    public IBasketDiscounter getDiscounter(){
        return (products, discounts)-> discountService.getDiscounts().forEach((discounter)-> discounter.apply(products, discounts));
    }

    private void validateBasketRequest(List<BasketRequestDto> basketDetails) throws ValidationException{
        basketDetails.stream().forEach(details -> {
            int itemsAvailable = productService.getProductBatches(details.getType()).stream()
                    .map(ProductBatch::getQuantity)
                    .reduce(Integer::sum).orElse(0);

            if(itemsAvailable < details.getQuantity()){
                throw new ValidationException("Cannot cost this basket");
            }
        });
    }
}