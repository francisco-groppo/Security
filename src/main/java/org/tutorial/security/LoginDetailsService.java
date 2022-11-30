package org.tutorial.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tutorial.login.Login;
import org.tutorial.login.LoginRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginDetailsService implements UserDetailsService {

    private final LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Login> login = loginRepository.findByEmail(email);

        if (login.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }

        return new LoginPrincipal(login.get());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return this;
    }
}
