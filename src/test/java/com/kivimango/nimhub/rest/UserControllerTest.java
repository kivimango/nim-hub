package com.kivimango.nimhub.rest;

import com.kivimango.nimhub.data.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository users;

    private final String actualUsername = "test_user";
    private final String actualEmail = "testuser@test.com";
    private final String actualPassword = "aVeRySeCrEtPaSsWoRd";

    @Test
    public void testUserRegisterShouldReturn200() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", actualUsername)
                .param("email", actualEmail)
                .param("password", actualPassword))
                .andExpect(status().isOk());
    }

}
