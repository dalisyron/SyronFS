package datasource;

import datasource.mapper.FilmMappers;
import entity.Film;
import io.FileHandler;

public class FilmDataSource {

    private FileHandler fileHandler;

    public FilmDataSource(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void addFilm(Film film) {
        boolean op = fileHandler.appendLine(FilmMappers.mapFilmToRecordFormat(film));
        if (op) {
            System.out.println("Successfully added the movie.");
        } else {
            System.out.println("Error while adding the movie.");
        }
    }

    public void updateFilmByName(String name, Film updatedFilm) {
        fileHandler.updateLine(name, FilmMappers.mapFilmToRecordFormat(updatedFilm), (record, key) -> {
            Film film = FilmMappers.mapRecordToFilm(record);
            return film.getName().equals(key);
        });
    }

    public void updateFilmById(String id, Film updatedFilm) {
        fileHandler.updateLine(id, FilmMappers.mapFilmToRecordFormat(updatedFilm), (record, key) -> {
            Film film = FilmMappers.mapRecordToFilm(record);
            return film.getId() == Integer.parseInt(key);
        });
    }

    public void deleteFilm(String id) {
        fileHandler.deleteLine(id, (record, key) -> FilmMappers.mapRecordToFilm(record).getId() == Integer.parseInt(key));
    }
}
