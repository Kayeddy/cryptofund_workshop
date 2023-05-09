package co.edu.udem.isv.cryptofunduser.service;

import co.edu.udem.isv.cryptofunduser.model.User;
import co.edu.udem.isv.cryptofunduser.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void updateUserPassword(User user, Long id) {
        userRepository.updateUserPassword(user.getPassword(), id);
    }

    public void updateUserWalletAddress(String newWalletAddress, Long id) {
        userRepository.updateUserWalletAddress(newWalletAddress, id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
