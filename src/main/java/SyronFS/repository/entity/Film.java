package SyronFS.repository.entity;

public class Film {

    private final int id;
    private final String name;
    private final String directorName;
    private final int productionYear;
    private final String genre;

    public Film(int id, String name, String directorName, int productionYear, String genre) {

        this.id = id;
        this.name = name;
        this.directorName = directorName;
        this.productionYear = productionYear;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDirectorName() {
        return directorName;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public String getGenre() {
        return genre;
    }
}