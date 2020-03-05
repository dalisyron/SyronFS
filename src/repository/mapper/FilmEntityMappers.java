package repository.mapper;

import datasource.dto.FilmDto;
import repository.entity.Film;

public class FilmEntityMappers {

    public FilmEntityMappers() {

    }

    public static Film mapFilmDtoToFilmEntity(FilmDto filmDto) {
        return new Film(filmDto.getId(), filmDto.getName(), filmDto.getDirectorName(),
                filmDto.getProductionYear(), filmDto.getGenre());
    }
}
