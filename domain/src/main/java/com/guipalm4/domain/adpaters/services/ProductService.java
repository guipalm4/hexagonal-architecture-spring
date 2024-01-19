package com.guipalm4.domain.adpaters.services;

import com.guipalm4.domain.Product;
import com.guipalm4.domain.ProductID;
import com.guipalm4.domain.commands.CreateProductCommand;
import com.guipalm4.domain.outputs.CreateProductOutput;
import com.guipalm4.domain.outputs.ProductOutput;
import com.guipalm4.domain.commands.UpdateQuantityCommand;
import com.guipalm4.domain.exceptions.NotFoundException;
import com.guipalm4.domain.exceptions.NotificationException;
import com.guipalm4.domain.ports.ProductRepositoryPort;
import com.guipalm4.domain.ports.ProductServicePort;
import com.guipalm4.domain.validation.handler.Notification;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static com.guipalm4.domain.utils.CollectionUtils.mapTo;

public class ProductService implements ProductServicePort {
    
    private final ProductRepositoryPort productRepository;
    
    public ProductService(final ProductRepositoryPort aProductRepository) {
        
        productRepository = Objects.requireNonNull(aProductRepository);
    }
    
    @Override
    public List<ProductOutput> findAll() {
        
        final var products = mapTo(
                productRepository.findAll(),
                ProductOutput::from
        );
        
        return products;
    }
    
    public ProductOutput findBySku(final String sku) {
        
        return productRepository.findBySku(sku)
                .map(ProductOutput::from)
                .orElseThrow(notFound("'sku'"));
    }
    
    
    @Override
    public CreateProductOutput create(final CreateProductCommand aCreateProductCommand) {
        
        final var aSku = aCreateProductCommand.sku();
        final var aName = aCreateProductCommand.name();
        final var aPrice = aCreateProductCommand.price();
        final var aQuantity = aCreateProductCommand.quantity();
        
        final var notification = Notification.create();
        
        final var newProduct = notification.validate(() -> Product.newProduct(aSku, aName, aPrice, aQuantity));
        
        if (notification.hasError()) {
            throw new NotificationException("Could not create Product", notification);
        }
        
        productRepository.save(newProduct);
        
        return CreateProductOutput.from(newProduct);
    }
    
    @Override
    public void updateQuantity(
            final UpdateQuantityCommand aUpdateQuantityCommand
    ) {
        
        final var productId = ProductID.from(aUpdateQuantityCommand.id());
        
        final var product = productRepository.findById(productId)
                .orElseThrow(notFound(productId));
        
        final var quantity = aUpdateQuantityCommand.quantity();
        
        final var updatedProduct = product.updateQuantity(quantity);
        
        productRepository.save(updatedProduct);
    
    }
    
    private Supplier<NotFoundException> notFound(final ProductID aProductId) {
        return () -> NotFoundException.with(Product.class, aProductId);
    }
    
    private Supplier<NotFoundException> notFound(final String fieldName) {
        return () -> NotFoundException.with(Product.class, fieldName);
    }
    
}
