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

    public void addFilm(FilmDto film) {
        try {
            fileHandler.appendLine(FilmDtoMappers.mapFilmDtoToRecordFormat(film));
            System.out.println(String.format(">> Added film %s successfully", film.getName()));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateFilm(int id, FilmDto updatedFilm) {
        try {
            fileHandler.updateLine("" + id, FilmDtoMappers.mapFilmDtoToRecordFormat(updatedFilm), (record, key) -> {
                FilmDto film = FilmDtoMappers.mapRecordToFilmDto(record);
                return film.getId() == Integer.parseInt(key);
            });
            System.out.println(String.format(">> Updated film %d successfully", id));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findFilmByName(String name) {
        try {
            String result = fileHandler.findLine(name, (record, key) -> {
                FilmDto film = FilmDtoMappers.mapRecordToFilmDto(record);
                return film.getName().equals(key);
            });
            FilmDto film = FilmDtoMappers.mapRecordToFilmDto(result);
            System.out.println(String.format(">> Found film named %s with id %d", name, film.getId()));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findFilmById(int id) {
        try {
            String result = fileHandler.findLine(String.format("%d", id), (record, key) -> {
                FilmDto film = FilmDtoMappers.mapRecordToFilmDto(record);
                return film.getId() == Integer.parseInt(key);
            });
            FilmDto film = FilmDtoMappers.mapRecordToFilmDto(result);
            System.out.println(String.format(">> Found film named %s with id %d", film.getName(), id));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFilm(int id) {
        try {
            fileHandler.deleteLine(String.format("%d", id), (record, key) ->
                    FilmDtoMappers.mapRecordToFilmDto(record).getId() == Integer.parseInt(key)
            );
            System.out.println(String.format(">> Deleted film with id %d successfully", id));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
