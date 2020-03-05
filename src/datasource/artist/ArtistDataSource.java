package datasource.artist;

import datasource.artist.mapper.ArtistDtoMappers;
import datasource.base.BaseDataSource;
import datasource.dto.ArtistDto;
import io.FileHandler;

import java.io.IOException;
import java.nio.file.FileSystemException;

public class ArtistDataSource extends BaseDataSource {

    public ArtistDataSource(FileHandler fileHandler) {
        super(fileHandler);
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
}
