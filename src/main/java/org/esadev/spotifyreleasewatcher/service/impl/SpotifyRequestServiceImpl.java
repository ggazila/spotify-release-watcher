package org.esadev.spotifyreleasewatcher.service.impl;

import io.micrometer.common.util.StringUtils;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.ParseException;
import org.esadev.spotifyreleasewatcher.dto.AlbumType;
import org.esadev.spotifyreleasewatcher.dto.TrackArtistsDto;
import org.esadev.spotifyreleasewatcher.exception.SpotifyRequestException;
import org.esadev.spotifyreleasewatcher.filter.SpotifyCodeFilter;
import org.esadev.spotifyreleasewatcher.service.SpotifyRequestService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.enums.ReleaseDatePrecision;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.AbstractModelObject;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsAlbumsRequest;
import se.michaelthelin.spotify.requests.data.follow.GetUsersFollowedArtistsRequest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class SpotifyRequestServiceImpl implements SpotifyRequestService {

    public static final int DEFAULT_ARTIST_LIMIT = 20;
    public static final String RETRIEVING_FOLLOWED_ARTISTS_EXCEPTION_MESSAGE = "Error retrieving followed artists";
    public static final String RETRIEVING_ARTISTS_ALBUMS_EXCEPTION_MESSAGE = "Error retrieving artists' albums";

    @Override
    public <T extends AbstractModelObject> PagingCursorbased<T> getUsersFollowedArtistsRequest(Integer limit, String after, ModelObjectType type) {
        SpotifyApi spotifyApi = spotifyRequest();
        GetUsersFollowedArtistsRequest.Builder getUsersFollowedArtistsRequest =
                spotifyApi.getUsersFollowedArtists(type)
                        .limit(ofNullable(limit).orElse(DEFAULT_ARTIST_LIMIT));
        if (StringUtils.isNotEmpty(after)) {
            getUsersFollowedArtistsRequest.after(after);
        }

        try {
            return (PagingCursorbased<T>) getUsersFollowedArtistsRequest.build().execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyRequestException(RETRIEVING_FOLLOWED_ARTISTS_EXCEPTION_MESSAGE, e);
        }
    }

    public List<AlbumSimplified> getArtistAlbums(String artistId, TrackArtistsDto trackArtistsDto) {
        try {
            SpotifyApi spotifyApi = spotifyRequest();
            Paging<AlbumSimplified> simplifiedPaging;
            GetArtistsAlbumsRequest.Builder builder = buildArtistAlbumsRequest(artistId, trackArtistsDto, spotifyApi);
            simplifiedPaging = builder.build().execute();
            LocalDate startDate = LocalDate.now().minusDays(trackArtistsDto.getLastDays());
            return Arrays.stream(simplifiedPaging.getItems())
                    .filter(albumSimplified -> albumSimplified.getReleaseDatePrecision().equals(ReleaseDatePrecision.DAY))
                    .filter(albumSimplified -> LocalDate.parse(albumSimplified.getReleaseDate()).isAfter(startDate))
                    .toList();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            throw new SpotifyRequestException(RETRIEVING_ARTISTS_ALBUMS_EXCEPTION_MESSAGE, e);
        }

    }

    @Override
    @SneakyThrows
    public Optional<PlaylistSimplified> getUserPlaylist(String playlistName) {
        SpotifyApi spotifyApi = spotifyRequest();
        while (true) {
            Paging<PlaylistSimplified> paging = spotifyApi.getListOfCurrentUsersPlaylists().build().execute();
            Optional<PlaylistSimplified> matchedPlaylist = Arrays.stream(paging.getItems())
                    .filter(playlist -> playlist.getName().equals(playlistName))
                    .findAny();
            if (matchedPlaylist.isPresent()) {
                return matchedPlaylist;
            }
            if (paging.getNext() == null) {
                break;
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<PlaylistSimplified> createUserPlayList(String playlistName) {
        return Optional.empty();
    }


    private SpotifyApi spotifyRequest() {
        return new SpotifyApi.Builder()
                .setAccessToken(SpotifyCodeFilter.getCookieValue())
                .build();
    }

    private GetArtistsAlbumsRequest.Builder buildArtistAlbumsRequest(String artistId, TrackArtistsDto trackArtistsDto, SpotifyApi spotifyApi) {
        GetArtistsAlbumsRequest.Builder builder = spotifyApi.getArtistsAlbums(artistId);

        if (!Objects.isNull(trackArtistsDto.getMarket())) {
            builder.market(trackArtistsDto.getMarket());
        }
        if (!Objects.isNull(trackArtistsDto.getAlbumType()) && !trackArtistsDto.getAlbumType().isEmpty()) {
            List<String> albumType = trackArtistsDto.getAlbumType().stream().map(AlbumType::getType).toList();
            builder.album_type(String.join(",", albumType));
        }
        return builder;
    }
}
