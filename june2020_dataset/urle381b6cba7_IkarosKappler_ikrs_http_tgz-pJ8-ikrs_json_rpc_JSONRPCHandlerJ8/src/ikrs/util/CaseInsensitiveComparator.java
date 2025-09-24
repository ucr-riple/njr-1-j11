package ikrs.util;

import java.text.Collator;
import java.util.Comparator;

/**
 * @author Ikaros Kappler
 * @date 2012-04-24
 * @version 1.0.0
 **/


public class CaseInsensitiveComparator 
    implements Comparator<String> {

    
    public static final Comparator<String> sharedInstance = createCaseInsensitiveCollator();

    private CaseInsensitiveComparator() {	

    }

    
    public int compare(String o1, String o2)  {
	return o1.compareToIgnoreCase(o2);
    }

    public boolean equals(Object obj) {
	return false;
    }


    public static CaseInsensitiveComparator createCaseInsensitiveCollator() {
	
	//Collator c = Collator.getInstance();
	//c.setStrength( Collator.PRIMARY );
	//return c;
	
	return new CaseInsensitiveComparator();
	
    }
    

}