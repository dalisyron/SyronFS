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
}
