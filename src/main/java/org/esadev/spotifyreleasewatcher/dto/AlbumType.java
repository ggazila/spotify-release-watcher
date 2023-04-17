package org.esadev.spotifyreleasewatcher.dto;

public enum AlbumType {
    ALBUM("album"),
    SINGLE("single"),
    APPEARS_ON("appears_on"),
    COMPILATION("compilation");
    private final String type;

    AlbumType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
