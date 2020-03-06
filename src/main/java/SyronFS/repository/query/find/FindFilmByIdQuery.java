package SyronFS.repository.query.find;

import SyronFS.repository.query.Query;

public class FindFilmByIdQuery extends Query {

    private final int filmId;

    public FindFilmByIdQuery(int filmId) {
        this.filmId = filmId;
    }

    public int getFilmId() {
        return filmId;
    }
}
