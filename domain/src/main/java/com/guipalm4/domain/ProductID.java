package com.guipalm4.domain;


import com.guipalm4.domain.utils.IdUtils;

import java.util.Objects;

public class ProductID extends Identifier {

    private final String value;

    private ProductID(final String anId) {
        this.value = Objects.requireNonNull(anId, "id cannot be null");
    }

    public static ProductID unique() {
        return from(IdUtils.uuid());
    }

    public static ProductID from(final String anId) {

        return new ProductID(anId);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object aO) {
        if (this == aO) return true;
        if (aO == null || getClass() != aO.getClass()) return false;
        final ProductID productId = (ProductID) aO;
        return Objects.equals(value, productId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
