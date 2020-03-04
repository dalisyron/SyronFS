package datasource.artist;

import datasource.base.BaseDataSource;
import io.FileHandler;

public class ArtistDataSource extends BaseDataSource {

    private FileHandler fileHandler;

    public ArtistDataSource(FileHandler fileHandler) {
        super(fileHandler);
    }
}
