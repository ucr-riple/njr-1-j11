package attribute;

import java.util.ArrayList;
import java.util.Iterator;


public class AttributeList<T extends Attribute> implements Iterable<T>, java.io.Serializable {
    
    public static final String NO_SUCH_ATTRIBUTE_MESSAGE = "No such attribute";
    
    private ArrayList<String> myAttributeNames;
    private ArrayList<T> myAttributeArray;
    
    public AttributeList () {
        myAttributeNames = new ArrayList<String>();
        myAttributeArray = new ArrayList<T>();
    }
    
    private class AttributeListIterator implements Iterator {

        private int index;
        ArrayList<String> names;
        ArrayList<T> attributes;
        
        public AttributeListIterator(AttributeList source) {
            index = 0;
            names = source.myAttributeNames;
            attributes = source.myAttributeArray;
        }
        
        public boolean hasNext() {
            return index < attributes.size();
        }

        public T next() {
            
            T attribute;
            
            if (hasNext()) {
                attribute = attributes.get(index);
                ++index;
            } else {
                attribute = null;
            }
            
            return attribute;
        }

        public void remove() {
            if (index >= 0 && index < attributes.size()) {
                names.remove(index);
                attributes.remove(index);
            }
        }
    };
    
    
    public void add(T playerAttribute) {
        myAttributeNames.add(playerAttribute.name());
        myAttributeArray.add(playerAttribute);
    }
    
    public void remove(T playerAttribute) {
        myAttributeNames.remove(playerAttribute.name());
        myAttributeArray.remove(playerAttribute);
    }
    
    public T get(String name) {
        try {
            return myAttributeArray.get(myAttributeNames.indexOf(name));
        } catch(Exception e) {
            return null;
        }
    }

    public boolean hasAttribute(String name) {
        for (T t: myAttributeArray) {
            if (t.name().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new AttributeListIterator(this);
    }
}
