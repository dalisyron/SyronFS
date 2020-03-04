package datasource.artist.mapper;

import datasource.dto.ArtistDto;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ArtistMappers {

    public static String mapArtistDtoToRecordFormat(ArtistDto artistDto) {
        return "$%d-" + artistDto.getId() + "/" + artistDto.getName() +
                "/" + artistDto.getAge() + "/" +
                artistDto.getFilmNames();
    }

    public static ArtistDto mapRecordToArtistDto(String record) {
        int dashIndex = record.indexOf('-');
        String trimSubstring = record.substring(0, dashIndex + 1);

        String trimmedRecord = record.replaceFirst(Pattern.quote(trimSubstring), "").trim();

        String[] items = trimmedRecord.split("/");

        int id = Integer.parseInt(items[0]);
        String name = items[1];
        int age = Integer.parseInt(items[2]);
        List<String> filmNames = Arrays.asList(items[3].split(Pattern.quote(",")));

        return new ArtistDto(id, name, age, filmNames);
    }
}
