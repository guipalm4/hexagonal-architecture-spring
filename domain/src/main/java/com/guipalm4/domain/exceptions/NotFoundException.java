package com.guipalm4.domain.exceptions;


import com.guipalm4.domain.AggregateRoot;
import com.guipalm4.domain.Identifier;
import com.guipalm4.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {

    protected NotFoundException(final String aMessage, final List<Error> anErrors) {
        super(aMessage, anErrors);
    }
    

    public static NotFoundException with(final Error error) {
        return new NotFoundException(error.message(), Collections.emptyList());
    }

    public static NotFoundException with(final Error error, List<Error> anErrors) {
        return new NotFoundException(error.message(), anErrors);
    }
    
    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final Identifier id
    ) {
        final var anError = "%s with ID %s was not found".formatted(
                anAggregate.getSimpleName(),
                id.getValue()
        );
        return new NotFoundException(anError, Collections.emptyList());
    }
    
    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final String fieldName
    ) {
        final var anError = "%s with this %s was not found".formatted(
                anAggregate.getSimpleName(),
                fieldName
        );
        return new NotFoundException(anError, Collections.emptyList());
    }
}