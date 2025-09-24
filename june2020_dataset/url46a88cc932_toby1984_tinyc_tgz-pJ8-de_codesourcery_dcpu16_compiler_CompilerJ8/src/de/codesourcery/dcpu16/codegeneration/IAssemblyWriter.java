package de.codesourcery.dcpu16.codegeneration;


public interface IAssemblyWriter 
{
    public void codeSegment();
    
    public void dataSegment();
    
    public void write(String s);
    
    public void flush();
    
    public void flushCodeSegment();

    public void flushDataSegment();    
}
