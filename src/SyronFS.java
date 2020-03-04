import datasource.film.FilmDataSource;
import datasource.film.mapper.DSTestKt;
import io.FileHandler;

import java.io.File;

public class SyronFS {

    public static void main(String[] args) {
        FilmDataSource dataSource = new FilmDataSource(new FileHandler(new File("/Users/mobin/Library/Preferences/IdeaIC2019.3/scratches/scratch.txt")));


    }
}