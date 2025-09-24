package de.codesourcery.dcpu16.util;

import java.io.IOException;
import java.io.InputStream;

public interface IInputStreamProvider
{
    public InputStream createInputStream() throws IOException;
}
