package datasource.index;

import kotlin.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class IndexMappers {

    public static String mapArtistFilmPairToRecord(Pair<Integer, List<Integer>> pair) {
        return String.format("%d: %s", pair.getFirst(), pair.getSecond().stream().map(it -> String.format("%d", it)).collect(Collectors.joining("-")));
    }

    public static String mapArtistFilmNamePairToRecord(Pair<String, List<Integer>> pair) {
        return String.format("%s: %s", pair.getFirst(), pair.getSecond().stream().map(it -> String.format("%d", it)).collect(Collectors.joining("-")));
    }
}
