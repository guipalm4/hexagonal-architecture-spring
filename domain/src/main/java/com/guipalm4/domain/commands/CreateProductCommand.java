package com.guipalm4.domain.commands;

public record CreateProductCommand(
        
        String sku,
        String name,
        Double price,
        Double quantity
) {
    
    public static CreateProductCommand with(
            String aSku,
            String aName,
            Double aPrice,
            Double aQuantity
    ) {
        return new CreateProductCommand(
                aSku,
                aName,
                aPrice,
                aQuantity
        );
    }
}
