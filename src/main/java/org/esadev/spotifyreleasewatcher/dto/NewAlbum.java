package org.esadev.spotifyreleasewatcher.dto;

import lombok.Data;
import se.michaelthelin.spotify.enums.AlbumGroup;
import se.michaelthelin.spotify.enums.AlbumType;

@Data
public class NewAlbum {
    private AlbumGroup albumGroup;
    private AlbumType albumType;
    private String id;
    private String name;
}
