package com.guipalm4.domain.exceptions;


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
}