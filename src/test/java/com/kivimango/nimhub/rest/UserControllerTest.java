package com.kivimango.nimhub.rest;

import com.kivimango.nimhub.config.SecurityConfig;
import com.kivimango.nimhub.data.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
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

    @Test
    public void testUserRegistrationShouldReturn401nOmittedValuesWithErrorMessages() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().string(containsString("username")))
                .andExpect(content().string(containsString("You must supply a username")))
                .andExpect(content().string(containsString("password")))
                .andExpect(content().string(containsString("You must supply a password")))
                .andExpect(content().string(containsString("email")))
                .andExpect(content().string(containsString("You must supply an email address")))
                .andReturn();
    }

    @Test
    public void testUserRegistrationShouldReturn401OnTooShortParams() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "us")
                .param("email", "em")
                .param("password", "pa"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(content().string(containsString("username")))
                .andExpect(content().string(containsString("The username must be at least 3 and max 100 characters long")))
                .andExpect(content().string(containsString("password")))
                .andExpect(content().string(containsString("The password must be at least 3 and max 255 characters long")))
                .andExpect(content().string(containsString("email")))
                .andExpect(content().string(containsString("The email must be at least 3 and max 255 characters long")))
                .andExpect(content().string(containsString("The email address must be a valid RFC822 email address")))
                .andReturn();
    }

    @Test
    public void testUserRegistrationShouldReturn401OnUsernameAlreadyExists() throws Exception {
        given(users.existsByUsername(actualUsername)).willReturn(true);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", actualUsername)
                .param("email", actualEmail)
                .param("password", actualPassword))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("username")))
                .andExpect(content().string(containsString("This username already exists.Please choose another one")))
                .andReturn();
    }

    @Test
    public void testUserRegistrationShouldReturn401OnEmailAddressAlreadyExists() throws Exception {
        given(users.existsByEmail(actualEmail)).willReturn(true);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", actualUsername)
                .param("email", actualEmail)
                .param("password", actualPassword))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("email")))
                .andExpect(content().string(containsString("This email address already in use.Please choose another one")))
                .andReturn();
    }

}
