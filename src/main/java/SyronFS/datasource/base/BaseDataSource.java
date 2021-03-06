package SyronFS.datasource.base;

import SyronFS.io.FileHandler;

import java.io.IOException;
import java.nio.file.FileSystemException;

public abstract class BaseDataSource {

    public abstract void initialize();

    public abstract void clearData();

}
