package datasource.dto;

import java.util.List;

public class ArtistDto {

    private final int id;
    private final String name;
    private final int age;
    private final List<String> filmNames;

    public ArtistDto(int id, String name, int age, List<String> filmNames) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.filmNames = filmNames;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<String> getFilmNames() {
        return filmNames;
    }
}