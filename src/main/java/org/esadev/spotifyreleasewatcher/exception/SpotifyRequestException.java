package org.esadev.spotifyreleasewatcher.exception;

public class SpotifyRequestException extends RuntimeException {
    public SpotifyRequestException(String message, Exception cause) {
        super(message, cause);
    }
}
