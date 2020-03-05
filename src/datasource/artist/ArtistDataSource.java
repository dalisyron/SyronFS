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

    public void updateArtist(int id, ArtistDto updatedArtist) {
        try {
            fileHandler.updateLine("" + id, ArtistDtoMappers.mapArtistDtoToRecordFormat(updatedArtist), (record, key) -> {
                ArtistDto artist = ArtistDtoMappers.mapRecordToArtistDto(record);
                return artist.getId() == Integer.parseInt(key);
            });
            System.out.println(String.format(">> Updated artist %d successfully", id));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findArtistByName(String name) {
        try {
            String result = fileHandler.findLine(name, (record, key) -> {
                ArtistDto artist = ArtistDtoMappers.mapRecordToArtistDto(record);
                return artist.getName().equals(key);
            });
            ArtistDto artist = ArtistDtoMappers.mapRecordToArtistDto(result);
            System.out.println(String.format(">> Found artist named %s with id %d", name, artist.getId()));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findArtistById(int id) {
        try {
            String result = fileHandler.findLine(String.format("%d", id), (record, key) -> {
                ArtistDto artist = ArtistDtoMappers.mapRecordToArtistDto(record);
                return artist.getId() == Integer.parseInt(key);
            });
            ArtistDto artist = ArtistDtoMappers.mapRecordToArtistDto(result);
            System.out.println(String.format(">> Found artist named %s with id %d", artist.getName(), id));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteArtist(int id) throws IOException {
        fileHandler.deleteLine(String.format("%d", id), (record, key) ->
                ArtistDtoMappers.mapRecordToArtistDto(record).getId() == Integer.parseInt(key)
        );

    }
}
