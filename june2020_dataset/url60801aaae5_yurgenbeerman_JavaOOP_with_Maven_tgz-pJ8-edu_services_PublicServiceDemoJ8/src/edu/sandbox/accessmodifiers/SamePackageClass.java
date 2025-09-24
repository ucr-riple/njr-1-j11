package edu.sandbox.accessmodifiers;

/**
 * Created by Lena on 23.04.14.
 */
public class SamePackageClass {
    public static void printParentClassFields() {
        ParentClass p = new ParentClass();
        //p.privateInt = 1001; //blenot accesible
        p.packageInt = 10002;
        p.protectedInt = 10003;
        p.publicInt = 10004;

        System.out.println("SamePackageClass: " + p);
    }
}
