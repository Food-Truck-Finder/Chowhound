package com.chowhound.chowhound.repos;

import com.chowhound.chowhound.ChowhoundApplication;
import com.chowhound.chowhound.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChowhoundApplication.class)
@AutoConfigureMockMvc
public class UserRepoTests {

    private User testUser;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception {
        testUser = userRepo.findByUsername("testUser");

        //create the test user if not exists
        if(testUser == null) {
            User newUser = new User();
            newUser.setUsername("testUser");
            newUser.setPassword(passwordEncoder.encode("pass"));
            newUser.setEmail("testUser@chowhound.com");
            testUser = userRepo.save(newUser);
        }
    }

    @Test
    public void testUserRegistration() throws Exception {
        mvc.perform(post("/register").with(csrf())
                .param("username", "user1")
                .param("email", "user1@test.com")
                .param("password","pass")
        )
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/login"));

    }

    @Test
    public void testUserLogin() throws Exception {
        User userToInsert = new User();
        userToInsert.setUsername("user2");
        userToInsert.setPassword(passwordEncoder.encode("pass"));
        userToInsert.setEmail("user2@test.com");
        userRepo.save(userToInsert);

        mvc.perform(post("/login").with(csrf())
        .param("username", "user2")
        .param("password", "pass")
        )
        .andExpect(status().is(HttpStatus.FOUND.value()))
        .andExpect(redirectedUrl("/home"));
    }

    @Test
    public void testRegisterDuplicateUsername() throws Exception {
        mvc.perform(post("/register").with(csrf())
                .param("username", "testUser")
                .param("email", "testUser@chowhound.com")
                .param("password",passwordEncoder.encode("pass"))
        )
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/register?error"));
    }

    @Test
    public void testLoginWithoutPassword() throws Exception {
        mvc.perform(post("/login").with(csrf())
                .param("username", "testUser")
                .param("password",passwordEncoder.encode(""))
        )
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    public void testLoginWithWrongPassword() throws Exception {
        mvc.perform(post("/login").with(csrf())
                .param("username", "testUser")
                .param("password",passwordEncoder.encode("PASS"))
        )
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    public void testLoginWithoutUsername() throws Exception {
        mvc.perform(post("/login").with(csrf())
                .param("username", "")
                .param("password",passwordEncoder.encode("pass"))
        )
                .andExpect(status().is(HttpStatus.FOUND.value()))
                .andExpect(redirectedUrl("/login?error"));
    }


    @After
    public void cleanOut(){

        User userToDelete = userRepo.findByUsername("user1");
        if (userToDelete!=null){
        userRepo.delete(userToDelete);
        }
        userToDelete = userRepo.findByUsername("user2");
        if (userToDelete!=null){
        userRepo.delete(userToDelete);
        }
    }

}