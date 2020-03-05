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

    public File provideFilmFile() {
        return new File(AppConfig.FILM_PATH);
    }

    public FileHandler provideFilmFileHandler() {
        return new FileHandler(provideFilmFile());
    }

    public FilmDataSource provideFilmDataSource() {
        return new FilmDataSource(provideFilmFileHandler());
    }

    public File provideArtistFile() {
        return new File(AppConfig.ARTIST_PATH);
    }

    public FileHandler provideArtistFileHandler() {
        return new FileHandler(provideArtistFile());
    }

    public ArtistDataSource provideArtistDataSource() {
        return new ArtistDataSource(provideArtistFileHandler());
    }

    public Repository provideRepository() {
        return new Repository(provideFilmDataSource(), provideArtistDataSource());
    }

    public static Injector getInstance() {
        if (instance == null) {
            instance = new Injector();
        }
        return instance;
    }
}
