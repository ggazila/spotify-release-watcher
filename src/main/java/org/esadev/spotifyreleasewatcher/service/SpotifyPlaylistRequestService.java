package org.esadev.spotifyreleasewatcher.service;


public interface SpotifyPlaylistRequestService {
    String getUserPlaylist(String playlistName);

    String createUserPlayList(String playlistName, String userId);
}
