package datasource.mapper;

import com.sun.tools.javac.util.Convert;
import entity.Film;

import java.util.List;

public class FilmMappers {

    public static String mapFilmToRecordFormat(Film film) {
        return "$%d-" + film.getId() + "/" + film.getName() +
                "/" + film.getDirectorName() + "/" + film.getProductionYear() + "/" + film.getGenre();
    }

    public static Film mapRecordToFilm(String record) {
        int dashIndex = record.indexOf('-');
        String trimSubstring = record.substring(0, dashIndex + 1);
        String trimmedRecord = record.replaceFirst(trimSubstring, "").trim();

        String[] items = trimmedRecord.split("/");

        int id = Integer.parseInt(items[0]);
        String name = items[1];
        String directorName = items[2];
        int productionYear = Integer.parseInt(items[3]);
        String genre = items[4];

        return new Film(id, name, directorName, productionYear, genre);
    }
}
