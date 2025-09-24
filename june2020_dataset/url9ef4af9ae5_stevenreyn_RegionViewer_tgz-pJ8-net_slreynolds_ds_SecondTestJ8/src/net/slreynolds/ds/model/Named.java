
package net.slreynolds.ds.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
abstract public class Named {
    
    private final Map<String, Object> _attributes;
    private final String _name;
    private final int _ID;

    /** Denote that this Named is anonymous */
    public final static String EMPTY_NAME = "NO NAME SPECIFIED";
    /** Key for class */
    public final static String CLASS = "Class";
    /** Key for value */
    public final static String VALUE = "Value";
    /** Key for label */
    public final static String LABEL = "Label";
    /** Key for System hash */
    public final static String SYSTEM_HASH = "SystemHash";
    /** Key for array index */
    public final static String ARRAY_INDEX = "ArrayIndex";
    /** Key to make this element invisible when exported or displayed */
    public final static String HIDDEN = "Hidden";
    /** Key to  make this element a symbol */
    public final static String SYMBOL = "Symbol";

    
    public Named(NamedID ID, String name) {

        if (name == null) {
            throw new IllegalArgumentException("Cannot use null for a name");
        }
        if (name.length() <= 0) {
            throw new IllegalArgumentException("Name length must be positive");
        }
        _name = name;
        _ID = ID.asInt();
        _attributes = new HashMap<String,Object>();
    }
    
    public Named(NamedID ID) {
        _name = EMPTY_NAME;
        _ID = ID.asInt();
        _attributes = new HashMap<String,Object>();
    }
    
    public boolean isAnonymous() {
        return _name.equals(EMPTY_NAME);
    }
    
    public int getID() {
        return _ID;
    }
    
    public boolean hasID() {
        return (_ID > 0);
    }
 
    public String getName() {
        return _name;
    }
    
    public Object getAttr(String key) {
        return _attributes.get(key);
    }
    
    public boolean hasAttr(String key) {
        return _attributes.containsKey(key);
    }
    
    public Named putAttr(String key, Object value) {
        _attributes.put(key, value);
        return this;
    }
    
    public Named removeAttr(String key) {
        _attributes.remove(key);
        return this;
    }
    
    public Set<String> getAttrKeys() {
        return _attributes.keySet();
    }
    
    public String attrToString() {
        return attrToString(_attributes.size()+1);
    }
    
    public String attrToString(int numAttr) {
        StringBuilder sb = new StringBuilder("{");
        Set<Map.Entry<String,Object>> entries = _attributes.entrySet();
        int i = 0;
        for (Map.Entry<String,Object> entry : entries) {
            i++;
            if (i > numAttr) {
                break;
            }
            if (i != 1) {
                sb.append(", ");
            }
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
        }
        sb.append('}');
        return sb.toString();
    }
    

    // ---------- Generated --------------------
    // TODO some subclasses are value objects and some are not
    
}
