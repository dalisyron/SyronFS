package SyronFS.datasource.dto;

import java.util.Objects;

public class FilmDto {

    private final int id;
    private final String name;
    private final String directorName;
    private final int productionYear;
    private final String genre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmDto filmDto = (FilmDto) o;
        return id == filmDto.id &&
                productionYear == filmDto.productionYear &&
                name.equals(filmDto.name) &&
                directorName.equals(filmDto.directorName) &&
                genre.equals(filmDto.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public FilmDto(int id, String name, String directorName, int productionYear, String genre) {

        this.id = id;
        this.name = name;
        this.directorName = directorName;
        this.productionYear = productionYear;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDirectorName() {
        return directorName;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public String getGenre() {
        return genre;
    }
}