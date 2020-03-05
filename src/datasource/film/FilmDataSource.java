package datasource.film;

import datasource.base.BaseDataSource;
import datasource.film.mapper.FilmDtoMappers;
import datasource.dto.FilmDto;
import io.FileHandler;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.List;
import java.util.stream.Collectors;

public class FilmDataSource extends BaseDataSource {

    private FileHandler fileHandler;

    public FilmDataSource(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
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

    public List<FilmDto> getAllFilms() throws IOException {
        return fileHandler.getAllRecords()
                .stream()
                .map(FilmDtoMappers::mapRecordToFilmDto)
                .collect(Collectors.toList());
    }

    @Override
    public void initialize() {
        try {
            fileHandler.initialize();
        } catch (FileSystemException e) {
            System.err.println(">> Error while initializing new file source for films");
        }
    }

    @Override
    public void clearData() {
        try {
            fileHandler.clearFile();
            System.out.println(String.format(">> Successfully cleared all data in %s", fileHandler.getFile().getName()));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
