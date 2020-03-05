package datasource.film;

import datasource.base.BaseDataSource;
import datasource.film.mapper.FilmDtoMappers;
import datasource.dto.FilmDto;
import io.FileHandler;

import java.io.IOException;
import java.nio.file.FileSystemException;

public class FilmDataSource extends BaseDataSource {

    public FilmDataSource(FileHandler fileHandler) {
        super(fileHandler);
    }

    public void addFilm(FilmDto film) throws IOException {
        fileHandler.appendLine(FilmDtoMappers.mapFilmDtoToRecordFormat(film));

    }

    public void updateFilm(String name, FilmDto updatedFilm) throws IOException {
        fileHandler.updateLine(name, FilmDtoMappers.mapFilmDtoToRecordFormat(updatedFilm), (record, key) -> {
            FilmDto film = FilmDtoMappers.mapRecordToFilmDto(record);
            return film.getName().equals(key);
        });
    }

    public FilmDto findFilmByName(String name) throws IOException {
        String result = fileHandler.findLine(name, (record, key) -> {
            FilmDto film = FilmDtoMappers.mapRecordToFilmDto(record);
            return film.getName().equals(key);
        });
        FilmDto film = FilmDtoMappers.mapRecordToFilmDto(result);
        return film;
    }

    public FilmDto findFilmById(int id) throws IOException {
        String result = fileHandler.findLine(String.format("%d", id), (record, key) -> {
            FilmDto film = FilmDtoMappers.mapRecordToFilmDto(record);
            return film.getId() == Integer.parseInt(key);
        });
        return FilmDtoMappers.mapRecordToFilmDto(result);
    }

    public void deleteFilm(int id) throws IOException {
        fileHandler.deleteLine(String.format("%d", id), (record, key) ->
                FilmDtoMappers.mapRecordToFilmDto(record).getId() == Integer.parseInt(key)
        );
    }
}
