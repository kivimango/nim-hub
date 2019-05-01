package com.kivimango.nimhub.data;

public final class ResourceNotFoundException extends Exception {
    private final String id;

    ResourceNotFoundException(String message, String id) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
