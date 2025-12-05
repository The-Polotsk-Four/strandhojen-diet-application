package dk.polotsk.backend;


import dk.polotsk.backend.Catalog.Service.UserService;
import dk.polotsk.backend.Catalog.dto.UserCreateDto;
import dk.polotsk.backend.Catalog.dto.UserDto;
import dk.polotsk.backend.Catalog.model.User;
import dk.polotsk.backend.Catalog.model.Userrole;
import dk.polotsk.backend.Catalog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private UserService userService;


    @Test
    void shouldReturnUserByLogin() {
        // Arrange
        User mockUser = new User(1L, Userrole.ADMIN, "Johnny", "JohnnyTheMan67", "Johnny Boii");
        when(userRepository.findUserByLogin("Johnny"))
                .thenReturn(List.of(mockUser));

        // Act
        List<UserDto> result = userService.getByUserLogin("Johnny");

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Johnny Boii", result.get(0).name());
        assertEquals("Johnny", result.get(0).login());
        assertEquals(Userrole.ADMIN, result.get(0).userrole());
    }

    @Test
    void createUserTest() {
        // Arrange
        UserCreateDto dto = new UserCreateDto(1L, "Elvis", "ElvisTheGoat", Userrole.ADMIN, "Elvis");

        when(passwordEncoder.encode(any(String.class))).thenReturn("encoded_pw");

        User savedUser = new User(1L, Userrole.ADMIN, "Elvis", "encoded_pw", "ElvisTheGoat");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserDto result = userService.createUser(dto);

        // Assert
        assertEquals("ElvisTheGoat", result.name());
        assertEquals(Userrole.ADMIN, result.userrole());
        assertEquals(1L, result.id());

        verify(passwordEncoder).encode(any(String.class));
        verify(userRepository).save(any(User.class));
    }



}



