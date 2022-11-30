package org.tutorial.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.tutorial.login.Login;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class LoginPrincipal implements UserDetails {

    private final Login login;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections
                .singleton(new SimpleGrantedAuthority(
                        "ROLE_" + login.getRole().toString()
                ));
    }

    @Override
    public String getPassword() {
        return login.getPassword();
    }

    @Override
    public String getUsername() {
        return login.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return login.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return login.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return login.isActive();
    }

    @Override
    public boolean isEnabled() {
        return login.isActive();
    }
}
