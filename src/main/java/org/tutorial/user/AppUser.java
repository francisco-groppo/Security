package org.tutorial.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.tutorial.login.Login;
import org.tutorial.login.RegisterDTO;
import org.tutorial.login.Role;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.tutorial.regex.UserRegexUtils.emailPattern;
import static org.tutorial.regex.UserRegexUtils.passwordPattern;

@Entity
@NoArgsConstructor
@Getter
public class AppUser {

    @Transient
    private final int MAX_FIELD_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private LocalDateTime dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Login login;

    public AppUser buildFromDto(RegisterDTO dto) {

        validateDto(dto);

        this.login = new Login(dto.email(), dto.password(), true, Role.USER);
        this.name = dto.name();
        this.dateOfBirth = dto.dateOfBirth();

        return this;
    }

    private void validateDto(RegisterDTO dto) {
        if (dto.email() == null || !emailPattern.matcher(dto.email()).matches()) {
            throw new IllegalArgumentException("Invalid email.");
        }

        if (dto.password() == null || !passwordPattern.matcher(dto.password()).matches()) {
            throw new IllegalArgumentException("Invalid password.");
        }

        if (dto.name() == null || dto.name().isBlank() || dto.name().length() > 255) {
            throw new IllegalArgumentException("Invalid name.");
        }
    }


}
