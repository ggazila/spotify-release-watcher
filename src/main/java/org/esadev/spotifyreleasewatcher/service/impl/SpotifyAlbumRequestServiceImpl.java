package org.esadev.spotifyreleasewatcher.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.esadev.spotifyreleasewatcher.exception.SpotifyRequestException;
import org.esadev.spotifyreleasewatcher.filter.SpotifyCodeFilter;
import org.esadev.spotifyreleasewatcher.service.SpotifyAlbumRequestService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SpotifyAlbumRequestServiceImpl implements SpotifyAlbumRequestService {
    @Override
    public TrackSimplified[] getTracksForAlbum(String albumId) {
        try {
            return spotifyRequest().getAlbumsTracks(albumId).build().execute().getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyRequestException("Can't get tracks for album " + albumId, e);
        }
    }

    private SpotifyApi spotifyRequest() {
        return new SpotifyApi.Builder()
                .setAccessToken(SpotifyCodeFilter.getCookieValue())
                .build();
    }
}
