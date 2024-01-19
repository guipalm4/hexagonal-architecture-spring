package com.guipalm4.domain.outputs;

import com.guipalm4.domain.Product;

public record CreateProductOutput(
        
        String id,
        String sku
) {
    
    public static CreateProductOutput with(
            final String anId,
            final String aSku
    ) {
       
        return new CreateProductOutput(
                anId,
                aSku
        );
    }
    
    public static CreateProductOutput from(final Product aProduct) {
        
        return with(
                aProduct.getId().getValue(),
                aProduct.getSku()
        );
    }
    
}
