package co.edu.udem.isv.cryptofunduser.service;

import co.edu.udem.isv.cryptofunduser.model.User;
import co.edu.udem.isv.cryptofunduser.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void UserService_PostUser_ReturnsSavedUser() {
        User user = User.builder()
                .userId(1L)
                .email("federico29.mg@gmail.com")
                .password("123")
                .name("Federico")
                .birthdate(Date.valueOf("1998-10-29"))
                .walletAddress("1Lbcfr7sAHTD9CgdQo3HTMTkV8LK4ZnX71")
                .about("Hi! I'm Federico")
                .build();

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertTrue(savedUser.getUserId() > 0);
    }

    @Test
    public void UserService_GetUser_ReturnsUserByID() {
        Long userId = 1L;

        User user = User.builder()
                .userId(userId)
                .email("federico29.mg@gmail.com")
                .password("123")
                .name("Federico")
                .birthdate(Date.valueOf("1998-10-29"))
                .walletAddress("1Lbcfr7sAHTD9CgdQo3HTMTkV8LK4ZnX71")
                .about("Hi! I'm Federico")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));

        Optional<User> foundUser = userService.findUserById(userId);

        Assertions.assertTrue(foundUser.isPresent());
        assert user != null;
        Assertions.assertSame(user.getUserId(), foundUser.get().getUserId());
    }

    @Test
    public void UserService_GetUser_ReturnsUserByEmail() {
        String email = "federico29.mg@gmail.com";

        User user = User.builder()
                .userId(1L)
                .email(email)
                .password("123")
                .name("Federico")
                .birthdate(Date.valueOf("1998-10-29"))
                .walletAddress("1Lbcfr7sAHTD9CgdQo3HTMTkV8LK4ZnX71")
                .about("Hi! I'm Federico")
                .build();

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.ofNullable(user));

        Optional<User> foundUser = userService.findUserByEmail(email);

        Assertions.assertTrue(foundUser.isPresent());
        assert user != null;
        Assertions.assertSame(user.getEmail(), foundUser.get().getEmail());
    }

    @Test
    public void UserService_UpdateUser_ChecksUpdatedPassword() {
        Long userId = 1L;
        User user = User.builder().password("321").build();

        doNothing().when(userRepository).updateUserPassword(user.getPassword(), userId);

        userService.updateUserPassword(user, userId);

        verify(userRepository, times(1)).updateUserPassword(user.getPassword(), userId);
    }

    @Test
    public void UserService_UpdateUser_ChecksUpdatedWalletAddress() {
        Long userId = 1L;
        String newWalletAddress = "3FZbgi29cpjq2GjdwV8eyHuJJnkLtktZc5";

        doNothing().when(userRepository).updateUserWalletAddress(newWalletAddress, userId);

        userService.updateUserWalletAddress(newWalletAddress, userId);

        verify(userRepository, times(1)).updateUserWalletAddress(newWalletAddress, userId);
    }

    @Test
    public void UserService_DeleteUser_ChecksNonExistence() {
        Long userId = 1L;

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}

