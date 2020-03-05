package datasource.artist;

import datasource.artist.mapper.ArtistDtoMappers;
import datasource.base.BaseDataSource;
import datasource.dto.ArtistDto;
import datasource.dto.FilmDto;
import datasource.film.mapper.FilmDtoMappers;
import io.FileHandler;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.List;
import java.util.stream.Collectors;

public class ArtistDataSource extends BaseDataSource {

    private FileHandler fileHandler;

    public ArtistDataSource(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void addArtist(ArtistDto artist) throws IOException {
        fileHandler.appendLine(ArtistDtoMappers.mapArtistDtoToRecordFormat(artist));
    }

    public void updateArtist(String name, ArtistDto updatedArtist) {
        try {
            fileHandler.updateLine(name, ArtistDtoMappers.mapArtistDtoToRecordFormat(updatedArtist), (record, key) -> {
                ArtistDto artist = ArtistDtoMappers.mapRecordToArtistDto(record);
                return artist.getName().equals(key);
            });
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArtistDto findArtistByName(String name) throws IOException {
        String result = fileHandler.findLine(name, (record, key) -> {
            ArtistDto artist = ArtistDtoMappers.mapRecordToArtistDto(record);
            return artist.getName().equals(name);
        });
        return ArtistDtoMappers.mapRecordToArtistDto(result);
    }

    public ArtistDto findArtistById(int id) throws IOException {
        String result = fileHandler.findLine(String.format("%d", id), (record, key) -> {
            ArtistDto artist = ArtistDtoMappers.mapRecordToArtistDto(record);
            return artist.getId() == Integer.parseInt(key);
        });
        return ArtistDtoMappers.mapRecordToArtistDto(result);
    }

    public void deleteArtist(int id) throws IOException {
        fileHandler.deleteLine(String.format("%d", id), (record, key) ->
                ArtistDtoMappers.mapRecordToArtistDto(record).getId() == Integer.parseInt(key)
        );

    }

    public List<ArtistDto> getAllArtists() throws IOException {
        return fileHandler.getAllRecords()
                .stream()
                .map(ArtistDtoMappers::mapRecordToArtistDto)
                .collect(Collectors.toList());
    }

    @Override
    public void initialize() {
        try {
            fileHandler.initialize();
        } catch (FileSystemException e) {
            System.err.println(">> Error while initializing new file source.");
        }
    }

    @Override
    public void clearData() {
        try {
            fileHandler.clearFile();
            System.out.println(String.format(">> Successfully cleared all data in %s", fileHandler.getFile().getName()));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
