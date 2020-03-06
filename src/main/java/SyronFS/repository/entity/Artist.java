package SyronFS.repository.entity;

import java.util.List;

public class Artist {

    private final int id;
    private final String name;
    private final int age;
    private final List<Film> films;

    public Artist(int id, String name, int age, List<Film> films) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.films = films;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<Film> getFilms() {
        return films;
    }
}