package org.tutorial.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tutorial.login.LoginDTO;
import org.tutorial.login.RegisterDTO;
import org.tutorial.security.JwtResponse;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {

        return ResponseEntity.ok(appUserService.register(registerDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDTO dto) {

        return ResponseEntity.ok(appUserService.login(dto));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<String> logout(Principal principal) {

        return ResponseEntity.ok(appUserService.logout(principal));
    }
}
