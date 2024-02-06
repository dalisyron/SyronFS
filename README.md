# SyronFS
File based information storage and retrieval system written in Java

The project provides APIs to create, modify, or delete different entities related to movies. To achieve this, custom serializers and deserializers have been implemented.

Developer Note: This cumbersome project was implemented to appreciate the benefits of a modern database over a traditional file based information retrieval system :)

## Entities:
- Artist
```
Artist(id: Int, name: String, age: Int, films: List<Film>)
```

- Film
```
Film(id: Int, name: String, directorName: String, productionYear: Int, genre: String)
```

- FilmCasting
```
FilmCasting(film: Film, artists: List<Artist>)
```

## APIs
```
public void clear()
```

```
public void addFilm(AddFilmQuery query)
```

```
public void addArtist(AddArtistQuery query)
```

```
public Film findFilm(int filmId)
```

```
public Film findFilm(String filmName)
```

```
public void removeArtist(int artistId)
```

```
public void removeFilm(int filmId)
```

```
public void updateFilm(UpdateFilmQuery query)
```

```
public void updateArtist(UpdateArtistQuery query)
```

```
public Artist findArtistEntityById(int id)
```

```
public ArtistDto findArtistById(int id)
```

```
public void addArtistWithValidation(AddArtistQuery query)
```

```
public List<FilmCasting> getAllFilmCastings()
```

```
public void buildArtistNameIndex()
```

```
public void buildArtistIdIndex()
```
