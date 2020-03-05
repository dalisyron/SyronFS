package datasource.index;

import datasource.base.BaseDataSource;
import io.FileHandler;
import kotlin.Pair;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.List;
import java.util.stream.Collectors;

public class IndexDataSource extends BaseDataSource {

    private final FileHandler artistIdFileHandler;
    private final FileHandler artistNameFileHandler;

    public IndexDataSource(FileHandler artistIdFileHandler, FileHandler artistNameFileHandler) {

        this.artistIdFileHandler = artistIdFileHandler;
        this.artistNameFileHandler = artistNameFileHandler;
    }

    public void buildArtistIdIndex(List<Pair<Integer, List<Integer>>> artistFilmPairs) throws IOException {

        List<String> lines = artistFilmPairs.stream().map(IndexMappers::mapArtistFilmPairToRecord).collect(Collectors.toList());

        artistIdFileHandler.writeLines(lines);
    }

    public void buildArtistNameIndex(List<Pair<String, List<Integer>>> artistFilmPairs) throws IOException {
        List<String> lines = artistFilmPairs.stream().map(IndexMappers::mapArtistFilmNamePairToRecord).collect(Collectors.toList());

        artistNameFileHandler.writeLines(lines);
    }

    @Override
    public void initialize() {
        try {
            artistIdFileHandler.initialize();
            artistNameFileHandler.initialize();
        } catch (FileSystemException e) {
            System.err.println(">> Error while initializing new file source.");
        }
    }

    @Override
    public void clearData() {
        try {
            artistIdFileHandler.clearFile();
            artistNameFileHandler.clearFile();
            System.out.println(String.format(">> Successfully cleared all data in %s", artistIdFileHandler.getFile().getName()));
            System.out.println(String.format(">> Successfully cleared all data in %s", artistNameFileHandler.getFile().getName()));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
