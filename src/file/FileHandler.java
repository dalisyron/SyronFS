package file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileHandler {

    private File file;

    public FileHandler(File file) {
        this.file = file;
    }

    public FileHandler(String path) {
        this(new File(path));
    }

    private PrintWriter retrievePrintWriter() {
        try {
            return new PrintWriter(Files.newBufferedWriter(file.toPath(), StandardOpenOption.APPEND));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private BufferedReader retrieveBufferedReader() {
        try {
            return Files.newBufferedReader(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ADD
    public void appendLine(String line) {
        PrintWriter writer = retrievePrintWriter();

        if (writer != null) {
            writer.println(line);
            writer.close();
        }
    }

    // FIND
    public String findLine(String key, RecordValidator recordValidator) {
        BufferedReader reader = retrieveBufferedReader();

        if (reader == null) {
            System.err.println("Failed to retrieve new BufferedReader");
            return null;
        }

        String line = "";

        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (recordValidator.validate(line, key)) {
                return line;
            }
        }

        return null;
    }
}