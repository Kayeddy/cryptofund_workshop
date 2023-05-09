package co.edu.udem.isv.cryptofunduser.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;


@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String password;
    private String name;
    private Date birthdate;
    private String walletAddress;
    private String about;
}
