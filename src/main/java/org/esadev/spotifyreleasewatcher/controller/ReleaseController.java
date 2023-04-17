package org.esadev.spotifyreleasewatcher.controller;

import lombok.RequiredArgsConstructor;
import org.esadev.spotifyreleasewatcher.dto.TrackArtistsDto;
import org.esadev.spotifyreleasewatcher.service.UserSpotifyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.enums.AlbumType;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Artist;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("release")
@RequiredArgsConstructor
public class ReleaseController {
    private final UserSpotifyService userSpotifyService;

    @PostMapping
    public Map<Artist, Map<AlbumType, List<AlbumSimplified>>> trackFollowedArtists(@RequestBody TrackArtistsDto trackArtists) {
        Map<Artist, Map<AlbumType, List<AlbumSimplified>>> albumSimplifieds = userSpotifyService.trackFollowedArtists(trackArtists);
        //add items to playlists
        return albumSimplifieds;
    }
}
