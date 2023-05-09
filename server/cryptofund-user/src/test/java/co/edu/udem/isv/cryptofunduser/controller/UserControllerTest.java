package co.edu.udem.isv.cryptofunduser.controller;

import co.edu.udem.isv.cryptofunduser.model.User;
import co.edu.udem.isv.cryptofunduser.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void UserController_PostUser_ReturnsSavedUser() throws Exception {
        given(userService.saveUser(any(User.class))).willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        User user = User.builder()
                .userId(1L)
                .email("federico29.mg@gmail.com")
                .password("123")
                .name("Federico")
                .birthdate(Date.valueOf("1998-10-29"))
                .walletAddress("1Lbcfr7sAHTD9CgdQo3HTMTkV8LK4ZnX71")
                .about("Hi! I'm Federico")
                .build();

        this.mockMvc.perform(post("/api/users/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(user.getUserId().intValue())));
    }

    @Test
    public void UserController_GetUser_ReturnsUserById() throws Exception {
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

        given(userService.findUserById(userId)).willReturn(Optional.of(user));

        this.mockMvc.perform(get("/api/users/get/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(user.getUserId().intValue())));
    }

    @Test
    public void UserController_GetUser_ReturnsUserByEmail() throws Exception {
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

        given(userService.findUserByEmail(email)).willReturn(Optional.of(user));

        this.mockMvc.perform(get("/api/users/get").param("email", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(user.getUserId().intValue())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    public void UserController_UpdateUser_ChecksUpdatedPassword() throws Exception {
        Long userId = 1L;
        User user = User.builder().password("321").build();

        doNothing().when(userService).updateUserPassword(user, userId);

        this.mockMvc.perform(put("/api/users/put/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateUserPassword(user, userId);
    }

    @Test
    public void UserController_UpdateUser_ChecksUpdatedWalletAddress() throws Exception {
        Long userId = 1L;
        String newWalletAddress = "3FZbgi29cpjq2GjdwV8eyHuJJnkLtktZc5";

        doNothing().when(userService).updateUserWalletAddress(newWalletAddress, userId);

        this.mockMvc.perform(put("/api/users/put/{id}", userId).param("walletAddress", newWalletAddress))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateUserWalletAddress(newWalletAddress, userId);
    }

    @Test
    public void UserController_DeleteUser_ChecksNonExistence() throws Exception {
        Long userId = 1L;

        doNothing().when(userService).deleteUser(userId);

        this.mockMvc.perform(delete("/api/users/delete/{id}", userId))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(userId);
    }
}
