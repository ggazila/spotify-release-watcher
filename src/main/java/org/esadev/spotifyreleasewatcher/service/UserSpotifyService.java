package org.esadev.spotifyreleasewatcher.service;

import org.esadev.spotifyreleasewatcher.dto.TrackArtistsDto;
import se.michaelthelin.spotify.enums.AlbumType;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Artist;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserSpotifyService {

    Set<Artist> getAllFollowedArtists(String after, Integer limit);

    Map<Artist, Map<AlbumType, List<AlbumSimplified>>> trackFollowedArtists(TrackArtistsDto trackArtistsDto);
}
