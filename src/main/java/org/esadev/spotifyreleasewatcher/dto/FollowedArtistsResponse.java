package org.esadev.spotifyreleasewatcher.dto;

import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.Artist;

import java.util.List;

@Data
public class FollowedArtistsResponse {
    List<Artist> followedArtists;
    Integer total;
    String after;
}
