package repository.query.add;

import datasource.dto.ArtistDto;
import repository.entity.Artist;
import repository.query.Query;

public class AddArtistQuery extends Query {

    private final ArtistDto artistToAdd;
    private boolean checkFilmsExist;

    public AddArtistQuery(ArtistDto artistToAdd, boolean checkFilmsExist) {
        this.artistToAdd = artistToAdd;
        this.checkFilmsExist = checkFilmsExist;
    }

    public ArtistDto getArtistToAdd() {
        return artistToAdd;
    }

    public boolean shouldCheckFilmsExist() {
        return checkFilmsExist;
    }
}
