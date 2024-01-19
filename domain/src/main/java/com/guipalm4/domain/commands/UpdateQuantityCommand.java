package com.guipalm4.domain.commands;

public record UpdateQuantityCommand(
        String id,
        Double quantity
) {
    
    public static UpdateQuantityCommand with(
            String anId,
            Double aQuantity
    ) {
        
        return new UpdateQuantityCommand(anId, aQuantity);
    }
}
