package datasource.film.test

import datasource.film.FilmDataSource
import datasource.dto.FilmDto
import io.FileHandler
import java.io.File

val Avengers = FilmDto(
    20,
    "Avengers",
    "Dan bliz",
    2012,
    "Action"
)

val Separation = FilmDto(
    10,
    "Separation",
    "Asghar joon",
    2011,
    "Drama"
)

val Avatar = FilmDto(
    30,
    "Avatar",
    "James",
    2010,
    "Sci-fi"
)

fun main() {
    val dataSource =
        FilmDataSource(FileHandler(File("./resource/Films.txt")))

    dataSource.initialize()

    dataSource.addFilm(Avatar)

    dataSource.addFilm(Separation)
    dataSource.addFilm(Avengers)

    dataSource.deleteFilm(10)
}