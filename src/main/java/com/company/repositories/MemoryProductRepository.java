package com.company.repositories;

import com.company.product.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class MemoryProductRepository implements IProductRepository{

    List<Product> products = new ArrayList<>();

    @Override
    public List<Product> retrieveByName(String name) {
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
    public void addProduct(Product product) {
        product.setId(UUID.randomUUID());
        products.add(product);
    }
}
