package com.kivimango.nimhub.data;

import com.kivimango.nimhub.util.TestData;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class Samples {
    static final User testUser = createUser();
    static final Package testPackage = createPackage();
    static final PackageUploadRequest testUploadForm = createTestUploadForm();

    private static User createUser() {
        User user = new User();
        user.setId(TestData.userId);
        user.setUsername(TestData.username);
        user.setEmail(TestData.email);
        return user;
    }

    private static Package createPackage() {
        Package pack = new Package();

        Set<Tag> tags = Collections.emptySet();
        Set<Package> tagOwner = new HashSet<>(1);

        tagOwner.add(pack);

        Tag tag = new Tag();
        tag.setName(TestData.tag);
        tag.setPackages(tagOwner);


        pack.setId(1L);
        pack.setName(TestData.packageName);
        pack.setOwner(Samples.testUser);
        pack.setDescription(TestData.description);

        pack.setTags(tags);
        pack.setVersion(TestData.version);
        pack.setWeb(TestData.web);
        pack.setLicense(TestData.license);
        return pack;
    }

    private static PackageUploadRequest createTestUploadForm() {
        return new TestUploadForm();
    }

    static final class TestUploadForm implements PackageUploadRequest {

        private String packageName = TestData.packageName;
        private String description = TestData.description;
        private String tag = TestData.tag;
        private String license = TestData.license;
        private String web = TestData.web;
        private String version =TestData.version;

        @Override
        public String getName() {
            return packageName;
        }

        @Override
        public void setName(String name) {
            this.packageName = name;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String getTagsString() {
            return tag;
        }

        @Override
        public void setTagsString(String tagsString) {
            this.tag = tagsString;
        }

        @Override
        public String getLicense() {
            return license;
        }

        @Override
        public void setLicense(String license) {
            this.license = license;
        }

        @Override
        public String getWeb() {
            return web;
        }

        @Override
        public void setWeb(String web) {
            this.web = web;
        }

        @Override
        public String getVersion() {
            return version;
        }

        @Override
        public void setVersion(String version) {
            this.version = version;
        }
    }
}
