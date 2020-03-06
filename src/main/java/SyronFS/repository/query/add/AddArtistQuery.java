package SyronFS.repository.query.add;

import SyronFS.datasource.dto.ArtistDto;
import SyronFS.repository.entity.Artist;
import SyronFS.repository.query.Query;

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
