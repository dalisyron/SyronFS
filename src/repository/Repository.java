package repository;

import datasource.artist.ArtistDataSource;
import datasource.artist.mapper.ArtistDtoMappers;
import datasource.dto.FilmDto;
import datasource.film.FilmDataSource;
import datasource.film.mapper.FilmDtoMappers;
import repository.entity.Film;
import repository.mapper.FilmEntityMappers;
import repository.query.Query;
import repository.query.add.AddArtistQuery;
import repository.query.add.AddFilmQuery;
import repository.query.update.UpdateArtistQuery;
import repository.query.update.UpdateFilmQuery;

import java.io.IOException;
import java.nio.file.FileSystemException;

public class Repository {

    private FilmDataSource filmDataSource;
    private ArtistDataSource artistDataSource;

    public Repository(FilmDataSource filmDataSource, ArtistDataSource artistDataSource) {
        this.filmDataSource = filmDataSource;
        this.artistDataSource = artistDataSource;
    }

    public void initialize() {
        filmDataSource.initialize();
        artistDataSource.initialize();
    }

    public void clear() {
        filmDataSource.clearData();
        artistDataSource.clearData();
    }

    public void addFilm(AddFilmQuery query) {
        try {
            filmDataSource.addFilm(query.getFilmToAdd());
            System.out.println(String.format(">> Repository: Added film %s successfully", query.getFilmToAdd().getName()));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addArtist(AddArtistQuery query) {
        try {
            artistDataSource.addArtist(query.getArtistToAdd());
            System.out.println(String.format(">> Repository: Added artist %s successfully", query.getArtistToAdd().getName()));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Film findFilm(int filmId) {
        try {
            FilmDto filmDto = filmDataSource.findFilmById(filmId);
            System.out.println(String.format(">> Repository: Found film named %s with id %d", filmDto.getName(), filmDto.getId()));
            return FilmEntityMappers.mapFilmDtoToFilmEntity(filmDto);
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Film findFilm(String filmName) {
        try {
            FilmDto filmDto = filmDataSource.findFilmByName(filmName);
            System.out.println(String.format(">> Repository: Found film named %s with id %d", filmDto.getName(), filmDto.getId()));
            return FilmEntityMappers.mapFilmDtoToFilmEntity(filmDto);
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeArtist(int artistId) {
        try {
            artistDataSource.deleteArtist(artistId);
            System.out.println(String.format(">> Repository: Deleted artist with id %d successfully", artistId));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeFilm(int filmId) {
        try {
            filmDataSource.deleteFilm(filmId);
            System.out.println(String.format(">> Repository: Deleted film with id %d successfully", filmId));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateFilm(UpdateFilmQuery query) {
        // TODO: Complicated query chains
    }

    public void updateArtist(UpdateArtistQuery query) {
        // TODO: Complicated query chains
    }
}
