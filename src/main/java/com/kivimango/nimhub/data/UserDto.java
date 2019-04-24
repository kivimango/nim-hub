package com.kivimango.nimhub.data;

import java.util.Set;

public final class UserDto {
    private final Long id;
    private final String username, email;
    private final Set<PackageDto> packages;

    private UserDto(Long id, String username, String email, Set<PackageDto> packages) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.packages = packages;
    }

    public static UserDto of(Long id, String username, String email, Set<Package> packages) {
        return new UserDto(id, username, email, PackageDto.of(packages));
    }

    static UserDto of(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), PackageDto.of(user.getPackages()));
    }

}
