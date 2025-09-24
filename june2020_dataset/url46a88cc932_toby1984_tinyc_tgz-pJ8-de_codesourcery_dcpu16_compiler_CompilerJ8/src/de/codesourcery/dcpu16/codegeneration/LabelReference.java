package de.codesourcery.dcpu16.codegeneration;


public final class LabelReference
{
    public final String identifier;

    public LabelReference(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }
}