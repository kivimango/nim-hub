package com.kivimango.nimhub.rest;

import com.kivimango.nimhub.config.SecurityConfig;
import com.kivimango.nimhub.data.*;
import com.kivimango.nimhub.util.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.InputStream;
import static com.kivimango.nimhub.util.TestData.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PackageController.class)
@Import(SecurityConfig.class)
@WithMockUser(username = "user1", password = "pwd", roles = "USER")
public class PackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PackageRepository dao;

    @MockBean
    private PackageStore repository;

    @MockBean
    private PackageService packages;

    private InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("lib-1.0-FINAL.tar.gz");

    @Test
    public void testUploadPackageShouldReturn201() throws Exception {
        MockMultipartFile packageFile = new MockMultipartFile("package", "lib-1.0-FINAL.tar.gz", "application/gzip", inputStream);
        given(packages.save(Mockito.any(), Mockito.any(), Mockito.any())).willReturn(TestData.dto);

        mockMvc.perform(multipart("/packages")
                .file("file", packageFile.getBytes())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("name", packageName)
                .param("description", description)
                .param("tagsString", TestData.tag)
                .param("license", license)
                .param("web", web)
                .param("version", version))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is(packageName)))
                .andExpect(jsonPath("$.description", is(description)))
                .andExpect(jsonPath("$.tags[0].tag", is(tag)))
                .andExpect(jsonPath("$.license", is(license)))
                .andExpect(jsonPath("$.web", is(web)))
                .andExpect(jsonPath("$.version", is(version)))
                .andExpect(jsonPath("$.hidden", is(false)))
                .andExpect(jsonPath("$.owner.username", is(dto.getOwner().getUsername())))
                .andReturn();
    }

    @Test
    public void testUploadPackageShouldReturn400OnMandatoryFieldsOmittedValuesWithErrorMessages() throws Exception {
        mockMvc.perform(multipart("/packages")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(content().string(containsString("file")))
                .andExpect(content().string(containsString("You must supply a package file")))
                .andExpect(content().string(containsString("name")))
                .andExpect(content().string(containsString("You must supply a package name")))
                .andExpect(content().string(containsString("description")))
                .andExpect(content().string(containsString("You must supply a package description")))
                .andExpect(content().string(containsString("version")))
                .andExpect(content().string(containsString("You must supply a package version")))
                .andReturn();
    }

    @Test
    public void testUploadPackagesShouldReturn400OnTooShortParams() throws Exception {
        MockMultipartFile packageFile = new MockMultipartFile("package", "lib-1.0-FINAL.tar.gz", "application/gzip", inputStream);
        mockMvc.perform(multipart("/packages")
                .file("file", packageFile.getBytes())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("name", "na")
                .param("description", "de")
                .param("tagsString", "INVALID!testString")
                .param("version", "0.1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().string(containsString("name")))
                .andExpect(content().string(containsString("The package name must be at least 3 and max 100 characters long")))
                .andExpect(content().string(containsString("description")))
                .andExpect(content().string(containsString("The description must be at least 3 and max 500 characters long")))
                .andExpect(content().string(containsString("tagsString")))
                .andExpect(content().string(containsString("The tag should contain only lowercase alphanumeric characters and underscores")))
                .andReturn();
    }

    @Test
    public void testUploadShouldReturn409nAlreadyExistingPackage() throws Exception {
        MockMultipartFile packageFile = new MockMultipartFile("package", "lib-1.0-FINAL.tar.gz", "application/gzip", inputStream);
        given(packages.isExists(Mockito.any(), Mockito.any())).willReturn(true);
        given(repository.exists(Mockito.anyString(), Mockito.anyString())).willReturn(true);
        mockMvc.perform(multipart("/packages")
                .file("file", packageFile.getBytes())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("name", packageName)
                .param("description", description)
                .param("version", "1.0-FINAL"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testDownloadShouldReturn200WithGzipFile() throws Exception {
        MockMultipartFile packageFile = new MockMultipartFile("package", "lib-1.0-FINAL.tar.gz", "application/gzip", inputStream);
        String testLibPath = ResourceUtils.getFile(this.getClass().getResource("/lib-1.0-FINAL.tar.gz")).getPath();
        given(packages.get("lib-test", "1.0-FINAL")).willReturn(testLibPath);
        MockHttpServletResponse response = mockMvc.perform(get("/packages/lib-test/1.0-FINAL"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/gzip"))
                .andReturn().getResponse();

        assertArrayEquals(packageFile.getBytes(), response.getContentAsByteArray());
    }

}
