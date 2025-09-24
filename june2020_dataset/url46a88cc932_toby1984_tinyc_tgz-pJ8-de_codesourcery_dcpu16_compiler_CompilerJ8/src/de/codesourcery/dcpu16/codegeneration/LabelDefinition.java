package de.codesourcery.dcpu16.codegeneration;

public final class LabelDefinition
{
    public final String identifier;

    public LabelDefinition(String identifier) {
        this.identifier = identifier;
    }

    public LabelReference ref() {
        return new LabelReference( identifier );
    }

    @Override
    public String toString() {
        return ":"+identifier;
    }
}   