package com.kivimango.nimhub.rest;

final class EmailAddressAlreadyExists extends Exception {
    private final String email;

    EmailAddressAlreadyExists(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
