package edu.sandbox.accessmodifiers.subpackage;

import edu.sandbox.accessmodifiers.ParentClass;

/**
 * Created by Lena on 23.04.14.
 */
public class ChildClass extends ParentClass {
    public ChildClass() {
        protectedInt = 100003;
        publicInt = 100004;
    }

//    protected int getPackageInt() {
//        return 6;
//    }

//    public int getPackageInt() {
//        return npackageInt; //not accessible
//    }

    public String toString() {
        return "ChildClass: "
                //"\nprivateInt = " + privateInt //not accessible
                //+ "\npackageInt = " + packageInt //not accessible
                + "\nprotectedInt = " + protectedInt
                + "\npublicInt = " + publicInt;
    }
}
