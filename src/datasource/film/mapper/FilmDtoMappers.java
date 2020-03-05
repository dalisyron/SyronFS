package datasource.film.mapper;

import datasource.dto.FilmDto;

import java.util.regex.Pattern;

public class FilmDtoMappers {

    public static String mapFilmDtoToRecordFormat(FilmDto film) {
        return "$%d-" + film.getId() + "/" + film.getName() +
                "/" + film.getDirectorName() + "/" + film.getProductionYear() + "/" + film.getGenre();
    }

    public static FilmDto mapRecordToFilmDto(String record) {
        int dashIndex = record.indexOf('-');
        String trimSubstring = record.substring(0, dashIndex + 1);

        String trimmedRecord = record.replaceFirst(Pattern.quote(trimSubstring), "").trim();

        String[] items = trimmedRecord.split("/");

        int id = Integer.parseInt(items[0]);
        String name = items[1];
        String directorName = items[2];
        int productionYear = Integer.parseInt(items[3]);
        String genre = items[4];

        return new FilmDto(id, name, directorName, productionYear, genre);
    }

    public static FilmDto updateId(FilmDto old, int id) {
        return new FilmDto(id, old.getName(), old.getDirectorName(), old.getProductionYear(), old.getGenre());
    }

    public static FilmDto updateName(FilmDto old, String name) {
        return new FilmDto(old.getId(), name, old.getDirectorName(), old.getProductionYear(), old.getGenre());
    }

    public static FilmDto updateDirector(FilmDto old, String directorName) {
        return new FilmDto(old.getId(), old.getName(), directorName, old.getProductionYear(), old.getGenre());
    }

    public static FilmDto updateProductionYear(FilmDto old, int productionYear) {
        return new FilmDto(old.getId(), old.getName(), old.getDirectorName(), productionYear, old.getGenre());
    }

    public static FilmDto updateGenre(FilmDto old, String genre) {
        return new FilmDto(old.getId(), old.getName(), old.getDirectorName(), old.getProductionYear(), genre);
    }
}
