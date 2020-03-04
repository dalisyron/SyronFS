package datasource.film.test

import datasource.film.FilmDataSource
import entity.Film
import io.FileHandler
import java.io.File

val Avengers = Film(
    20,
    "Avengers",
    "Dan bliz",
    2012,
    "Action"
)

val Separation = Film(
    10,
    "Separation",
    "Asghar joon",
    2011,
    "Drama"
)

val Avatar = Film(
    30,
    "Avatar",
    "James",
    2010,
    "Sci-fi"
)

fun main() {
    val dataSource =
        FilmDataSource(FileHandler(File("/Users/mobin/Library/Preferences/IdeaIC2019.3/scratches/scratch.txt")))

    dataSource.clearData()

    dataSource.addFilm(Avatar)

    dataSource.addFilm(Separation)
    dataSource.addFilm(Avengers)

    dataSource.deleteFilm(10)
}