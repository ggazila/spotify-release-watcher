package org.esadev.spotifyreleasewatcher.service;

import org.esadev.spotifyreleasewatcher.dto.ArtistRelease;
import org.esadev.spotifyreleasewatcher.dto.TrackArtistsDto;
import se.michaelthelin.spotify.model_objects.specification.Artist;

import java.util.List;
import java.util.Set;

public interface UserSpotifyService {

    Set<Artist> getAllFollowedArtists(String after, Integer limit);

    List<ArtistRelease> trackFollowedArtists(TrackArtistsDto trackArtistsDto);
}
