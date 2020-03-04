package di;
import datasource.artist.ArtistDataSource;
import datasource.film.FilmDataSource;
import io.FileHandler;
import repository.Repository;

import java.io.File;

public class Injector {

    private static Injector instance = null;

    private Injector() {

    }

    File provideFilmFile() {
        return new File(AppConfig.FILM_PATH);
    }

    FileHandler provideFilmFileHandler() {
        return new FileHandler(provideFilmFile());
    }

    FilmDataSource provideFilmDataSource() {
        return new FilmDataSource(provideFilmFileHandler());
    }

    File provideArtistFile() {
        return new File(AppConfig.ARTIST_PATH);
    }

    FileHandler provideArtistFileHandler() {
        return new FileHandler(provideArtistFile());
    }

    ArtistDataSource provideArtistDataSource() {
        return new ArtistDataSource(provideArtistFileHandler());
    }

    Repository provideRepository() {
        return new Repository(provideFilmDataSource(), provideArtistDataSource());
    }

    public static Injector getInstance() {
        if (instance == null) {
            instance = new Injector();
        }
        return instance;
    }
}
