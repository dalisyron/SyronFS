import di.Injector;
import repository.Repository;
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
import java.util.NoSuchElementException;

/*
Sample test inputs:

_Add Film Query_
Add FilmID: 1123 , FilmName: The Salesman , DirectorName: Asghar Farhadi , ProductionYear: 2016 , Genre: Drama

_Add Artist Query_
Add ArtistID: 2243 , ArtistName: Shahab Hosseini , Age: 46 , ArtistFilms: The Salesman,About Elly,A Separation

_Find Film By FilmID_
Find Film 1123 By FilmID

_Find Film By FilmName_
Find Film The Salesman By FilmName

_Remove Artist By ID_
Remove ArtistID 10

_Remove Film By ID_
Remove FilmID 20

_Update Artist_
Update Artist Shahab Hosseini Set Age to 47

_Update Film_
Update Film The Salesman Set Genre to Thriller
 */
public class SyronFS {

    public static void main(String[] args) throws IOException {
        handleQueries();
    }

    private static void handleQueries() throws IOException {

        Repository repository = Injector.getInstance().provideRepository();
        repository.initialize();
        repository.clear();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print(">> ");
            String queryString = reader.readLine();

            try {
                Query query = Query.parseQuery(queryString);

                if (query instanceof AddFilmQuery) {
                    repository.addFilm((AddFilmQuery) query);
                } else if (query instanceof AddArtistQuery) {
                    repository.addArtist((AddArtistQuery) query);
                } else if (query instanceof FindFilmByIdQuery) {
                    try {
                        repository.findFilm(((FindFilmByIdQuery) query).getFilmId());
                    } catch (NoSuchElementException e) {
                        System.out.println(String.format(
                                ">> Error: No film with id %d was found", ((FindFilmByIdQuery) query).getFilmId()
                        ));
                    }
                } else if (query instanceof FindFilmByNameQuery) {
                    try {
                        repository.findFilm(((FindFilmByNameQuery) query).getFilmName());
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
                    } catch (NoSuchElementException e) {
                        System.out.println(String.format(
                                ">> Error: No film named %s was found", ((UpdateFilmQuery) query).getName()
                        ));
                    }
                } else if (query instanceof UpdateArtistQuery) {
                    try {
                        repository.updateArtist((UpdateArtistQuery) query);
                    } catch (NoSuchElementException e) {
                        System.out.println(String.format(
                                ">> Error: No artist named %s was found", ((UpdateArtistQuery) query).getName()
                        ));
                    }
                }
            } catch (IllegalArgumentException e) {
                System.err.println(">> Error: Invalid Query.");
            }
        }
    }
}