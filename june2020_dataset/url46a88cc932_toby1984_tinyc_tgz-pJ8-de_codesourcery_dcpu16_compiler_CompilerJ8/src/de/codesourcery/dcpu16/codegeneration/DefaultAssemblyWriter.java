package de.codesourcery.dcpu16.codegeneration;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DefaultAssemblyWriter implements IAssemblyWriter
{
    private static enum Segment {
        CODE,DATA;
    }
    
    private final PrintWriter writer;
    private Segment segment = Segment.CODE;
    
    private final List<String> dataSegment = new ArrayList<>();

    public DefaultAssemblyWriter(OutputStream out) {
        this( new PrintWriter(out,true));
    }
    
    public DefaultAssemblyWriter(PrintWriter writer)
    {
        if (writer == null) {
            throw new IllegalArgumentException("writer must not be NULL.");
        }
        this.writer = writer;
    }

    @Override
    public void flush()
    {
        flushCodeSegment();
        flushDataSegment();
    }

    @Override
    public void flushCodeSegment()
    {
        writer.flush();        
    }

    @Override
    public void flushDataSegment()
    {
        for ( String s : dataSegment ) {
            writer.write(s);
        }
        dataSegment.clear();
        writer.flush();
    }

    @Override
    public void codeSegment()
    {
        segment = Segment.CODE;
    }

    @Override
    public void dataSegment()
    {
        segment = Segment.DATA;        
    }

    @Override
    public void write(String s)
    {
        if ( segment == Segment.CODE ) {
            final boolean isLabel = s.trim().endsWith(":");
                
            if ( isLabel ) {
                writer.write("; ===================================\n");
            }
            writer.write( s );
            if ( isLabel ) {
                writer.write("; ===================================\n");
            }            
            writer.flush();
        } else {
            dataSegment.add( s );
        }
    }
}
