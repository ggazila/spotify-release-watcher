package org.esadev.spotifyreleasewatcher.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Optional;

import static org.esadev.spotifyreleasewatcher.RequestUtils.extractCookieValue;


public class SpotifyCodeFilter extends OncePerRequestFilter {
    private static final ThreadLocal<String> spotifyCode = new ThreadLocal<>();

    public static String getCookieValue() {
        return spotifyCode.get();
    }

    @Override
    @SneakyThrows
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) {
        Optional<Cookie> value = extractCookieValue(request, "spotifyCode");
        value.ifPresent(cookie -> spotifyCode.set(cookie.getValue()));
        try {
            filterChain.doFilter(request, response);
        } finally {
            spotifyCode.remove();
        }
    }

}
