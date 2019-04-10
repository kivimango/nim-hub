package com.kivimango.nimhub.rest;

final class UsernameAlreadyExists extends Exception {
    private final String username;

    UsernameAlreadyExists(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
