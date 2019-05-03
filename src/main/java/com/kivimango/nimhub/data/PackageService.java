package com.kivimango.nimhub.data;

import java.io.IOException;

public interface PackageService {
    PackageDto save(PackageUploadRequest form, byte[] bytes, String username) throws IOException;
    String get(String name, String version) throws ResourceNotFoundException;
    Boolean isExists(String name, String version);
}
