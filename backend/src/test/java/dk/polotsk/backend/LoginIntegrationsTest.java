package dk.polotsk.backend;

import dk.polotsk.backend.Catalog.model.User;
import dk.polotsk.backend.Catalog.model.Userrole;
import dk.polotsk.backend.Catalog.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        User user = new User(null, Userrole.ADMIN, "johnny",
                passwordEncoder.encode("mypassword"),
                "Johnny Boii");
        userRepository.save(user);
    }

    @Test
    void loginShouldReturn200WhenCredentialsAreCorrect() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "johnny")
                        .param("password", "mypassword"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void loginShouldReturn401OnInvalidPassword() throws Exception {
        mockMvc.perform(post("/login")
                        .param("username", "johnny")
                        .param("password", "wrong"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

}

