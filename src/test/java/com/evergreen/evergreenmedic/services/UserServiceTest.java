package com.evergreen.evergreenmedic.services;


import com.evergreen.evergreenmedic.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    void testGetUserById() {
//        when()
        Short userId = 1;
//        assertThrows(EntityNotFoundException.class, () -> {
//            userService.getUserById(userId).orElseThrow(() -> new EntityNotFoundException("User not found."));
//        });
    }


}