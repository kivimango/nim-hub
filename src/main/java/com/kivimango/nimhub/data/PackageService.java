package com.kivimango.nimhub.data;

import java.io.IOException;

public interface PackageService {
    PackageDto save(PackageUploadRequest form, byte[] bytes, String username) throws IOException;
}
