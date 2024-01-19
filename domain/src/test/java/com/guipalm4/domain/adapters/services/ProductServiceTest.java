package com.guipalm4.domain.adapters.services;

import com.guipalm4.domain.Product;
import com.guipalm4.domain.UnitTest;
import com.guipalm4.domain.adpaters.services.ProductService;
import com.guipalm4.domain.commands.CreateProductCommand;
import com.guipalm4.domain.commands.UpdateQuantityCommand;
import com.guipalm4.domain.exceptions.NotFoundException;
import com.guipalm4.domain.exceptions.NotificationException;
import com.guipalm4.domain.ports.ProductRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceTest extends UnitTest {
    
    @InjectMocks
    private ProductService productService;
    
    @Mock
    private ProductRepositoryPort productRepositoryPort;
    
    @Test
    @DisplayName("Should be able to list all products")
    void findAllProductsTest() {
        
        final var expectedSku = "1828PR";
        final var expectedName = "A new Product";
        final var expectedPrice = 10.00;
        final var expectedQuantity = 5.0;
        
        final var expectedProduct = Product.newProduct(
                expectedSku,
                expectedName,
                expectedPrice,
                expectedQuantity
        );
        
        when(productRepositoryPort.findAll())
                .thenReturn(List.of(expectedProduct));
        
        final var actual = productService.findAll();
        
        final var findProduct = actual.get(0);
        
        verify(productRepositoryPort, times(1)).findAll();
        assertEquals(expectedProduct.getId().getValue(), findProduct.id());
        assertEquals(expectedSku, findProduct.sku());
        assertEquals(expectedName, findProduct.name());
        assertEquals(expectedPrice, findProduct.price());
        assertEquals(expectedQuantity, findProduct.quantity());
        
    }
    
    @Test
    @DisplayName("Given a valid sku should be able to find a product")
    void testFindBySku() {
        
        final var expectedSku = "1828PR";
        final var expectedName = "A new Product";
        final var expectedPrice = 10.00;
        final var expectedQuantity = 5.0;
        
        final var expectedProduct = Product.newProduct(
                expectedSku,
                expectedName,
                expectedPrice,
                expectedQuantity
        );
        
        when(productRepositoryPort.findBySku(expectedSku))
                .thenReturn(Optional.of(expectedProduct));
        
        final var actual = productService.findBySku(expectedSku);
        
        verify(productRepositoryPort).findBySku(eq(expectedSku));
        assertEquals(expectedProduct.getId().getValue(), actual.id());
        assertEquals(expectedSku, actual.sku());
        assertEquals(expectedName, actual.name());
        assertEquals(expectedPrice, actual.price());
        assertEquals(expectedQuantity, actual.quantity());
    }
    
    @Test
    @DisplayName("Given a invalid sku should throws notFoundException")
    void findBySkuNotFoundTest() {
        
        final var expectedErrorMessage = "Product with this 'sku' was not found";
        final var expectedSku = "invalid";
        
        when(productRepositoryPort.findBySku(any()))
                .thenReturn(Optional.empty());
        
        final var actual = assertThrows(
                NotFoundException.class,
                () -> productService.findBySku(expectedSku)
        );
        
        assertEquals(expectedErrorMessage, actual.getMessage());
    }
    
    @Test
    @DisplayName("Given valid parameters should be able to create a product")
    void createProductTest() {
        
        final var expectedSku = "1828PR";
        final var expectedName = "A new Product";
        final var expectedPrice = 10.00;
        final var expectedQuantity = 5.0;
        
        final var aCommand = CreateProductCommand.with(
                expectedSku,
                expectedName,
                expectedPrice,
                expectedQuantity
        );
        
        doNothing().when(productRepositoryPort).save(any());
        
        final var actual = assertDoesNotThrow(() -> productService.create(aCommand));
        
        assertNotNull(actual);
        assertEquals(expectedSku, actual.sku());
        verify(productRepositoryPort).save(argThat(aProduct ->
                                                           Objects.equals(expectedSku, aProduct.getSku()) &&
                                                                   Objects.equals(expectedName, aProduct.getName()) &&
                                                                   Objects.equals(expectedPrice, aProduct.getPrice()) &&
                                                                   Objects.equals(
                                                                           expectedQuantity,
                                                                           aProduct.getQuantity()
                                                                   )
        ));
    }
    
    @Test
    @DisplayName("Given invalid parameters should not be able to create a product")
    void createProductInvalidParamsTest() {
        
        final var expectedErrorMessage = "Could not create Product";
        final var expectedSku = "1828PR";
        final var expectedPrice = 10.00;
        final var expectedQuantity = 5.0;
        
        final var aCommand = CreateProductCommand.with(
                expectedSku,
                null,
                expectedPrice,
                expectedQuantity
        );
        
        final var actual = assertThrows(
                NotificationException.class,
                () -> productService.create(aCommand)
        );
        
        assertEquals(expectedErrorMessage, actual.getMessage());
        verify(productRepositoryPort, never()).save(any());
    }
        
        
        @Test
    @DisplayName("Given valid id should be able to update quantity at product")
    void updateQuantityProductTest() {
        
        final var expectedSku = "1828PR";
        final var expectedName = "A new Product";
        final var expectedPrice = 10.00;
        final var expectedQuantity = 5.0;
        
        final var expectedProduct = Product.newProduct(
                expectedSku,
                expectedName,
                expectedPrice,
                expectedQuantity
        );
        
        final var expectedId = expectedProduct.getId().getValue();
        
        final var expectedNewQuantity = 10.00;
        
        final var aCommand = UpdateQuantityCommand.with(
                expectedId,
                expectedNewQuantity
        );
        
        when(productRepositoryPort.findById(any()))
                .thenReturn(Optional.of(expectedProduct));
        
        assertDoesNotThrow(() -> productService.updateQuantity(aCommand));
        
        verify(productRepositoryPort).save(argThat(aProduct ->
                                                           Objects.equals(expectedId, aProduct.getId().getValue()) &&
                                                                   Objects.equals(expectedSku, aProduct.getSku()) &&
                                                                   Objects.equals(expectedName, aProduct.getName()) &&
                                                                   Objects.equals(expectedPrice, aProduct.getPrice()) &&
                                                                   Objects.equals(
                                                                           expectedNewQuantity,
                                                                           aProduct.getQuantity()
                                                                   )
        ));
    }
    
    @Test
    @DisplayName("Given a invalid id should throws notFoundException")
    void updateQuantityWithNotFoundProductTest() {
        
        final var expectedErrorMessage = "Product with ID invalid was not found";
        final var expectedId = "invalid";
        final var expectedNewQuantity = 10.00;
        
        final var aCommand = UpdateQuantityCommand.with(
                expectedId,
                expectedNewQuantity
        );
        
        when(productRepositoryPort.findById(any()))
                .thenReturn(Optional.empty());
        
        final var actual = assertThrows(
                NotFoundException.class,
                () -> productService.updateQuantity(aCommand)
        );
        
        assertEquals(expectedErrorMessage, actual.getMessage());
        verify(productRepositoryPort, never()).save(any());
    }
    
}
