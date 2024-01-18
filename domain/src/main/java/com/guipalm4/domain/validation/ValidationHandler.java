package com.guipalm4.domain.validation;

import java.util.List;
import java.util.function.Function;

public interface ValidationHandler {

    ValidationHandler append(Error error);

    ValidationHandler append(ValidationHandler anHandler);

    <T> T validate(Validation<T> aValidation);
    
    default  <T, R> Function <T, R> validate(final Function<? super T, ? extends R> mapper) {
        return f -> validate(() -> mapper.apply(f));
    }
    
    List<Error> getErrors();

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {
        if (getErrors() != null && !getErrors().isEmpty()) {
            return getErrors().get(0);
        } else {
            return null;
        }
    }

    interface Validation<T> {
        T validate();
    }

}
