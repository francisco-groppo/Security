package org.tutorial.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.tutorial.user.AppUser;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String email;
    @JsonIgnore
    private String password;
    private boolean active;
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private AppUser user;

    public Login(String email, String password, boolean active, Role role) {
        this.email = email;
        this.password = password;
        this.active = active;
        this.role = role;
    }

    public void setPassword(String password, PasswordEncoder encoder) {
        this.password = encoder.encode(password);
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
