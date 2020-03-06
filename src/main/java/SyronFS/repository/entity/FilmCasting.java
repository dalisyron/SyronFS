package SyronFS.repository.entity;

import SyronFS.datasource.dto.ArtistDto;

import java.util.List;

public class FilmCasting {

    private final Film film;
    private final List<ArtistDto> artists;

    public FilmCasting(Film film, List<ArtistDto> artists) {
        this.film = film;
        this.artists = artists;
    }

    public Film getFilm() {
        return film;
    }

    public List<ArtistDto> getArtists() {
        return artists;
    }
}
