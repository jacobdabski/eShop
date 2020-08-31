package com.company.basket;

import com.company.product.ProductBatch;
import com.company.product.ProductType;

import java.math.BigDecimal;

public class BasketProduct {

    private ProductBatch product;
    private int quantity;

    public BasketProduct(ProductBatch product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }

    public ProductBatch getProduct() {
        return product;
    }

    public void setProduct(ProductBatch product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getBatchPrice(){
        return product.getPrice().multiply(new BigDecimal(quantity));
    }

    public BigDecimal getProductPrice(){
        return product.getPrice();
    }

    public ProductType getProductType(){
        return product.getType();
    }
}
