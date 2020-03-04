package io;

import java.io.*;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.NoSuchElementException;

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

    private BufferedWriter retrieveAppendBufferedWriter() throws IOException {
        return Files.newBufferedWriter(file.toPath(), StandardOpenOption.APPEND);
    }

    private BufferedReader retrieveBufferedReader() throws IOException {
        return Files.newBufferedReader(file.toPath());
    }

    private PrintWriter retrieveAppendPrintWriter() throws IOException {
        return new PrintWriter(retrieveAppendBufferedWriter());
    }

    // ADD
    public void appendLine(String line) throws IOException {
        PrintWriter writer = retrieveAppendPrintWriter();

        writer.println(line);
        writer.close();
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
}