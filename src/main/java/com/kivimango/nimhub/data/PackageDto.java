package com.kivimango.nimhub.data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class PackageDto {
    private final Long id;
    private final AuthorDto owner;
    private final String name, description, license, web, version;
    private final Set<TagDto> tags;
    private final Boolean hidden;

    private PackageDto(Long id, AuthorDto owner, String name, String description, String license, String web, String version,
                       Set<TagDto> tags, Boolean hidden) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.license = license;
        this.web = web;
        this.version = version;
        this.tags = tags;
        this.hidden = hidden;
    }

    public static PackageDto of(Long id, AuthorDto owner, String name, String description, String license, String web,
                                String version, Set<TagDto> tags, Boolean hidden) {
        return new PackageDto(id, owner, name, description, license, web, version, tags, hidden);
    }

    static Set<PackageDto> of (Set<Package> packages) {
        Set<PackageDto> packageDtos = new HashSet<>();
        for(Package p : packages) {
            packageDtos.add(PackageDto.of(p));
        }
        return packageDtos;
    }

    static PackageDto of (Package pack) {
        Set<TagDto> tags = new HashSet<>();
        for(Tag t : pack.getTags()) {
            tags.add(TagDto.of(t));
        }
        return new PackageDto(pack.getId(), AuthorDto.of(pack.getOwner()), pack.getName(), pack.getDescription(),
                pack.getLicense(), pack.getWeb(), pack.getVersion(), tags, pack.getHidden());
    }

    public Long getId() {
        return id;
    }

    public AuthorDto getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLicense() {
        return license;
    }

    public String getWeb() {
        return web;
    }

    public String getVersion() {
        return version;
    }

    public Set<TagDto> getTags() {
        return tags;
    }

    public Boolean getHidden() {
        return hidden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageDto that = (PackageDto) o;
        return id.equals(that.id) &&
                owner.equals(that.owner) &&
                name.equals(that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(license, that.license) &&
                Objects.equals(web, that.web) &&
                Objects.equals(version, that.version) &&
                Objects.equals(tags, that.tags) &&
                hidden.equals(that.hidden);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, name, description, license, web, version, tags, hidden);
    }
}
