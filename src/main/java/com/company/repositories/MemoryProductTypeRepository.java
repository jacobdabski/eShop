package com.company.repositories;

import com.company.product.ProductType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MemoryProductTypeRepository implements IProductTypeRepository{

    List<ProductType> productTypes = new ArrayList<>();

    @Override
    public void addProductType(ProductType type) {
        if(!productTypes.contains(type)) {
            type.setId(UUID.randomUUID());
            productTypes.add(type);
        }
    }

    @Override
    public Optional<ProductType> findById(UUID id) {
        return productTypes.stream().filter((productType)-> productType.getId().equals(id)).findAny();
    }

    @Override
    public boolean productExists(ProductType product) {
        return productTypes.contains(product);
    }

}
