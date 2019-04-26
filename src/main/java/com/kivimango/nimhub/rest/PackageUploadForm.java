package com.kivimango.nimhub.rest;

import com.kivimango.nimhub.data.PackageUploadRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

final class PackageUploadForm implements PackageUploadRequest {

    @NotNull(message = "You must supply a package name")
    @Size(min = 3, max = 100, message = "The package name must be at least {min} and max {max} characters long")
    @Pattern(regexp = "^[a-z0-9-_]+$", message = "The name should only contain lowercase alphanumeric characters and underscores")
    private String name;

    @NotNull(message = "You must supply a package description")
    @Size(min = 3, max = 500, message = "The description must be at least {min} and max {max} characters long")
    private String description;

    @Pattern(regexp = "^[a-z0-9-_]*$", message = "The tag should contain only lowercase alphanumeric characters and underscores")
    private String tagsString;

    @Size(max = 50, message = "The license should be max 50 characters long")
    private String license;

    @Size(max = 255, message = "The web url should be max 255 characters long")
    private String web;

    @NotNull(message = "You must supply a package version")
    @Size(min = 1, max = 50, message = "The package version must be at least {min} and max {max} characters long")
    private String version;

    @NotNull(message = "You must supply a package file")
    private MultipartFile file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagsString() {
        return tagsString;
    }

    public void setTagsString(String tagsString) {
        this.tagsString = tagsString;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
