package repository.query.add;

import datasource.dto.ArtistDto;
import repository.entity.Artist;
import repository.query.Query;

public class AddArtistQuery extends Query {

    private final ArtistDto artistToAdd;

    public AddArtistQuery(ArtistDto artistToAdd) {
        this.artistToAdd = artistToAdd;
    }

    public ArtistDto getArtistToAdd() {
        return artistToAdd;
    }
}
