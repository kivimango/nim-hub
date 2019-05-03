package com.kivimango.nimhub.rest;

final class PackageAlreadyExistsException extends Exception {
    PackageAlreadyExistsException(String name, String version) {
        super("The package (" + name + ") already exists with version " + version);
    }
}
