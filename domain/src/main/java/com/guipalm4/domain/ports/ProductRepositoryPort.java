package com.guipalm4.domain.ports;

import com.guipalm4.domain.Product;
import com.guipalm4.domain.ProductID;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    
    List<Product> findAll();
    
    Optional<Product> findBySku(String sku);
    
    void save(Product product);
    
    Optional<Product> findById(ProductID aProductId);
}
