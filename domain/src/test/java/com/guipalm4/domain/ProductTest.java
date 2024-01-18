package com.guipalm4.domain;

import com.guipalm4.domain.exceptions.DomainException;
import com.guipalm4.domain.utils.InstantUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest extends UnitTest {
    
    @Test
    @DisplayName("Given valid params should create a new enabled Product")
    void testCreateProductEnabled() {
        
        final var expectedSku = "1828PR";
        final var expectedName = "A new Product";
        final var expectedPrice = 10.00;
        final var expectedQuantity = 5.0;
        final var now = InstantUtils.now();
        
        final var actual = Product.newProduct(
                expectedSku,
                expectedName,
                expectedPrice,
                expectedQuantity
        );
        
        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertTrue(actual.getId() instanceof ProductID);
        assertEquals(expectedSku, actual.getSku());
        assertEquals(expectedName, actual.getName());
        assertEquals(expectedPrice, actual.getPrice());
        assertEquals(expectedQuantity, actual.getQuantity());
        assertTrue(actual.getCreatedAt().isAfter(now));
        assertTrue(actual.getUpdatedAt().isAfter(now));
    }
    
    @Test
    @DisplayName("Given a null name should be not create a new Product and throws DomainException")
    void testCreateProductWithNullName() {
        
        final var expectedSku = "1828PR";
        final var expectedPrice = 10.00;
        final var expectedQuantity = 5.0;
        
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null or empty";
        
        final var actualException =
                Assertions.assertThrows(
                        DomainException.class,
                        () -> Product.newProduct(
                                expectedSku,
                                null,
                                expectedPrice,
                                expectedQuantity
                        )
                );
        
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }
    
    @Test
    @DisplayName("Given a name with more than 50 chars should be not create a new Product and throws DomainException")
    void testCreateProductWithNameLengthMoreThan50() {
        
        final var expectedSku = "1828PR";
        final var expectedPrice = 10.00;
        final var expectedQuantity = 5.0;
        
        final var expectedInvalidName = "Invalid name Invalid name Invalid name Invalid name Invalid name";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' cannot be greater than 50 characters";
        
        final var actualException =
                Assertions.assertThrows(DomainException.class,
                                        () -> Product.newProduct(
                                                expectedSku,
                                                expectedInvalidName,
                                                expectedPrice,
                                                expectedQuantity
                                        )
                );
        
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }
    
    @Test
    @DisplayName("Given a valid name should be return a updated product")
    void testChangeName() {
        final var expectedProductId = ProductID.unique();
        final var expectedName = "A Product";
        final var expectedSku = "1828PR";
        final var expectedPrice = 10.00;
        final var expectedQuantity = 5.0;
        final var now = InstantUtils.now();
        
        final var aProduct = Product.with(
                expectedProductId,
                expectedSku,
                expectedName,
                expectedPrice,
                expectedQuantity,
                now,
                now
        );
        
        final var expectedNameUpdated = "A updated Product";
        
        final var actual = aProduct.changeName(expectedNameUpdated);
        
        assertNotNull(actual);
        assertEquals(expectedProductId, actual.getId());
        assertEquals(expectedNameUpdated, actual.getName());
        assertEquals(now, actual.getCreatedAt());
        assertTrue(actual.getUpdatedAt().isAfter(now));
    }
    
}
