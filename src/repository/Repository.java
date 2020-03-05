package repository;

import datasource.artist.ArtistDataSource;
import datasource.artist.mapper.ArtistDtoMappers;
import datasource.dto.ArtistDto;
import datasource.dto.FilmDto;
import datasource.film.FilmDataSource;
import datasource.film.mapper.FilmDtoMappers;
import datasource.index.IndexDataSource;
import kotlin.Pair;
import repository.entity.Artist;
import repository.entity.Film;
import repository.entity.FilmCasting;
import repository.mapper.FilmEntityMappers;
import repository.query.add.AddArtistQuery;
import repository.query.add.AddFilmQuery;
import repository.query.update.UpdateArtistQuery;
import repository.query.update.UpdateFilmQuery;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.*;
import java.util.regex.Pattern;

public class Repository {

    private FilmDataSource filmDataSource;
    private ArtistDataSource artistDataSource;
    private IndexDataSource indexDataSource;

    public Repository(FilmDataSource filmDataSource, ArtistDataSource artistDataSource, IndexDataSource indexDataSource) {
        this.filmDataSource = filmDataSource;
        this.artistDataSource = artistDataSource;
        this.indexDataSource = indexDataSource;
    }

    public void initialize() {
        filmDataSource.initialize();
        artistDataSource.initialize();
    }

    public void clear() {
        filmDataSource.clearData();
        artistDataSource.clearData();
        indexDataSource.clearData();
    }

    public void addFilm(AddFilmQuery query) {
        if (!isValid(query.getFilmToAdd())) {
            throw new IllegalFieldsException();
        }
        try {
            FilmDto filmDto = null;
            try {
                filmDto = filmDataSource.findFilmById(query.getFilmToAdd().getId());
            } catch (NoSuchElementException e) {
                //Pass
            }
            if (filmDto != null) {
                throw new DuplicateElementIdException();
            }
            filmDataSource.addFilm(query.getFilmToAdd());
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addArtist(AddArtistQuery query) {
        if (!isValid(query.getArtistToAdd())) {
            throw new IllegalFieldsException();
        }
        try {
            ArtistDto artistDto = null;
            try {
                artistDto = artistDataSource.findArtistById(query.getArtistToAdd().getId());
            } catch (NoSuchElementException e) {
                //Pass
            }
            if (artistDto != null) {
                throw new DuplicateElementIdException();
            }
            artistDataSource.addArtist(query.getArtistToAdd());
            System.out.println(String.format(">> Repository: Added artist %s successfully", query.getArtistToAdd().getName()));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Film findFilm(int filmId) {
        try {
            FilmDto filmDto = filmDataSource.findFilmById(filmId);
            return FilmEntityMappers.mapFilmDtoToFilmEntity(filmDto);
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Film findFilm(String filmName) {
        try {
            FilmDto filmDto = filmDataSource.findFilmByName(filmName);
            return FilmEntityMappers.mapFilmDtoToFilmEntity(filmDto);
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeArtist(int artistId) {
        try {
            artistDataSource.deleteArtist(artistId);
            System.out.println(String.format(">> Repository: Deleted artist with id %d successfully", artistId));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeFilm(int filmId) {
        try {
            filmDataSource.deleteFilm(filmId);
            System.out.println(String.format(">> Repository: Deleted film with id %d successfully", filmId));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateFilm(UpdateFilmQuery query) {
        try {
            FilmDto oldFilm = filmDataSource.findFilmByName(query.getName());
            FilmDto newFilm = null;
            switch (query.getFieldToUpdate()) {
                case "FilmID":
                    newFilm = FilmDtoMappers.updateId(oldFilm, Integer.parseInt(query.getFieldValue()));
                    break;
                case "FilmName":
                    newFilm = FilmDtoMappers.updateName(oldFilm, query.getFieldValue());
                    break;
                case "DirectorName":
                    newFilm = FilmDtoMappers.updateDirector(oldFilm, query.getFieldValue());
                    break;
                case "ProductionYear":
                    newFilm = FilmDtoMappers.updateProductionYear(oldFilm, Integer.parseInt(query.getFieldValue()));
                    break;
                case "Genre":
                    newFilm = FilmDtoMappers.updateGenre(oldFilm, query.getFieldValue());
                    break;
                default:
                    throw new IllegalUpdateFieldException();
            }

            if (!isValid(newFilm)) {
                throw new IllegalFieldsException();
            }

            filmDataSource.updateFilm(query.getName(), newFilm);
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateArtist(UpdateArtistQuery query) {
        try {
            ArtistDto oldArtist = artistDataSource.findArtistByName(query.getName());
            ArtistDto newArtist = null;
            switch (query.getFieldToUpdate()) {
                case "ArtistID":
                    newArtist = ArtistDtoMappers.updateId(oldArtist, Integer.parseInt(query.getFieldValue()));
                    break;
                case "ArtistName":
                    newArtist = ArtistDtoMappers.updateName(oldArtist, query.getFieldValue());
                    break;
                case "Age":
                    newArtist = ArtistDtoMappers.updateAge(oldArtist, Integer.parseInt(query.getFieldValue()));
                    break;
                case "ArtistFilms":
                    List<String> filmNames = Arrays.asList(query.getFieldValue().split(Pattern.quote(",")));
                    newArtist = ArtistDtoMappers.updateFilmNames(oldArtist, filmNames);
                    break;
                default:
                    throw new IllegalUpdateFieldException();
            }

            if (!isValid(newArtist)) {
                throw new IllegalFieldsException();
            }

            artistDataSource.updateArtist(query.getName(), newArtist);
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Artist findArtistEntityById(int id) {
        try {
            ArtistDto artistDto = artistDataSource.findArtistById(id);

            List<String> filmNames = artistDto.getFilmNames();
            ArrayList<Film> films = new ArrayList<>();

            for (String name : filmNames) {
                FilmDto filmDto = filmDataSource.findFilmByName(name);
                films.add(FilmEntityMappers.mapFilmDtoToFilmEntity(filmDto));
            }
            return new Artist(artistDto.getId(), artistDto.getName(), artistDto.getAge(), films);

        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArtistDto findArtistById(int id) {
        try {
            ArtistDto artistDto = artistDataSource.findArtistById(id);
            return artistDto;
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addArtistWithValidation(AddArtistQuery query) {
        if (!isValid(query.getArtistToAdd())) {
            throw new IllegalFieldsException();
        }
        try {
            ArtistDto artistDto = null;
            try {
                artistDto = artistDataSource.findArtistById(query.getArtistToAdd().getId());
            } catch (NoSuchElementException e) {
                //Pass
            }
            if (artistDto != null) {
                throw new DuplicateElementIdException();
            }
            ArtistDto artistToAdd = query.getArtistToAdd();
            for (String filmName : artistToAdd.getFilmNames()) {
                try {
                    FilmDto nextFilm = filmDataSource.findFilmByName(filmName);
                } catch (NoSuchElementException e) {
                    throw new NonExistingFilmException();
                }
            }
            artistDataSource.addArtist(query.getArtistToAdd());
            System.out.println(String.format(">> Repository: Added artist %s successfully", query.getArtistToAdd().getName()));
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<FilmCasting> getAllFilmCastings() {
        try {
            List<FilmDto> films = filmDataSource.getAllFilms();
            List<ArtistDto> artists = artistDataSource.getAllArtists();
            ArrayList<FilmCasting> result = new ArrayList<>();
            HashMap<String, FilmDto> filmDtoMap = new HashMap<>();

            for (FilmDto film : films) {
                filmDtoMap.put(film.getName(), film);
            }

            HashMap<FilmDto, List<ArtistDto>> cast = new HashMap<>();

            for (ArtistDto artist : artists) {
                for (String film: artist.getFilmNames()) {
                    if (filmDtoMap.containsKey(film)) {
                        if (!cast.containsKey(filmDtoMap.get(film))) {
                            cast.put(filmDtoMap.get(film), new ArrayList<>(Arrays.asList(artist)));
                        } else {
                            cast.get(filmDtoMap.get(film)).add(artist);
                        }
                    }
                }
            }

            Iterator hmIterator = cast.entrySet().iterator();

            while (hmIterator.hasNext()) {
                Map.Entry<FilmDto, List<ArtistDto>> mapElement = (Map.Entry) hmIterator.next();
                result.add(new FilmCasting(FilmEntityMappers.mapFilmDtoToFilmEntity(mapElement.getKey()), mapElement.getValue()));
            }
            return result;
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void buildArtistNameIndex() {
        try {
            List<FilmDto> films = filmDataSource.getAllFilms();
            List<ArtistDto> artists = artistDataSource.getAllArtists();
            artists.sort(Comparator.comparing(ArtistDto::getName));

            HashMap<String, Integer> filmId = new HashMap<String, Integer>();
            for (FilmDto film : films) {
                filmId.put(film.getName(), film.getId());
            }

            List<Pair<String, List<Integer>>> artistFilmPairs = new ArrayList<>();
            for (ArtistDto artist : artists) {
                ArrayList<Integer> artistFilms = new ArrayList<>();
                for (String filmName : artist.getFilmNames()) {
                    if (filmId.containsKey(filmName)) {
                        artistFilms.add(filmId.get(filmName));
                    }
                }
                artistFilmPairs.add(new Pair<>(artist.getName(), artistFilms));
            }
            indexDataSource.buildArtistNameIndex(artistFilmPairs);
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildArtistIdIndex() {
        try {
            List<FilmDto> films = filmDataSource.getAllFilms();
            List<ArtistDto> artists = artistDataSource.getAllArtists();
            artists.sort(Comparator.comparingInt(ArtistDto::getId));

            HashMap<String, Integer> filmId = new HashMap<String, Integer>();
            for (FilmDto film : films) {
                filmId.put(film.getName(), film.getId());
            }

            List<Pair<Integer, List<Integer>>> artistFilmPairs = new ArrayList<>();
            for (ArtistDto artist : artists) {
                ArrayList<Integer> artistFilms = new ArrayList<>();
                for (String filmName : artist.getFilmNames()) {
                    if (filmId.containsKey(filmName)) {
                        artistFilms.add(filmId.get(filmName));
                    }
                }
                artistFilmPairs.add(new Pair<>(artist.getId(), artistFilms));
            }
            indexDataSource.buildArtistIdIndex(artistFilmPairs);
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValid(ArtistDto artistToAdd) {
        boolean idCheck = artistToAdd.getId() > 999 && artistToAdd.getId() < 10000;
        boolean artistNameCheck = artistToAdd.getName().length() <= 100;
        boolean artistAgeCheck = artistToAdd.getAge() < 1000;
        return idCheck && artistNameCheck && artistAgeCheck;
    }

    private static boolean isValid(FilmDto filmToAdd) {
        boolean idCheck = filmToAdd.getId() > 999 && filmToAdd.getId() < 10000;
        boolean filmNameCheck = filmToAdd.getName().length() <= 100;
        boolean productionYearCheck = filmToAdd.getProductionYear() > 999 && filmToAdd.getProductionYear() < 10000;
        boolean genreCheck = filmToAdd.getGenre().length() <= 20;
        return idCheck && filmNameCheck && productionYearCheck && genreCheck;
    }
}
