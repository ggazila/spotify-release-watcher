package org.esadev.spotifyreleasewatcher.service.impl;

import lombok.RequiredArgsConstructor;
import org.esadev.spotifyreleasewatcher.dto.FollowedArtistsResponse;
import org.esadev.spotifyreleasewatcher.dto.TrackArtistsDto;
import org.esadev.spotifyreleasewatcher.service.SpotifyRequestService;
import org.esadev.spotifyreleasewatcher.service.UserSpotifyService;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.enums.AlbumType;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static se.michaelthelin.spotify.enums.ModelObjectType.ARTIST;

@Service
@RequiredArgsConstructor
public class UserSpotifyServiceImpl implements UserSpotifyService {
    private static final int DEFAULT_LIMIT_ARTIST = 50;
    private final SpotifyRequestService spotifyRequestService;

    @Override
    public Set<Artist> getAllFollowedArtists(String after, Integer limit) {
        int processed = 0;

        FollowedArtistsResponse followedArtists = getFollowedArtists("");
        Set<Artist> spotifyArtists = new HashSet<>(followedArtists.getFollowedArtists());

        do {
            spotifyArtists.addAll(followedArtists.getFollowedArtists());
            followedArtists = getFollowedArtists(followedArtists.getAfter());
            processed += DEFAULT_LIMIT_ARTIST;
        } while (processed < followedArtists.getTotal());

        return spotifyArtists;
    }

    private FollowedArtistsResponse getFollowedArtists(String after) {
        PagingCursorbased<Artist> pagingCursorbased = spotifyRequestService.getUsersFollowedArtistsRequest(DEFAULT_LIMIT_ARTIST, after, ARTIST);

        FollowedArtistsResponse followedArtistsResponse = new FollowedArtistsResponse();
        followedArtistsResponse.setFollowedArtists(Arrays.stream(pagingCursorbased.getItems()).toList());
        followedArtistsResponse.setAfter(pagingCursorbased.getCursors()[0].getAfter());
        followedArtistsResponse.setTotal(pagingCursorbased.getTotal());
        return followedArtistsResponse;
    }

    @Override
    public Map<Artist, Map<AlbumType, List<AlbumSimplified>>> trackFollowedArtists(TrackArtistsDto trackArtistsDto) {

        Set<Artist> followedArtists = getAllFollowedArtists("", DEFAULT_LIMIT_ARTIST);

        List<Artist> list = followedArtists.stream().toList();

        Map<Artist, Map<AlbumType, List<AlbumSimplified>>> result = new HashMap<>();

        for (Artist artist : list) {
            Map<AlbumType, List<AlbumSimplified>> artistAlbums = spotifyRequestService.getArtistAlbums(artist.getId(), trackArtistsDto).stream().collect(Collectors.groupingBy(AlbumSimplified::getAlbumType));
            if (!artistAlbums.isEmpty()) {
                result.put(artist, artistAlbums);
            }
        }
        return result;
    }
}
