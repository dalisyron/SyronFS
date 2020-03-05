package repository.query.remove;

import repository.query.Query;

public class RemoveArtistByIdQuery extends Query {

    private int artistId;

    public RemoveArtistByIdQuery(int artistId) {
        this.artistId = artistId;
    }

    public int getArtistId() {
        return artistId;
    }
}
