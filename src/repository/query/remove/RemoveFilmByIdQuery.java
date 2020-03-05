package repository.query.remove;

import repository.query.Query;

public class RemoveFilmByIdQuery extends Query {

    private int filmId;

    public RemoveFilmByIdQuery(int filmId) {
        this.filmId = filmId;
    }

    public int getFilmId() {
        return filmId;
    }
}
