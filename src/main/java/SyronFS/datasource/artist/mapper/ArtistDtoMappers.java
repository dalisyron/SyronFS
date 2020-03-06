package SyronFS.datasource.artist.mapper;

import SyronFS.datasource.dto.ArtistDto;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ArtistDtoMappers {

    public static String mapArtistDtoToRecordFormat(ArtistDto artistDto) {
        return "$%d-" + artistDto.getId() + "/" + artistDto.getName() +
                "/" + artistDto.getAge() + "/" +
                String.join(",", artistDto.getFilmNames());
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

    public static ArtistDto updateId(ArtistDto old, int id) {
        return new ArtistDto(id, old.getName(), old.getAge(), old.getFilmNames());
    }

    public static ArtistDto updateName(ArtistDto old, String name) {
        return new ArtistDto(old.getId(), name, old.getAge(), old.getFilmNames());
    }

    public static ArtistDto updateAge(ArtistDto old, int age) {
        return new ArtistDto(old.getId(), old.getName(), age, old.getFilmNames());
    }

    public static ArtistDto updateFilmNames(ArtistDto old, List<String> filmNames) {
        return new ArtistDto(old.getId(), old.getName(), old.getAge(), filmNames);
    }
}
