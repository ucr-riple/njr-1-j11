package br.com.bit.ideias.reflection.scanner;

import java.io.File;
import java.net.URL;

/**
 * @author Leonardo Campos
 * @date 16/08/2009
 */
public class FileSystemResource implements Resource {
    private final File file;

    public FileSystemResource(File file) {
        this.file = file;
    }

    public Resource createRelative(String relativePath) {
        return null;
    }

    public File getFile() {
        return null;
    }

    public URL getURL() {
        return null;
    }
    
    @Override
    public String toString() {
        return file.toString();
    }
}
