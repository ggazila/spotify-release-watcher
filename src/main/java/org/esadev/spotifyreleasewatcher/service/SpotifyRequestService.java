package org.esadev.spotifyreleasewatcher.service;

import org.esadev.spotifyreleasewatcher.dto.TrackArtistsDto;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.AbstractModelObject;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

import java.util.List;
import java.util.Optional;

public interface SpotifyRequestService {

    <T extends AbstractModelObject> PagingCursorbased<T> getUsersFollowedArtistsRequest(Integer limit, String after, ModelObjectType type);

    List<AlbumSimplified> getArtistAlbums(String artistId, TrackArtistsDto trackArtistsDto);

    Optional<PlaylistSimplified> getUserPlaylist(String playlistName);

    Optional<PlaylistSimplified> createUserPlayList(String playlistName);
}
