package com.company.repositories;

import com.company.product.ProductType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MemoryProductTypeRepository implements IProductTypeRepository{

    List<ProductType> productTypes = new ArrayList<>();

    @Override
    public UUID addProductType(ProductType type) {
        if(!productTypes.contains(type)) {
            type.setId(UUID.randomUUID());
            productTypes.add(type);
        }
        return type.getId();
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
