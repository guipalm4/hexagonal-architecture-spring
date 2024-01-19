package com.guipalm4.domain.ports;

import com.guipalm4.domain.commands.CreateProductCommand;
import com.guipalm4.domain.outputs.CreateProductOutput;
import com.guipalm4.domain.outputs.ProductOutput;
import com.guipalm4.domain.commands.UpdateQuantityCommand;

import java.util.List;

public interface ProductServicePort {

    List<ProductOutput> findAll();
    ProductOutput findBySku(String sku);
    CreateProductOutput create(CreateProductCommand aCreateProductCommand);
    void updateQuantity(UpdateQuantityCommand aUpdateQuantityCommand);
    
}
