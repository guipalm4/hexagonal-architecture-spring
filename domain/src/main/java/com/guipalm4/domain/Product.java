package com.guipalm4.domain;

import com.guipalm4.domain.exceptions.NotificationException;
import com.guipalm4.domain.utils.IdUtils;
import com.guipalm4.domain.utils.InstantUtils;
import com.guipalm4.domain.validation.ValidationHandler;
import com.guipalm4.domain.validation.handler.Notification;

import java.time.Instant;

public class Product extends AggregateRoot<ProductID> {
    
    private String sku;
    private String name;
    private Double price;
    private Double quantity;
    private Instant createdAt;
    private Instant updatedAt;
    
    private Product(
            final ProductID anId,
            final String aSku,
            final String aName,
            final Double aPrice,
            final Double aQuantity,
            final Instant aCreatedAt,
            final Instant anUpdatedAt
    ) {
        
        super(anId);
        sku = aSku;
        name = aName;
        price = aPrice;
        quantity = aQuantity;
        createdAt = aCreatedAt;
        updatedAt = anUpdatedAt;
        
        selfValidate();
    }
    
    public static Product newProduct(
            final String aSku,
            final String aName,
            final Double aPrice,
            final Double aQuantity
    ) {
        
        final var id = ProductID.unique();
        final var now = InstantUtils.now();
        
        return new Product(
                id,
                aSku,
                aName,
                aPrice,
                aQuantity,
                now,
                now
        );
    }
    
    public static Product with(
            final ProductID anId,
            final String aSku,
            final String aName,
            final Double aPrice,
            final Double aQuantity,
            final Instant aCreatedAt,
            final Instant anUpdatedAt
    ) {
        
        return new Product(
                anId,
                aSku,
                aName,
                aPrice,
                aQuantity,
                aCreatedAt,
                anUpdatedAt
        );
    }
    
    @Override
    public void validate(final ValidationHandler validationHandler) {
        
        new ProductValidator(this, validationHandler).validate();
    }
    
    public String getSku() {
        
        return sku;
    }
    
    public String getName() {
        
        return name;
    }
    
    public Double getPrice() {
        
        return price;
    }
    
    public Double getQuantity() {
        
        return quantity;
    }
    
    public Instant getCreatedAt() {
        
        return createdAt;
    }
    
    public Instant getUpdatedAt() {
        
        return updatedAt;
    }
    
    public Product updateQuantity(double aQuantity) {
        
        this.quantity = aQuantity;
        this.updatedAt = InstantUtils.now();
        
        return this;
    }
    
    public Product changeName(String aName) {
        
        this.name = aName;
        this.updatedAt = InstantUtils.now();
        
        return this;
    }
    
    public void selfValidate() {
        
        final var notification = Notification.create();
        validate(notification);
        
        if (notification.hasError()) {
            throw new NotificationException(
                    "Failed to create a Aggregate Product",
                    notification
            );
        }
    }
    
}
