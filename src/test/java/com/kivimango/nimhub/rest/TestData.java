package com.kivimango.nimhub.rest;

import com.kivimango.nimhub.data.AuthorDto;
import com.kivimango.nimhub.data.PackageDto;
import com.kivimango.nimhub.data.TagDto;

import java.util.HashSet;
import java.util.Set;

class TestData {
    static final String packageName = "test-lib";
    static final String description = "Test description";
    static final String tag = "test";
    static final String license = "MIT";
    static final String web = "https://github.com/kivimango/nim-hub";
    static final String version = "1.0-STABLE";

    static Set<TagDto> getTestTagDtos() {
        Set<TagDto> tagDto = new HashSet<>();
        tagDto.add(TagDto.of(tag));
        return tagDto;
    }

    static PackageDto dto = PackageDto.of(1L, AuthorDto.of(1L, "testUser", "test@testemail.com"), packageName, description, license, web, version, getTestTagDtos(), false);
}
