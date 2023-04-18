package org.esadev.spotifyreleasewatcher.dto;

import lombok.Data;
import se.michaelthelin.spotify.enums.ModelObjectType;

import java.util.List;

@Data
public class ArtistRelease {
    List<NewAlbum> albums;
    private String name;
    private ModelObjectType type;
}
