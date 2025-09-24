package edu.sandbox;

import edu.sandbox.accessmodifiers.SamePackageClass;
import edu.sandbox.accessmodifiers.subpackage.ChildClass;
import edu.sandbox.accessmodifiers.ParentClass;

/**
 * Created by Lena on 23.04.14.
 */
public class AccessModifiersTests {
    public static void main(String[] args) {
        ParentClass p = new ParentClass();
//        p.privateInt = 1; //not accesible
//        p.packageInt = 2; //not accesible
//        p.protectedInt = 3; //not accesible
        p.publicInt = 4;
        System.out.println("AccessModifiersTests: " + p);
        SamePackageClass.printParentClassFields();

        ParentClass pc = new ChildClass();
        System.out.println("ParentClass pc = new ChildClass();" + pc);

        ChildClass c = new ChildClass();
        System.out.println("ChildClass c = new ChildClass();" + c);
/*
        System.out.println("ParentClass p = new ParentClass();" + p.getPackageInt());

        ParentClass pc = new ChildClass();
        System.out.println("ParentClass pc = new ChildClass();" + pc.getPackageInt());

        ChildClass c = new ChildClass();
        System.out.println("ChildClass c = new ChildClass();" + c.getPackageInt());

        ChildClass cc = (ChildClass) pc;
        cc.setA(3);
        System.out.println("cc.setA(3); ParentClass pc.getPackageInt() = " + pc.getPackageInt());

        ChildClass ppc = (ChildClass) p;
        ppc.setA(7);
        System.out.println("ppc.setA(7); ParentClass ppc.getPackageInt() = " + ppc.getPackageInt());
*/
    }
}
