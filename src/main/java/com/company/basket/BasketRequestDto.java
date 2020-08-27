package com.company.basket;

import com.company.product.ProductType;
import com.company.product.ProductTypeDto;

public class BasketRequestDto {

    private ProductTypeDto type;
    private int quantity;

    public BasketRequestDto(ProductTypeDto type, int quantity){
        this.type = type;
        this.quantity = quantity;
    }

    public ProductTypeDto getType() {
        return type;
    }

    public void setType(ProductTypeDto type) {
        this.type = type;
    }

    public int getQuantity(){
        return quantity;
    }
}
