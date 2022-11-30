package org.tutorial.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.tutorial.login.LoginDTO;
import org.tutorial.login.LoginService;
import org.tutorial.login.RegisterDTO;
import org.tutorial.security.JwtResponse;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final LoginService loginService;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder encoder;

    public String register(RegisterDTO registerDTO) {

        AppUser user;

        try {
            user = new AppUser().buildFromDto(registerDTO);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        if (loginService.findByEmail(registerDTO.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists.");
        }

        user.getLogin().setPassword(
                user.getLogin().getPassword(),
                encoder
        );

        user = appUserRepository.save(user);
        user.getLogin().setUser(user);
        loginService.save(user.getLogin());

        return "User saved successfully";
    }

    public JwtResponse login(LoginDTO dto) {

        return loginService.createAuthenticationToken(dto);
    }

    public String logout(Principal principal) {

        return loginService.logout(principal);
    }
}
