package com.kivimango.nimhub.rest;

import java.util.Objects;

final class ValidationError {
    final private String field;
    final private String errorMessage;

    ValidationError(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
    }

    public String getField() {
        return field;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationError that = (ValidationError) o;
        return field.equals(that.field) &&
                errorMessage.equals(that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, errorMessage);
    }
}
