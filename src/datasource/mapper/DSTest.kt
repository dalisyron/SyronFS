package datasource.mapper

import datasource.FilmDataSource
import entity.Film
import io.FileHandler
import java.io.File

fun main() {
    val dataSource = FilmDataSource(FileHandler(File("/Users/mobin/Library/Preferences/IdeaIC2019.3/scratches/scratch.txt")))

    dataSource.addFilm(
        Film(
            10,
            "Separation",
            "Asghar joon",
            2011,
            "Drama"
        )
    )
}