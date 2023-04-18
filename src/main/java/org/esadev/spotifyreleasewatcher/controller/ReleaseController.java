package org.esadev.spotifyreleasewatcher.controller;

import lombok.RequiredArgsConstructor;
import org.esadev.spotifyreleasewatcher.dto.ArtistRelease;
import org.esadev.spotifyreleasewatcher.dto.TrackArtistsDto;
import org.esadev.spotifyreleasewatcher.service.UserSpotifyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("release")
@RequiredArgsConstructor
public class ReleaseController {
    private final UserSpotifyService userSpotifyService;

    @PostMapping
    public List<ArtistRelease> trackFollowedArtists(@RequestBody TrackArtistsDto trackArtists) {
        List<ArtistRelease> albumSimplifieds = userSpotifyService.trackFollowedArtists(trackArtists);
        //add items to playlists
        return albumSimplifieds;
    }
}
