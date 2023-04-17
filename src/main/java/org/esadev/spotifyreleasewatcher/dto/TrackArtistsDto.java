package org.esadev.spotifyreleasewatcher.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neovisionaries.i18n.CountryCode;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackArtistsDto {
    private List<AlbumType> albumType;
    private CountryCode market;
    private Integer limit;
    private Integer offset;
    private boolean isSeparatePlaylists;
    private Integer lastDays;
}
