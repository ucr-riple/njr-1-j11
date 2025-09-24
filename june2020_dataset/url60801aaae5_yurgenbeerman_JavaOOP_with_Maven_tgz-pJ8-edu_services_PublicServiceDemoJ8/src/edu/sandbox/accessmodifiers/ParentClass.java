package edu.sandbox.accessmodifiers;

/**
 * Created by Lena on 23.04.14.
 */
public class ParentClass {
    private int privateInt;
    int packageInt;
    protected int protectedInt;
    public int publicInt;

    public ParentClass()  {
        privateInt = 101;
        packageInt = 102;
        protectedInt = 103;
        publicInt = 104;
    }

    protected int getPackageInt() {
        return packageInt;
    }

    public String toString() {
        return "ParentClass"
        + "\nprivateInt = " + privateInt
        + "\npackageInt = " + packageInt
        + "\nprotectedInt = " + protectedInt
        + "\npublicInt = " + publicInt;
    }

    public int hashCode() {
        return publicInt * 7; // for hashCode tests in collectionsandthreads.CollectionsTests
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParentClass that = (ParentClass) o;

        if (packageInt != that.packageInt) return false;
        if (privateInt != that.privateInt) return false;
        if (protectedInt != that.protectedInt) return false;
        if (publicInt != that.publicInt) return false;

        return true;
    }
}
