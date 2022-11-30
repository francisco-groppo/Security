package org.tutorial.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.tutorial.login.Login;
import org.tutorial.login.LoginRepository;
import org.tutorial.login.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceRedis {

    private static final String BEARER_PREFIX = "Bearer ";

    private final RedisTemplate<String, String> redis;
    private final LoginRepository loginRepository;

    public void store(String user, String token) {
        redis.opsForValue().set(user, token);
    }

    public void delete(String user) {
        redis.delete(user);
    }

    public Optional<Authentication> authenticate(String email, HttpServletRequest request) {
        return extractBearerTokenHeader(request).flatMap(token -> lookup(email, token));
    }

    private Optional<Authentication> lookup(String email, String token) {
        try {
            String redisToken = this.redis.opsForValue().get(email);

            if (nonNull(redisToken) && redisToken.equals(token)) {

                Optional<Login> loginOptional = loginRepository.findByEmail(email);
                if (loginOptional.isEmpty()) {
                    log.warn("Email not found in DB.");
                    return Optional.empty();
                }

                Authentication authentication = createAuthentication(redisToken, loginOptional.get().getRole());
                return Optional.of(authentication);
            }

            return Optional.empty();
        } catch (Exception e) {
            log.warn("Unknown error while trying to look up Redis token", e);
            return Optional.empty();
        }
    }

    private static Optional<String> extractBearerTokenHeader(@NonNull HttpServletRequest request) {
        try {
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (nonNull(authorization)) {
                if (authorization.startsWith(BEARER_PREFIX)) {
                    String token = authorization.substring(BEARER_PREFIX.length()).trim();
                    if (!token.isBlank()) {
                        return Optional.of(token);
                    }
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            log.error("An unknown error occurred while trying to extract bearer token", e);
            return Optional.empty();
        }
    }

    private static Authentication createAuthentication(String actor, @NonNull Role... roles) {
        // The difference between `hasAuthority` and `hasRole` is that the latter uses the `ROLE_` prefix
        List<SimpleGrantedAuthority> authorities = Stream.of(roles)
                .distinct()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .toList();
        return new UsernamePasswordAuthenticationToken(nonNull(actor) ? actor : "N/A", "N/A", authorities);
    }

}
