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

    public void appendLine(String line) {
        PrintWriter printWriter = retrievePrintWriter();

        if (printWriter != null) {
            printWriter.println(line);
            printWriter.close();
        }
    }
}