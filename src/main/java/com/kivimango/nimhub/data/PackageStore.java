package com.kivimango.nimhub.data;

import java.io.IOException;

public interface PackageStore {
    void put(Package pack, byte[] bytes) throws IOException;
    String get(Package pack) throws ResourceNotFoundException;
}
