package repository.query;

import datasource.dto.ArtistDto;
import datasource.dto.FilmDto;
import repository.Repository;
import repository.entity.Film;
import repository.query.add.AddArtistQuery;
import repository.query.add.AddFilmQuery;
import repository.query.find.FindFilmByIdQuery;
import repository.query.find.FindFilmByNameQuery;
import repository.query.remove.RemoveArtistByIdQuery;
import repository.query.remove.RemoveFilmByIdQuery;
import repository.query.update.UpdateArtistQuery;
import repository.query.update.UpdateFilmQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public abstract class Query {

    public static final int TYPE_ADD_FILM = 1;
    public static final int TYPE_ADD_ARTIST = 2;
    public static final int TYPE_FIND_FILM_BY_ID = 3;
    public static final int TYPE_FIND_FILM_BY_NAME = 4;
    public static final int TYPE_REMOVE_FILM_BY_ID = 5;
    public static final int TYPE_REMOVE_ARTIST_BY_ID = 6;
    public static final int TYPE_UPDATE_ARTIST = 7;
    public static final int TYPE_UPDATE_FILM = 8;

    public static Query parseQuery(String queryString) {
        int queryType = Query.getQueryType(queryString);

        switch (queryType) {
            case TYPE_ADD_FILM:
                return parseAddFilmQuery(queryString);
            case TYPE_ADD_ARTIST:
                return parseAddArtistQuery(queryString);
            case TYPE_FIND_FILM_BY_ID:
                return parseFindFilmByIdQuery(queryString);
            case TYPE_FIND_FILM_BY_NAME:
                return parseFindFilmByNameQuery(queryString);
            case TYPE_REMOVE_FILM_BY_ID:
                return parseRemoveFilmByIdQuery(queryString);
            case TYPE_REMOVE_ARTIST_BY_ID:
                return parseRemoveArtistByIdQuery(queryString);
            case TYPE_UPDATE_ARTIST:
                return parseUpdateArtistQuery(queryString);
            case TYPE_UPDATE_FILM:
                return parseUpdateFilmQuery(queryString);
            default:
                throw new IllegalStateException("Unexpected value: " + queryType);
        }
    }

    private static UpdateFilmQuery parseUpdateFilmQuery(String queryString) {
        String[] wordList = queryString.split(Pattern.quote(" "));

        int indexOfFirst = 2;
        int indexOfLast = wordList.length - 5;

        String name = String.join(" ", Arrays.copyOfRange(wordList, indexOfFirst, indexOfLast + 1));
        String fieldToUpdate = wordList[wordList.length - 3].trim();
        String updatedValue = wordList[wordList.length - 1].trim();

        return new UpdateFilmQuery(name, fieldToUpdate, updatedValue);
    }

    private static UpdateArtistQuery parseUpdateArtistQuery(String queryString) {
        String[] wordList = queryString.split(Pattern.quote(" "));

        int indexOfFirst = 2;
        int indexOfLast = wordList.length - 5;

        String name = String.join(" ", Arrays.copyOfRange(wordList, indexOfFirst, indexOfLast + 1));
        String fieldToUpdate = wordList[wordList.length - 3].trim();
        String updatedValue = wordList[wordList.length - 1].trim();

        return new UpdateArtistQuery(name, fieldToUpdate, updatedValue);
    }

    private static RemoveFilmByIdQuery parseRemoveFilmByIdQuery(String queryString) {
        String[] wordList = queryString.split(Pattern.quote(" "));

        int id = Integer.parseInt(wordList[wordList.length - 1]);

        return new RemoveFilmByIdQuery(id);
    }

    private static RemoveArtistByIdQuery parseRemoveArtistByIdQuery(String queryString) {
        String[] wordList = queryString.split(Pattern.quote(" "));

        int id = Integer.parseInt(wordList[wordList.length - 1]);

        return new RemoveArtistByIdQuery(id);
    }

    private static FindFilmByNameQuery parseFindFilmByNameQuery(String queryString) {
        String[] wordList = queryString.split(Pattern.quote(" "));

        int indexOfFirst = 2;
        int indexOfLast = wordList.length - 3;

        String name = String.join(" ", Arrays.copyOfRange(wordList, indexOfFirst, indexOfLast + 1));

        return new FindFilmByNameQuery(name);
    }

    private static FindFilmByIdQuery parseFindFilmByIdQuery(String queryString) {
        String[] wordList = queryString.split(Pattern.quote(" "));

        int id = Integer.parseInt(wordList[wordList.length - 3].trim());

        return new FindFilmByIdQuery(id);
    }

    private static AddArtistQuery parseAddArtistQuery(String queryString) {
        String[] wordList = queryString.split(Pattern.quote(" "));

        String update = queryString.replaceFirst(Pattern.quote("Add "), "");

        String[] fields = update.split(Pattern.quote(","));

        Object[] values2 = Arrays.stream(fields).map(it -> {
            String[] valueSt = it.split(Pattern.quote(":"));
            if (valueSt.length > 1) {
                return valueSt[1].trim();
            } else {
                return valueSt[0].trim();
            }
        }).toArray();

        String[] values = Arrays.copyOf(values2, values2.length, String[].class);

        int id = Integer.parseInt(values[0]);
        String name = values[1];
        int age = Integer.parseInt(values[2]);

        List<String> films = new ArrayList<>();

        for (int i = 3; i < values.length; i++) {
            films.add(values[i]);
        }

        return new AddArtistQuery(new ArtistDto(id, name, age, films));
    }

    private static AddFilmQuery parseAddFilmQuery(String queryString) {
        String[] wordList = queryString.split(Pattern.quote(" "));

        String update = queryString.replaceFirst(Pattern.quote("Add "), "");

        String[] fields = update.split(Pattern.quote(","));

        Object[] values2 = Arrays.stream(fields).map(it -> it.split(Pattern.quote(":"))[1].trim()).toArray();

        String[] values = Arrays.copyOf(values2, values2.length, String[].class);

        int id = Integer.parseInt(values[0]);
        String name = values[1];
        String director = values[2];
        int productionYear = Integer.parseInt(values[3]);
        String genre = values[4];

        return new AddFilmQuery(new FilmDto(id, name, director, productionYear, genre));
    }

    private static int getQueryType(String queryString) {
        String firstWord = StringUtil.getFirstWord(queryString);
        String[] wordList = queryString.split(Pattern.quote(" "));

        switch (firstWord) {
            case "Add":
                if (wordList[1].startsWith("Film")) {
                    return TYPE_ADD_FILM;
                } else if (wordList[1].startsWith("Artist")) {
                    return TYPE_ADD_ARTIST;
                } else {
                    throw new IllegalArgumentException();
                }
            case "Find":
                if (wordList[1].equals("Film")) {
                    if (wordList[wordList.length - 1].equals("FilmID")) {
                        return TYPE_FIND_FILM_BY_ID;
                    } else if (wordList[wordList.length - 1].equals("FilmName")) {
                        return TYPE_FIND_FILM_BY_NAME;
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            case "Remove":
                if (wordList[1].equals("ArtistID")) {
                    return TYPE_REMOVE_ARTIST_BY_ID;
                } else if (wordList[1].equals("FilmID")) {
                    return TYPE_REMOVE_FILM_BY_ID;
                } else {
                    throw new IllegalArgumentException();
                }
            case "Update":
                if (wordList[1].equals("Artist")) {
                    return TYPE_UPDATE_ARTIST;
                } else if (wordList[1].equals("Film")) {
                    return TYPE_UPDATE_FILM;
                } else {
                    throw new IllegalArgumentException();
                }
            default:
                throw new IllegalArgumentException();
        }
    }
}
