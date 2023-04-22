package org.esadev.spotifyreleasewatcher.service;

import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

public interface SpotifyAlbumRequestService {
    TrackSimplified[] getTracksForAlbum(String albumId);
}
