package datasource;

import datasource.mapper.FilmMappers;
import entity.Film;
import io.FileHandler;

import java.io.IOException;
import java.nio.file.FileSystemException;

public class FilmDataSource {

    private FileHandler fileHandler;

    public FilmDataSource(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void addFilm(Film film) {
        try {
            fileHandler.appendLine(FilmMappers.mapFilmToRecordFormat(film));
            System.out.println(String.format(">> Added film %s successfully", film.getName()));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateFilm(int id, Film updatedFilm) {
        try {
            fileHandler.updateLine("" + id, FilmMappers.mapFilmToRecordFormat(updatedFilm), (record, key) -> {
                Film film = FilmMappers.mapRecordToFilm(record);
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
                Film film = FilmMappers.mapRecordToFilm(record);
                return film.getName().equals(key);
            });
            Film film = FilmMappers.mapRecordToFilm(result);
            System.out.println(String.format(">> Found movie named %s with id %d", name, film.getId()));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findFilmById(int id) {
        try {
            String result = fileHandler.findLine(String.format("%d", id), (record, key) -> {
                Film film = FilmMappers.mapRecordToFilm(record);
                return film.getId() == Integer.parseInt(key);
            });
            Film film = FilmMappers.mapRecordToFilm(result);
            System.out.println(String.format(">> Found movie named %s with id %d", film.getName(), id));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFilm(int id) {
        try {
            fileHandler.deleteLine(String.format("%d", id), (record, key) ->
                    FilmMappers.mapRecordToFilm(record).getId() == Integer.parseInt(key)
            );
            System.out.println(String.format(">> Deleted movie with id %d successfully", id));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearData() {
        try {
            fileHandler.clearFile();
            System.out.println(">> Successfully cleared all data");
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
