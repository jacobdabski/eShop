package com.company.repositories;

import com.company.product.ProductBatch;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
public interface IProductRepository {

    List<ProductBatch> retrieveByName(String name);
    void updateProductQuantity(UUID id, int quantity);
    void updateProductPrice(UUID id, BigDecimal price);
    void addProduct(ProductBatch product);

}
