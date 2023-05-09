package co.edu.udem.isv.cryptofunduser.repository;

import co.edu.udem.isv.cryptofunduser.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_PostUser_ReturnsSavedUser() {
        User user = User.builder()
                .email("federico29.mg@gmail.com")
                .password("123")
                .name("Federico")
                .birthdate(Date.valueOf("1998-10-29"))
                .walletAddress("1Lbcfr7sAHTD9CgdQo3HTMTkV8LK4ZnX71")
                .about("Hi! I'm Federico")
                .build();

        User savedUser = userRepository.save(user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertTrue(savedUser.getUserId() > 0);
    }

    @Test
    public void UserRepository_GetUser_ReturnsUserByID() {
        User user = User.builder()
                .email("federico29.mg@gmail.com")
                .password("123")
                .name("Federico")
                .birthdate(Date.valueOf("1998-10-29"))
                .walletAddress("1Lbcfr7sAHTD9CgdQo3HTMTkV8LK4ZnX71")
                .about("Hi! I'm Federico")
                .build();

        User savedUser = userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(savedUser.getUserId());

        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertSame(savedUser.getUserId(), foundUser.get().getUserId());
    }

    @Test
    public void UserRepository_GetUser_ReturnsUserByEmail() {
        User user = User.builder()
                .email("federico29.mg@gmail.com")
                .password("123")
                .name("Federico")
                .birthdate(Date.valueOf("1998-10-29"))
                .walletAddress("1Lbcfr7sAHTD9CgdQo3HTMTkV8LK4ZnX71")
                .about("Hi! I'm Federico")
                .build();

        User savedUser = userRepository.save(user);

        Optional<User> foundUser = userRepository.findUserByEmail(savedUser.getEmail());

        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertSame(savedUser.getEmail(), foundUser.get().getEmail());
    }

    @Test
    public void UserRepository_UpdateUser_ChecksUpdatedPassword() {
        String newPassword = "321";

        User user = User.builder()
                .email("federico29.mg@gmail.com")
                .password("123")
                .name("Federico")
                .birthdate(Date.valueOf("1998-10-29"))
                .walletAddress("1Lbcfr7sAHTD9CgdQo3HTMTkV8LK4ZnX71")
                .about("Hi! I'm Federico")
                .build();

        User savedUser = userRepository.save(user);

        userRepository.updateUserPassword(newPassword, savedUser.getUserId());

        Optional<User> foundUser = userRepository.findById(savedUser.getUserId());

        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertSame(newPassword, foundUser.get().getPassword());
    }

    @Test
    public void UserRepository_UpdateUser_ChecksUpdatedWalletAddress() {
        String newAddress = "3FZbgi29cpjq2GjdwV8eyHuJJnkLtktZc5";

        User user = User.builder()
                .email("federico29.mg@gmail.com")
                .password("123")
                .name("Federico")
                .birthdate(Date.valueOf("1998-10-29"))
                .walletAddress("1Lbcfr7sAHTD9CgdQo3HTMTkV8LK4ZnX71")
                .about("Hi! I'm Federico")
                .build();

        User savedUser = userRepository.save(user);

        userRepository.updateUserWalletAddress(newAddress, savedUser.getUserId());

        Optional<User> foundUser = userRepository.findById(savedUser.getUserId());

        Assertions.assertTrue(foundUser.isPresent());
        Assertions.assertSame(newAddress, foundUser.get().getWalletAddress());
    }

    @Test
    public void UserRepository_DeleteUser_ChecksNonExistence() {
        User user = User.builder()
                .email("federico29.mg@gmail.com")
                .password("123")
                .name("Federico")
                .birthdate(Date.valueOf("1998-10-29"))
                .walletAddress("1Lbcfr7sAHTD9CgdQo3HTMTkV8LK4ZnX71")
                .about("Hi! I'm Federico")
                .build();

        User savedUser = userRepository.save(user);

        userRepository.deleteById(savedUser.getUserId());

        Optional<User> foundUser = userRepository.findById(savedUser.getUserId());

        Assertions.assertTrue(foundUser.isEmpty());
    }
}
