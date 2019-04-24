package com.kivimango.nimhub.rest;

import com.kivimango.nimhub.config.SecurityConfig;
import com.kivimango.nimhub.data.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.io.InputStream;
import static com.kivimango.nimhub.rest.TestData.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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

    private InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("lib.tar.gz");

    @Test
    public void testUploadPackageShouldReturn201() throws Exception {
        MockMultipartFile packageFile = new MockMultipartFile("package", "lib.tar.gz", "application/gzip", inputStream);
        given(packages.save(Mockito.any(), Mockito.any(), Mockito.any())).willReturn(TestData.dto);

        mockMvc.perform(multipart("/packages")
                .file("file", packageFile.getBytes())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("name", packageName)
                .param("description", description)
                .param("tagString", TestData.tag)
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
}
