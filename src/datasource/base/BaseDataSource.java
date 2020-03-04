package datasource.base;

import io.FileHandler;

import java.io.IOException;
import java.nio.file.FileSystemException;

public abstract class BaseDataSource {

    protected FileHandler fileHandler;

    public BaseDataSource(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public void initialize() {
        try {
            fileHandler.initialize();
        } catch (FileSystemException e) {
            System.err.println(">> Error while initializing new file source for films");
        }
    }

    public void clearData() {
        try {
            fileHandler.clearFile();
            System.out.println(">> Successfully cleared all data");
        } catch (FileSystemException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
