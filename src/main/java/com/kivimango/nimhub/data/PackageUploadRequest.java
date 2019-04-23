package com.kivimango.nimhub.data;

public interface PackageUploadRequest {
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
    String getTagsString();
    void setTagsString(String tagsString);
    String getLicense();
    void setLicense(String license);
    String getWeb();
    void setWeb(String web);
    String getVersion();
    void setVersion(String version);
}
