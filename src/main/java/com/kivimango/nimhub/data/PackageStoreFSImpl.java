package com.kivimango.nimhub.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
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

    @Override
    public String get(Package pack) throws ResourceNotFoundException {
        Path path = Paths.get(storePath.resolve(pack.getName()) + File.separator + pack.getName() + "-" + pack.getVersion() + ".tar.gz");
        if(Files.exists(path)) {
            return path.toString();
        } else throw new ResourceNotFoundException("The requested resource could not be found", pack.getName() + pack.getVersion());
    }

    @Override
    public Boolean exists(String name, String version) {
        String fName = name + "-" + version + ".tar.gz";
        Path p = Paths.get(storePath.toString(), name, fName);
        return Files.exists(p);
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
