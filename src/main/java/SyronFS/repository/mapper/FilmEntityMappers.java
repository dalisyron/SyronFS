package SyronFS.repository.mapper;

import SyronFS.datasource.dto.FilmDto;
import SyronFS.repository.entity.Film;

public class FilmEntityMappers {

    public FilmEntityMappers() {

    }

    public static Film mapFilmDtoToFilmEntity(FilmDto filmDto) {
        return new Film(filmDto.getId(), filmDto.getName(), filmDto.getDirectorName(),
                filmDto.getProductionYear(), filmDto.getGenre());
    }
}
