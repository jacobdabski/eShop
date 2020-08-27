package com.company.repositories;

import com.company.product.ProductType;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

public interface IProductTypeRepository {

    public void addProductType(ProductType type);
    public Optional<ProductType> findById(UUID id);
    public boolean productExists(ProductType product);

}
