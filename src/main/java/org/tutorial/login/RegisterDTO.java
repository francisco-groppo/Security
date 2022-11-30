package org.tutorial.login;

import java.time.LocalDateTime;

public record RegisterDTO(
        String email,
        String password,
        String name,
        LocalDateTime dateOfBirth
) {
}
