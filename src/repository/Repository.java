package repository;

import datasource.artist.ArtistDataSource;
import datasource.film.FilmDataSource;

public class Repository {

    private FilmDataSource filmDataSource;
    private ArtistDataSource artistDataSource;

    public Repository(FilmDataSource filmDataSource, ArtistDataSource artistDataSource) {
        this.filmDataSource = filmDataSource;
        this.artistDataSource = artistDataSource;
    }

}
