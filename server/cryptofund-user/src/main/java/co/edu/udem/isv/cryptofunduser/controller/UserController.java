package co.edu.udem.isv.cryptofunduser.controller;

import co.edu.udem.isv.cryptofunduser.model.User;
import co.edu.udem.isv.cryptofunduser.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/post")
    public User postUser(@RequestBody User user) {
        return this.userService.saveUser(user);
    }

    @GetMapping("/get/{id}")
    public Optional<User> findUserById(@PathVariable Long id) {
        return this.userService.findUserById(id);
    }

    @GetMapping(value = "/get", params = "email")
    public Optional<User> findUserByEmail(@RequestParam String email) {
        return this.userService.findUserByEmail(email);
    }

    @PostMapping(value = "/get/login")
    public Optional<User> findUserByEmailAndPassword(@RequestBody User user) {
        return this.userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    @PutMapping("/put/{id}")
    public void putUserPassword(@RequestBody User user, @PathVariable Long id) {
        this.userService.updateUserPassword(user, id);
    }

    @PutMapping(value = "/put/{id}", params = "walletAddress")
    public void putUserWalletAddress(@RequestParam String walletAddress, @PathVariable Long id) {
        this.userService.updateUserWalletAddress(walletAddress, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        this.userService.deleteUser(id);
    }
}
