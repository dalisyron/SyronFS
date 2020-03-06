import datasource.dto.ArtistDto;
import di.AppConfig;
import di.Injector;
import repository.*;
import repository.entity.Film;
import repository.entity.FilmCasting;
import repository.query.Query;
import repository.query.add.AddArtistQuery;
import repository.query.add.AddFilmQuery;
import repository.query.find.FindFilmByIdQuery;
import repository.query.find.FindFilmByNameQuery;
import repository.query.remove.RemoveArtistByIdQuery;
import repository.query.remove.RemoveFilmByIdQuery;
import repository.query.update.UpdateArtistQuery;
import repository.query.update.UpdateFilmQuery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class SyronFS {

    public static void main(String[] args) throws IOException {
        handleQueries();
    }

    private static void handleQueries() throws IOException {

        Repository repository = Injector.getInstance().provideRepository();
        repository.initialize();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print(">> ");
            String queryString = reader.readLine();

            try {
                if (queryString.equals("Clear")) {
                    repository.clear();
                    continue;
                } else if (queryString.equals("Exit")) {
                    break;
                } else if (queryString.equals("BuildArtistNameIndex")) {
                    try {
                        repository.buildArtistNameIndex();
                        System.out.println(String.format(">> Successfully created index for artist name column in \"%s\"", AppConfig.ARTIST_NAME_INDEX_PATH));
                        continue;
                    } catch (Exception e) {
                        System.out.println(String.format(">> Error: %s", e.getMessage()));
                    }
                } else if (queryString.equals("BuildArtistIdIndex")) {
                    try {
                        repository.buildArtistIdIndex();
                        System.out.println(String.format(">> Successfully created index for artist id column in \"%s\"", AppConfig.ARTIST_ID_INDEX_PATH));
                        continue;
                    } catch (Exception e) {
                        System.out.println(String.format(">> Error: %s", e.getMessage()));
                    }
                } else if (queryString.equals("FilmCastings")) {
                    List<FilmCasting> castings = repository.getAllFilmCastings();

                    System.out.println("Film castings: ");
                    for (FilmCasting casting : castings) {
                        System.out.println(
                                String.format("%s/%s/%s/%d/%s", casting.getFilm().getName(),
                                        casting.getArtists().stream().map(ArtistDto::getName).collect(Collectors.joining(",")),
                                        casting.getFilm().getDirectorName(),
                                        casting.getFilm().getProductionYear(),
                                        casting.getFilm().getGenre()
                                )
                        );
                    }
                    continue;
                }
                Query query = Query.parseQuery(queryString);

                if (query instanceof AddFilmQuery) {
                    try {
                        repository.addFilm((AddFilmQuery) query);
                    } catch (DuplicateElementIdException e) {
                        String duplicateName = repository.findFilm(((AddFilmQuery) query).getFilmToAdd().getId()).getName();
                        System.out.println(String.format(
                                ">> Error: A film with the same ID (%d) was already in the file system (%s)",
                                ((AddFilmQuery) query).getFilmToAdd().getId(), duplicateName
                        ));
                    } catch (IllegalFieldsException e) {
                        System.out.println(">> Error: Invalid film info.");
                    }
                } else if (query instanceof AddArtistQuery) {
                    if (((AddArtistQuery) query).shouldCheckFilmsExist()) {
                        try {
                            repository.addArtistWithValidation((AddArtistQuery) query);
                        } catch (DuplicateElementIdException e) {
                            String duplicateName = repository.findArtistById(((AddArtistQuery) query).getArtistToAdd().getId()).getName();
                            System.out.println(String.format(
                                    ">> Error: An artist with the same ID (%d) was already in the file system (%s)",
                                    ((AddArtistQuery) query).getArtistToAdd().getId(), duplicateName
                            ));
                        } catch (NonExistingFilmException e) {
                            System.out.println(">> Error: Non existing films were found in the artist's films list.");
                        } catch (IllegalFieldsException e) {
                            System.out.println(">> Error: Invalid film info.");
                        }
                    } else {
                        try {
                            repository.addArtist((AddArtistQuery) query);
                        } catch (DuplicateElementIdException e) {
                            String duplicateName = repository.findArtistById(((AddArtistQuery) query).getArtistToAdd().getId()).getName();
                            System.out.println(String.format(
                                    ">> Error: An artist with the same ID (%d) was already in the file system (%s)",
                                    ((AddArtistQuery) query).getArtistToAdd().getId(), duplicateName
                            ));
                        } catch (IllegalFieldsException e) {
                            System.out.println(">> Error: Film info violates film constraints.");
                        }
                    }
                } else if (query instanceof FindFilmByIdQuery) {
                    try {
                        Film film = repository.findFilm(((FindFilmByIdQuery) query).getFilmId());
                        System.out.println(String.format(">> Repository: Found film named %s with id %d", film.getName(), film.getId()));
                    } catch (NoSuchElementException e) {
                        System.out.println(String.format(
                                ">> Error: No film with id %d was found", ((FindFilmByIdQuery) query).getFilmId()
                        ));
                    }
                } else if (query instanceof FindFilmByNameQuery) {
                    try {
                        Film film = repository.findFilm(((FindFilmByNameQuery) query).getFilmName());
                        System.out.println(String.format(">> Repository: Found film named %s with id %d", film.getName(), film.getId()));
                    } catch (NoSuchElementException e) {
                        System.out.println(String.format(
                                ">> Error: No film named %s was found", ((FindFilmByNameQuery) query).getFilmName()
                        ));
                    }
                } else if (query instanceof RemoveArtistByIdQuery) {
                    try {
                        repository.removeArtist(((RemoveArtistByIdQuery) query).getArtistId());
                    } catch (NoSuchElementException e) {
                        System.out.println(String.format(
                                ">> Error: No artist with id %d was found", ((RemoveArtistByIdQuery) query).getArtistId()
                        ));
                    }
                } else if (query instanceof RemoveFilmByIdQuery) {
                    try {
                        repository.removeFilm(((RemoveFilmByIdQuery) query).getFilmId());
                    } catch (NoSuchElementException e) {
                        System.out.println(String.format(
                                ">> Warning: No film with id %d was found", ((RemoveFilmByIdQuery) query).getFilmId()
                        ));
                    }
                } else if (query instanceof UpdateFilmQuery) {
                    try {
                        repository.updateFilm((UpdateFilmQuery) query);
                        System.out.println(String.format("Updated field %s to %s successfully.", ((UpdateFilmQuery) query).getFieldToUpdate(), ((UpdateFilmQuery) query).getFieldValue()));
                    } catch (NoSuchElementException e) {
                        System.out.println(String.format(
                                ">> Error: No film named %s was found", ((UpdateFilmQuery) query).getName()
                        ));
                    } catch (IllegalFieldsException e) {
                        System.out.println(">> Error: The new field value violates film constraints.");
                    } catch (IllegalUpdateFieldException e) {
                        System.out.println(">> The specified field does not belong to the film model.");
                    }
                } else if (query instanceof UpdateArtistQuery) {
                    try {
                        repository.updateArtist((UpdateArtistQuery) query);
                        System.out.println(String.format("Updated field %s to %s successfully.", ((UpdateArtistQuery) query).getFieldToUpdate(), ((UpdateArtistQuery) query).getFieldValue()));
                    } catch (NoSuchElementException e) {
                        System.out.println(String.format(
                                ">> Error: No artist named %s was found", ((UpdateArtistQuery) query).getName()
                        ));
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(">> Error: Invalid Query.");
            }
        }
    }
}