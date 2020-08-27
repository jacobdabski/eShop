package com.company.repositories;

import com.company.product.ProductBatch;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface IProductRepository {

    public List<ProductBatch> retrieveByName(String name);
    public void updateProductQuantity(UUID id, int quantity);
    public void updateProductPrice(UUID id, BigDecimal price);
    public void addProduct(ProductBatch product);

}
