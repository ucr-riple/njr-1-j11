package edu.sandbox;

import java.util.ArrayList;

/**
 * Created by yurij.pyvovarenko on 19.04.14.
 */
public final class ImmutableObject implements Cloneable {
    private final int anInt;
    private final ArrayList<String> strings;
    private final ArrayList<StringBuffer> stringBuffers;

    public ImmutableObject(int anInt, final ArrayList<String> strings, final ArrayList<StringBuffer> stringBuffers) {
        this.anInt = anInt;
        this.strings = (ArrayList<String>) strings.clone();
        this.stringBuffers = deepSBCopy(stringBuffers);
//                = new ArrayList<StringBuffer>();
//        for (StringBuffer sb : stringBuffers) {
//            this.stringBuffers.add(new StringBuffer(sb));
//        }

        //((ArrayList<StringBuffer>) stringBuffers.clone());//(ArrayList<StringBuffer>) Collections.unmodifiableList(stringBuffers);
    }

    private static synchronized
            ArrayList<StringBuffer> deepSBCopy
                (ArrayList<StringBuffer> SBtoBeCopied) {
        ArrayList<StringBuffer> SBCopy = new ArrayList<StringBuffer>();
        for (StringBuffer sb : SBtoBeCopied) {
            SBCopy.add(new StringBuffer(sb));
        }
        return SBCopy;
    }

    public final int getAnInt() {
        return anInt;
    }

    public final ArrayList<String> getStrings() {
        return (ArrayList<String>) strings.clone();
    }

//    private void setAnInt(int anInt) {
//        this.anInt = anInt;
//    }
//
//    private void setStrings(ArrayList<String> strings) {
//        this.strings = strings;
//    }
//
//    private void setStringBuffers(ArrayList<StringBuffer> stringBuffers) {
//        this.stringBuffers = stringBuffers;
//    }

    public final ArrayList<StringBuffer> getStringBuffers() {
        return deepSBCopy(stringBuffers);
        //return (ArrayList<StringBuffer>) stringBuffers.clone();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
//        ImmutableObject cloned = (ImmutableObject) super.clone();
//        cloned.anInt = getAnInt();
//        cloned.setStrings( (ArrayList<String>) getStrings().clone() );
//        cloned.setStringBuffers( deepSBCopy(getStringBuffers()) );
//        return cloned;
        return (Object) deepCopy();
    }

    public ImmutableObject deepCopy() throws CloneNotSupportedException {
        return new ImmutableObject(
                anInt,
                strings, //(ArrayList<String>) strings.clone(),
                stringBuffers);//(ArrayList<StringBuffer>) stringBuffers.clone());
    }
//    public ImmutableObject clone() throws CloneNotSupportedException {
//        ImmutableObject newImmutableObject = (ImmutableObject) super.clone();
//        newImmutableObject.anInt = anInt;
//        newImmutableObject.stringBuffers = stringBuffers.clone();
//        return newImmutableObject;
//    }

    public String toString() {
        return "anInt = " + this.getAnInt()
            + "\nstrings = " + this.getStrings()
            + "\nstringBuffers = " + this.getStringBuffers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImmutableObject that = (ImmutableObject) o;

        if (anInt != that.anInt) {
            System.out.println("\tequals(): anInt != that.anInt");
            return false;
        }

        if (stringBuffers != null ? !stringBuffers.toString().equals(that.stringBuffers.toString()) : that.stringBuffers != null) {
//            System.out.println("\tequals(): stringBuffers != null ? !stringBuffers.equals(that.stringBuffers) : that.stringBuffers != null");
//            System.out.println("\tequals(): stringBuffers = " + stringBuffers);
//            System.out.println("\tequals(): that.stringBuffers = " + that.stringBuffers);
//            System.out.println("\tequals(): stringBuffers.equals(that.stringBuffers) = " + stringBuffers.equals(that.stringBuffers));
            return false;
        }

        if (strings != null ? !strings.equals(that.strings) : that.strings != null) {
            System.out.println("\tequals(): strings != null ? !strings.equals(that.strings) : that.strings != null");
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = anInt;
        result = 31 * result + (strings != null ? strings.hashCode() : 0);
        result = 31 * result + (stringBuffers != null ? stringBuffers.hashCode() : 0);
        return result;
    }

    public static void main(String[] args) {
        ArrayList<String> outerStringsList = new ArrayList<String>();
        ArrayList<StringBuffer> outerSBList = new ArrayList<StringBuffer>();// = (ArrayList) Arrays.asList("q",1,3.2f,'c');
        for (Integer i = 0; i < 5; i++ ) {
            outerStringsList.add(i.toString());
            StringBuffer sb = new StringBuffer(i.toString());
            outerSBList.add(sb);
        }
        int i = 10;
        final ImmutableObject anImmutableObject =
                new ImmutableObject(i, outerStringsList, outerSBList);
        System.out.println(anImmutableObject);

        System.out.println("try to modify anImmutableObject...");
        outerStringsList.add("ddddddddddddddd");
        outerStringsList.get(0).concat("qazzzzie");
        outerSBList.add(new StringBuffer("ddddd"));
        outerSBList.get(0).append("qaz");
        anImmutableObject.getStrings().add("qwerty");
        anImmutableObject.getStrings().get(0).concat("zzzzzzzzz");
        anImmutableObject.getStringBuffers().add(new StringBuffer("qwerty"));
        anImmutableObject.getStringBuffers().get(0).append("ssss");


        ImmutableObject clonedImmutableObject = null;
        try {
            clonedImmutableObject = anImmutableObject.deepCopy();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        System.out.println("clonedImmutableObject != anImmutableObject: "
                + (clonedImmutableObject != anImmutableObject));
        System.out.println("clonedImmutableObject.getClass() == anImmutableObject.getClass(): "
                + (clonedImmutableObject.getClass() == anImmutableObject.getClass()));
        System.out.println("clonedImmutableObject.equals(anImmutableObject): "
                + clonedImmutableObject.equals(anImmutableObject));
        clonedImmutableObject.getStrings().add("qwerty111");
        clonedImmutableObject.getStrings().get(0).concat("zzzzzzzzz111");
        clonedImmutableObject.getStringBuffers().add(new StringBuffer("qwerty111"));
        clonedImmutableObject.getStringBuffers().get(0).append("ssss1111");

        System.out.println(anImmutableObject);
    }
}