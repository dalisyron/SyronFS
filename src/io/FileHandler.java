package io;

import java.io.*;
import java.nio.file.Files;

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
    public boolean appendLine(String line) {
        PrintWriter writer = retrievePrintWriter();

        if (writer != null) {
            writer.println(line);
            writer.close();
            return true;
        }

        return false;
    }

    // FIND
    public String findLine(String key, LineValidator lineValidator) throws IOException {
        try {
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
                if (lineValidator.validate(line, key)) {
                    return line;
                }
            }

            reader.close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // DELETE
    public boolean deleteLine(String key, LineValidator lineValidator) {
        try {
            File tempFile = new File("tempFile.txt");
            boolean foundRecord = false;

            BufferedReader reader = retrieveBufferedReader();
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if (lineValidator.validate(trimmedLine, key) && !foundRecord) {
                    foundRecord = true;
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            writer.close();
            reader.close();
            boolean successful = tempFile.renameTo(file);

            return successful && foundRecord;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE
    public boolean updateLine(String key, String newRecord, LineValidator lineValidator) {
        File tempFile = new File("tempFile.txt");
        boolean foundRecord = false;
        try {
            BufferedReader reader = retrieveBufferedReader();
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if (lineValidator.validate(trimmedLine, key) && !foundRecord) {
                    foundRecord = true;
                    writer.write(newRecord + System.getProperty("line.separator"));
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            writer.close();
            reader.close();
            boolean successful = tempFile.renameTo(file);

            return successful && foundRecord;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // LINE COUNT
    public int getLineCount() throws IOException {
        BufferedReader bufferedReader = retrieveBufferedReader();
        int count = 0;

        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            count++;
        }

        return count;
    }
}