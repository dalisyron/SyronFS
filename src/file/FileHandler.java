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

    private BufferedWriter retrieveBufferedWriter() throws IOException {
        return Files.newBufferedWriter(file.toPath());
    }

    private BufferedReader retrieveBufferedReader() {
        try {
            return Files.newBufferedReader(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private PrintWriter retrievePrintWriter() {
        try {
            return new PrintWriter(retrieveBufferedWriter());
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

    // DELETE
    public boolean deleteLine(String key, RecordValidator recordValidator) throws IOException {
        File tempFile = new File("tempFile.txt");
        boolean foundRecord = false;

        BufferedReader reader = retrieveBufferedReader();
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if (recordValidator.validate(trimmedLine, key) && !foundRecord) {
                foundRecord = true;
                continue;
            }
            writer.write(currentLine + System.getProperty("line.separator"));
        }

        writer.close();
        reader.close();
        boolean successful = tempFile.renameTo(file);

        return successful && foundRecord;
    }
}