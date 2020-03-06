package SyronFS.di;
import SyronFS.datasource.artist.ArtistDataSource;
import SyronFS.datasource.film.FilmDataSource;
import SyronFS.datasource.index.IndexDataSource;
import SyronFS.io.FileHandler;
import SyronFS.repository.Repository;

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

    public FileHandler provideArtistIdIndexFileHandler() {
        return new FileHandler(new File(AppConfig.ARTIST_ID_INDEX_PATH));
    }

    public FileHandler provideArtistNameIndexFileHandler() {
        return new FileHandler(new File(AppConfig.ARTIST_NAME_INDEX_PATH));
    }

    public ArtistDataSource provideArtistDataSource() {
        return new ArtistDataSource(provideArtistFileHandler());
    }

    public IndexDataSource provideArtistIdIndexDataSource() {
        return new IndexDataSource(provideArtistIdIndexFileHandler(), provideArtistNameIndexFileHandler());
    }

    public Repository provideRepository() {
        return new Repository(provideFilmDataSource(), provideArtistDataSource(), provideArtistIdIndexDataSource());
    }

    public static Injector getInstance() {
        if (instance == null) {
            instance = new Injector();
        }
        return instance;
    }
}
