package se.jensen.meiying.socialapp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import se.jensen.meiying.socialapp.dto.UserRegistrationDTO;
import se.jensen.meiying.socialapp.logging.AppLogger;
import se.jensen.meiying.socialapp.model.User;
import se.jensen.meiying.socialapp.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AppLogger appLogger;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_success() {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setUsername("test");
        dto.setPassword("password");

        when(userRepository.existsByUsername("test")).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("hashed");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User u = invocation.getArgument(0);
                    u.setId(1L);
                    return u;
                });

        User user = userService.createUser(dto);

        assertEquals("test", user.getUsername());
        assertEquals(1L, user.getId());
    }
}
