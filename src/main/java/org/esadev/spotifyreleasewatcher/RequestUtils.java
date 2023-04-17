package org.esadev.spotifyreleasewatcher;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUtils {

    public static Optional<Cookie> extractCookieValue(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return Optional.empty();
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny();
    }

}
