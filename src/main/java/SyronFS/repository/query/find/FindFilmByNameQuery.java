package SyronFS.repository.query.find;

import SyronFS.repository.query.Query;

public class FindFilmByNameQuery extends Query {

    private final String filmName;

    public FindFilmByNameQuery(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmName() {
        return filmName;
    }
}
