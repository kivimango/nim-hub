package com.kivimango.nimhub.data;

public final class ResourceNotFoundException extends Exception {
    private final Long id;

    ResourceNotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
