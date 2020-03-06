package SyronFS.repository.query.add;

import SyronFS.datasource.dto.FilmDto;
import SyronFS.repository.entity.Film;
import SyronFS.repository.query.Query;

public class AddFilmQuery extends Query {

    private final FilmDto filmToAdd;

    public AddFilmQuery(FilmDto filmToAdd) {
        this.filmToAdd = filmToAdd;
    }

    public FilmDto getFilmToAdd() {
        return filmToAdd;
    }
}
