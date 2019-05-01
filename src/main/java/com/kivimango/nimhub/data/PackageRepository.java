package com.kivimango.nimhub.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageRepository extends CrudRepository<Package, Long> {
    Optional<Package> findByNameAndVersion(String name, String version);
}
