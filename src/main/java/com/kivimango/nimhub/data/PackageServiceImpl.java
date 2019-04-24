package com.kivimango.nimhub.data;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
class PackageServiceImpl implements PackageService {

    private final PackageRepository repository;
    private final PackageStore storage;
    private final UserRepository users;

    PackageServiceImpl(PackageRepository repository, PackageStore storage, UserRepository userRepository) {
        this.repository = repository;
        this.storage = storage;
        this.users = userRepository;
    }

    @Override
    @Transactional
    public PackageDto save(PackageUploadRequest form, byte[] bytes, String username) throws IOException {
        Objects.requireNonNull(form, "You must supply an upload form");
        Objects.requireNonNull(bytes, "You must supply a file");
        Objects.requireNonNull(username, "You must supply a username");

        Package saved = repository.save(getEntityFromForm(form, username));
        storage.put(saved, bytes);
        return PackageDto.of(saved);
    }

    private Package getEntityFromForm(PackageUploadRequest form, String username) {
        Package newPack = new Package();
        newPack.setName(form.getName());
        newPack.setDescription(form.getDescription());
        newPack.setLicense(form.getLicense());
        newPack.setWeb(form.getWeb());
        newPack.setVersion(form.getVersion());

        Optional<User> u = users.findByUsername(username);
        if(u.isPresent()) {
            User owner = u.get();
            Set<Package> packs = owner.getPackages();
            packs.add(newPack);
            owner.setPackages(packs);
            newPack.setOwner(owner);
        }

        return newPack;
    }
}
