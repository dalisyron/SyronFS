package SyronFS.di;

/**
 * The file config should be handled by the file system in a real application
 * But it's stored in this way here, Since in the project documentation handed by the TAs it was specified that-
 * files should be named Films.txt and Artist.txt
 */
public class AppConfig {

    public AppConfig() {
    }

    public static String FILM_PATH = "./resource/Films.txt";
    public static String ARTIST_PATH = "./resource/Artists.txt";
    public static String ARTIST_ID_INDEX_PATH = "./resource/ArtistIDIndex.txt";
    public static String ARTIST_NAME_INDEX_PATH = "./resource/ArtistNameIndex.txt";
}
