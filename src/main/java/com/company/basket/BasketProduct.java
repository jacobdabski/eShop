package com.company.basket;

import com.company.product.ProductBatch;

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
}
