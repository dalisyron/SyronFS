package repository.query.add;

import datasource.dto.FilmDto;
import repository.entity.Film;
import repository.query.Query;

public class AddFilmQuery extends Query {

    private final FilmDto filmToAdd;

    public AddFilmQuery(FilmDto filmToAdd) {
        this.filmToAdd = filmToAdd;
    }

    public FilmDto getFilmToAdd() {
        return filmToAdd;
    }
}
