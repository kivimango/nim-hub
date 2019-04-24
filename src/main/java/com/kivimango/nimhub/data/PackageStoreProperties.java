package com.kivimango.nimhub.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Configuration Properties class mapping application.properties file property values
 * for externalized configuration of the PackageStore service
 *
 * @author kivimango
 * @since 0.1
 * @version 0.1
 */

@Component
@ConfigurationProperties("packages")
final class PackageStoreProperties {

    private final Logger log = LoggerFactory.getLogger(PackageStoreProperties.class);
    private String storageDir = System.getProperty("user.home") + File.separator + "nim-hub" + File.separator;

    String getStorageDir() {
        return storageDir;
    }

    public void setStorageDir(String path) {
        if(path == null || path.isEmpty()) {
            log.info("Package storage path from application.properties is null, fallback to default...");
        } else {
            this.storageDir = storageDir;
            log.info("Package storage path set to {}", path);
        }
    }
}
