package com.company.product;

import com.company.ValidationException;
import com.company.repositories.IProductRepository;
import com.company.repositories.IProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private IProductRepository productRepo;

    @Autowired
    private IProductTypeRepository productTypeRepository;

    /**
     * Retrieves one product line from the available products with the given name
     * @param productTypeDto
     * @return
     */
    public List<ProductBatch> getProductBatches(ProductTypeDto productTypeDto) throws ValidationException{
        //TODO handle more complicated queries
        return productRepo.retrieveByName(productTypeDto.getName());
    }

    public void addProduct(UUID productTypeId, int quantity, LocalDate expiryDate) throws ValidationException {
        ProductType type  = productTypeRepository.findById(productTypeId).orElseThrow(()-> new ValidationException("ProductType does not exist"));
        ProductBatch product = new ProductBatch();
        product.setType(type);
        product.setExpiry(expiryDate);
        product.setQuantity(quantity);
        productRepo.addProduct(product);
    }

    public void addProductType(ProductTypeDto productType, BigDecimal price) throws ValidationException{
        ProductType type = new ProductType();
        type.setName(productType.getName());
        type.setBrand(productType.getBrand());
        type.setPrice(price);
        type.setType(productType.getType());
        type.setUnit(productType.getUnit());
        if(productTypeRepository.productExists(type)){
            throw new ValidationException("Product Type already exists");
        }
        productTypeRepository.addProductType(type);
    }

}