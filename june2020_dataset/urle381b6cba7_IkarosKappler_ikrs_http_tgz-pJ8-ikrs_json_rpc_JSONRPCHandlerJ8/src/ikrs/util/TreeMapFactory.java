package ikrs.util;

/**
 *
 *
 * @author Ikaros Kappler
 * @date 2012-04-24
 * @version 1.0.0
 **/ 

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapFactory<K,V>
    implements MapFactory<K,V> {

    private Comparator<K> keyComparator;

    public TreeMapFactory() {
	super();
    }

    public TreeMapFactory( Comparator<K> keyComparator ) {
	super();

	this.keyComparator = keyComparator;

    }

    public Map<K,V> createMap() {
	if( this.keyComparator == null )
	    return new TreeMap<K,V>();
	else
	    return new TreeMap<K,V>( this.keyComparator );
    }
    

}