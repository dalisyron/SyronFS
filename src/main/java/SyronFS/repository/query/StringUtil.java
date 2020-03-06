package SyronFS.repository.query;

import java.util.regex.Pattern;

public class StringUtil {

    public static String getFirstWord(String s) {
        return s.split(Pattern.quote(" "))[0];
    }
}
