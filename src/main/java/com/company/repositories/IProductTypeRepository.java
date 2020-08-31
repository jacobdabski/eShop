package com.company.repositories;

import com.company.product.ProductType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IProductTypeRepository {

    UUID addProductType(ProductType type);
    Optional<ProductType> findById(UUID id);
    boolean productExists(ProductType product);

}
