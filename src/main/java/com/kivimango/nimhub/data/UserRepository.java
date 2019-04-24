package com.kivimango.nimhub.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String name);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
