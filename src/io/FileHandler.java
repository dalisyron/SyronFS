package io;

import repository.entity.Film;

import java.io.*;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class FileHandler {

    private File file;
    private final LineEnumerator lineEnumerator;

    public FileHandler(File file) {
        this.file = file;
        lineEnumerator = (line, number) -> {
            int dashIndex = line.indexOf('-');
            String trimSubstring = line.substring(0, dashIndex + 1);

            String record = line.replaceFirst(Pattern.quote(trimSubstring), "").trim();

            return String.format("%d-%s", number, record);
        };
    }

    public FileHandler(String path) {
        this(new File(path));
    }

    public void initialize() throws FileSystemException {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                throw new FileSystemException(String.format("Internal File System Error: Could not create new file for %s", file.getName()));
            }
        }
    }
    private BufferedWriter retrieveBufferedWriter() throws IOException {
        return Files.newBufferedWriter(file.toPath());
    }

    private BufferedWriter retrieveAppendBufferedWriter() throws IOException {
        return Files.newBufferedWriter(file.toPath(), StandardOpenOption.APPEND);
    }

    private BufferedReader retrieveBufferedReader() throws IOException {
        return Files.newBufferedReader(file.toPath());
    }

    private PrintWriter retrieveAppendPrintWriter() throws IOException {
        return new PrintWriter(retrieveAppendBufferedWriter());
    }

    private PrintWriter retrieveNonAppendPrintWriter() throws IOException {
        return new PrintWriter(retrieveBufferedWriter());
    }

    // ADD
    public void appendLine(String line) throws IOException {
        PrintWriter writer = retrieveAppendPrintWriter();

        writer.println(line);
        writer.close();
        enumerateLines();
    }

    // FIND
    public String findLine(String key, LineValidator lineValidator) throws IOException {
        BufferedReader reader = retrieveBufferedReader();

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if (lineValidator.validate(trimmedLine, key)) {
                return trimmedLine;
            }
        }

        reader.close();
        throw new NoSuchElementException("Record Error: Requested record was not found");
    }

    // DELETE
    public void deleteLine(String key, LineValidator lineValidator) throws IOException {
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
        if (!foundRecord) {
            throw new NoSuchElementException("Record Error: Requested record was not found");
        }

        boolean renameSuccessful = tempFile.renameTo(file);

        if (!renameSuccessful) {
            throw new FileSystemException(String.format("Internal File System Error: Could not update the temporary %s file.", file.getName()));
        }
        enumerateLines();
    }

    // UPDATE
    public void updateLine(String key, String newRecord, LineValidator lineValidator) throws IOException {
        File tempFile = new File("tempFile.txt");
        boolean foundRecord = false;

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
        if (!foundRecord) {
            throw new NoSuchElementException("Record Error: Requested record was not found");
        }

        boolean renameSuccessful = tempFile.renameTo(file);

        if (!renameSuccessful) {
            throw new FileSystemException(String.format("Internal File System Error: Could not update the temporary %s file.", file.getName()));
        }
    }

    public void clearFile() throws IOException {
        BufferedWriter bufferedWriter = retrieveBufferedWriter();

        bufferedWriter.write("");
    }

    private void enumerateLines() throws IOException {
        File tempFile = new File("tempFile.txt");
        boolean foundRecord = false;

        BufferedReader reader = retrieveBufferedReader();
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        int ind = 1;

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            writer.write(lineEnumerator.enumerate(trimmedLine, ind) + System.getProperty("line.separator"));
            ind++;
        }

        writer.close();
        reader.close();

        boolean renameSuccessful = tempFile.renameTo(file);

        if (!renameSuccessful) {
            throw new FileSystemException(String.format("Internal File System Error: Could not update the temporary %s file.", file.getName()));
        }
    }

    public File getFile() {
        return file;
    }

    public List<String> getAllRecords() throws IOException {
        BufferedReader reader = retrieveBufferedReader();

        String currentLine;

        ArrayList<String> result = new ArrayList<>();

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            result.add(trimmedLine);
        }

        reader.close();
        return result;
    }

    public void writeLines(List<String> lines) throws IOException {
        PrintWriter printWriter = retrieveNonAppendPrintWriter();

        for (String line : lines) {
            printWriter.println(line);
        }
        printWriter.close();
    }
}