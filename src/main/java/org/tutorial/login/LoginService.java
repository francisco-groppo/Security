package org.tutorial.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.tutorial.security.JwtResponse;
import org.tutorial.security.JwtTokenUtil;
import org.tutorial.security.LoginDetailsService;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authManager;
    private final JwtTokenUtil tokenUtil;
    private final LoginDetailsService loginDetailsService;
    private final LoginRepository loginRepository;

    public Optional<Login> findByEmail(String email) {
        return loginRepository.findByEmail(email);
    }

    public void save(Login login) {
        loginRepository.save(login);
    }

    public String logout(Principal principal) {

        tokenUtil.deleteToken(principal.getName());

        return "Logged out successfully";
    }

    public JwtResponse createAuthenticationToken(LoginDTO dto) {

        authenticate(dto.email(), dto.password());

        final UserDetails userDetails = loginDetailsService
                .loadUserByUsername(dto.email());

        return new JwtResponse(tokenUtil.generateToken(userDetails));
    }

    private void authenticate(String email, String password) {

        if (email == null || password == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_CREDENTIALS");
        }

        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_CREDENTIALS", e);
        }
    }
}
