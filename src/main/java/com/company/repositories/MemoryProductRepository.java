package com.company.repositories;

import com.company.product.ProductBatch;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MemoryProductRepository implements IProductRepository{

    List<ProductBatch> products = new ArrayList<>();

    @Override
    public List<ProductBatch> retrieveByName(String name) {
        return products.stream().filter((product)-> product.getProductTypeName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public void updateProductQuantity(UUID id, int quantity) {
        //todo
    }

    @Override
    public void updateProductPrice(UUID id, BigDecimal price){
        //todo
    }

    @Override
    public void addProduct(ProductBatch product) {
        product.setId(UUID.randomUUID());
        products.add(product);
    }
}
