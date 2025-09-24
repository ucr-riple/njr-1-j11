
package net.slreynolds.ds;



/**
 *
 */
public class ArraysTest extends AbstractJavaTest {
	
	public ArraysTest() {
		super("../graphs/java.arrays");
	}
	
	public void run() {

        /* --- Foo array --- */
        Foo[] fooarray = new Foo[] {
        		new Foo(1),
        		new Foo(2),
        		new Foo(3)
        };
        saveToFiles(fooarray, "fooarray");
        
        /* --- int array -- */
        int[] intarray = new int[] {1,2,3};	
        saveToFiles(intarray, "intarray");
       
        /* --- Foo array with sharing --- */
        Foo foo2 = new Foo(2);
        Foo[] fooarray2 = new Foo[] {
        		new Foo(1),
        		foo2,
        		new Foo(3)
        };
        saveToFiles(new Object[]{foo2,fooarray2}, 
        		    new String[]{"foo2","fooarray2"},
        		   "foosharingarray");
        
        /* --- Foo array with sharing in the other order --- */
        saveToFiles(new Object[]{fooarray2,foo2}, 
        		    new String[]{"fooarray2","foo2"},
        		   "foosharingooarray");
        
        /* --- Two Foo array --- */
        Foo foo3 = new Foo(3);
        Foo[] fooarray3 = new Foo[] {
        		new Foo(1),
        		foo3,
        		new Foo(4)
        };
        Foo[] fooarray4 = new Foo[] {
        		new Foo(10),
        		foo3,
        		new Foo(40)
        };
        saveToFiles(new Object[]{foo3,fooarray3,fooarray4}, 
    		    new String[]{"foo3","fooarray3","fooarray4"},
    		   "foo3foo4arrays");
        
        System.out.printf("all done\n");
	}
	
	public static void main(String[] args) {
    	new ArraysTest().run();
    }
}
