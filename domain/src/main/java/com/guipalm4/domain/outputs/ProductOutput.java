package com.guipalm4.domain.outputs;

import com.guipalm4.domain.Product;

public record ProductOutput(
        
        String id,
        String sku,
        String name,
        Double price,
        Double quantity
) {
    
    public static ProductOutput with(
            final String anId,
            final String aSku,
            final String aName,
            final Double aPrice,
            final Double aQuantity
    ) {
       
        return new ProductOutput(
                anId,
                aSku,
                aName,
                aPrice,
                aQuantity
        );
    }
    
    public static ProductOutput from(final Product aProduct) {
        
        return with(
                aProduct.getId().getValue(),
                aProduct.getSku(),
                aProduct.getName(),
                aProduct.getPrice(),
                aProduct.getQuantity()
        );
    }
    
}
