package com.guipalm4.domain;


import com.guipalm4.domain.validation.Error;
import com.guipalm4.domain.validation.ValidationHandler;
import com.guipalm4.domain.validation.Validator;

import static com.guipalm4.domain.utils.StringUtils.isEmpty;

public class ProductValidator extends Validator {

    private static final int NAME_MAX_LENGTH = 50;

    private final Product aggregate;

    protected ProductValidator(
            final Product aAggregate,
            final ValidationHandler aValidationHandler
    ) {

        super(aValidationHandler);
        this.aggregate = aAggregate;
    }

    @Override
    public void validate() {
        checkConstraints();
    }

    private void checkConstraints() {

        final var handler = this.validationHandler();
        
        final var sku = this.aggregate.getSku();
        if (sku == null || sku.isBlank()) {
            handler.append(new Error("'sku' should not be null or empty"));
            return;
        }

        final var name = this.aggregate.getName();
        if (name == null || name.isBlank()) {
            handler.append(new Error("'name' should not be null or empty"));
            return;
        }

        final var aName = this.aggregate.getName();
        if (aName.length() > NAME_MAX_LENGTH) {
            handler.append(Error.with("'name' cannot be greater than %s characters".formatted(NAME_MAX_LENGTH)));
        }

        final var anId = this.aggregate.getId();
        if (anId == null || isEmpty(anId.getValue())) {
            handler.append(Error.with("'id' cannot be null or empty."));
        }

        final var createdAt = this.aggregate.getCreatedAt();
        if (createdAt == null) {
            handler.append(Error.with("'createdAt' cannot be null."));
        }

        final var updateAt = this.aggregate.getUpdatedAt();
        if (updateAt == null) {
            handler.append(Error.with("'updateAt' cannot be null."));
        }

    }
}
