package br.com.bit.ideias.reflection.scanner;

import java.io.File;
import java.net.URL;

/**
 * @author Leonardo Campos
 * @date 16/08/2009
 */
public interface Resource {
    public File getFile();
    public URL getURL();
    public Resource createRelative(String relativePath);
}
