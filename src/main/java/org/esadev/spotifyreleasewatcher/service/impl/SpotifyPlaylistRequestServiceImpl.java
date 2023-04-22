package org.esadev.spotifyreleasewatcher.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.esadev.spotifyreleasewatcher.exception.SpotifyRequestException;
import org.esadev.spotifyreleasewatcher.filter.SpotifyCodeFilter;
import org.esadev.spotifyreleasewatcher.service.SpotifyPlaylistRequestService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpotifyPlaylistRequestServiceImpl implements SpotifyPlaylistRequestService {
    @Override
    public String getUserPlaylist(String playlistName) {
        SpotifyApi spotifyApi = spotifyRequest();
        while (true) {
            try {
                Paging<PlaylistSimplified> paging = spotifyApi.getListOfCurrentUsersPlaylists().build().execute();
                Optional<PlaylistSimplified> matchedPlaylist = Arrays.stream(paging.getItems())
                        .filter(playlist -> playlist.getName().equals(playlistName))
                        .findAny();
                if (matchedPlaylist.isPresent()) {
                    return matchedPlaylist.get().getId();
                }
                if (paging.getNext() == null) {
                    break;
                }
            } catch (IOException | SpotifyWebApiException | ParseException e) {
                throw new SpotifyRequestException("Cant' get playlist " + playlistName, e);
            }
        }
        return "";
    }

    @Override
    public String createUserPlayList(String playlistName, String userId) {
        try {
            if (userId.isEmpty()) {
                userId = spotifyRequest().getCurrentUsersProfile().build().execute().getId();
            }
            Playlist createdPlaylist = spotifyRequest().createPlaylist(userId, playlistName).build().execute();
            return createdPlaylist.getId();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private SpotifyApi spotifyRequest() {
        return new SpotifyApi.Builder()
                .setAccessToken(SpotifyCodeFilter.getCookieValue())
                .build();
    }
}
