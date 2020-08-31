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
    public List<ProductBatch> getProductBatches(ProductTypeDto productTypeDto) {
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

    public UUID addProductType(ProductType productType, BigDecimal price) throws ValidationException{
        productType.setPrice(price);
        if(productTypeRepository.productExists(productType)){
            throw new ValidationException("Product Type already exists");
        }
        return productTypeRepository.addProductType(productType);
    }

}