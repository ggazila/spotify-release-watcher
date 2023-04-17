package org.esadev.spotifyreleasewatcher.service;

import org.esadev.spotifyreleasewatcher.dto.TrackArtistsDto;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.AbstractModelObject;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;

import java.util.List;

public interface SpotifyRequestService {

    <T extends AbstractModelObject> PagingCursorbased<T> getUsersFollowedArtistsRequest(Integer limit, String after, ModelObjectType type);

    List<AlbumSimplified> getArtistAlbums(String artistId, TrackArtistsDto trackArtistsDto);
}
