package com.kivimango.nimhub.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
class PackageStoreFSImpl implements PackageStore {

    private final Logger log = LoggerFactory.getLogger(PackageStoreFSImpl.class);
    private Path storePath;

    PackageStoreFSImpl(PackageStoreProperties props) {
        this.storePath = Paths.get(props.getStorageDir());
        checkStoragePath();
    }

    @Override
    public void put(Package pack, byte[] bytes) throws IOException {
        String name = pack.getName();
        Path libDir = storePath.resolve(name);
        Path target = libDir.resolve(pack.getName() + "-" + pack.getVersion() + ".tar.gz");
        if(!Files.exists(libDir)) Files.createDirectory(libDir);
        Files.write(target, bytes);
        log.debug("New package file successfully stored under {}", target);
    }

    private void checkStoragePath() {
        if(!Files.exists(storePath)) {
            try {
                Files.createDirectory(storePath);
                log.info("Package storage directory does not exists, creating in {}...", storePath);
            } catch (IOException e) {
                log.error("Failed to create package storage directory", e);
                e.printStackTrace();
            }
        }
    }
}
