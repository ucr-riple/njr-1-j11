
package net.slreynolds.ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;


/**
 *
 */
public class SecondTest extends AbstractJavaTest {
	
	public SecondTest() {
		super("../graphs/java");
	}
	
	public void run() {

        /* --- Foo array --- */
        Foo[] fooarray = new Foo[] {
        		new Foo(1),
        		new Foo(2),
        		new Foo(3)
        };
        saveToFiles(fooarray, "fooarray");
        
        /* --- Foo --- */
        Foo foo = new Foo(1);		
        saveToFiles(foo, "foo"); 
       
         
        /* --- darray--- */
        Double[] darray = new Double[3];
        darray[0] = 1.0;
        darray[1] = 2.0;
        darray[2] = 3.0;
        saveToFiles(darray, "darray");
        
        /* --- Foo Bar--- */
        Foo foo1 = new Foo(5);
        Bar bar = new Bar(6);
        saveToFiles(new Object[]{foo1,bar},
        		    new String[]{"foo1","bar"},"foobar");
        
        /* --- strings --- */
        String brother = "brother";
        String the = brother.substring(3, 6);
        saveToFiles(new Object[]{brother,the},
        		   new String[]{"brother","the"},"strings");
        
        /* --- alist --- */
        ArrayList<Bar> alist = new ArrayList<Bar>(2);
        alist.add(new Bar(1));
        alist.add(new Bar(2));
        saveToFiles(alist, "alist");
        
        
        /* --- llist --- */
        LinkedList<Bar> llist = new LinkedList<Bar>();
        llist.add(new Bar(1));
        llist.add(new Bar(2));
        saveToFiles(llist, "llist");
        
        
        /* --- hmap --- */
        HashMap<Foo,Bar> hmap = new HashMap<Foo,Bar>();
        hmap.put(new Foo(1), new Bar(1));
        hmap.put(new Foo(2), new Bar(2));
        saveToFiles(hmap, "hmap");
        
        /* --- tmap --- */
        TreeMap<Foo,Bar> tmap = new TreeMap<Foo,Bar>();
        tmap.put(new Foo(1), new Bar(1));
        tmap.put(new Foo(2), new Bar(2));
        saveToFiles(tmap, "tmap");
        
 
        /* --- interned strings --- */
        String practical = "practical";
        practical.intern();
        String cal = practical.substring(6,8);
        saveToFiles(new Object[]{practical,cal},
     		   new String[]{"practical","cal"},"interned_strings");
       
        System.out.printf("all done\n");
	}
	
	public static void main(String[] args) {
    	new SecondTest().run();
    }
}
