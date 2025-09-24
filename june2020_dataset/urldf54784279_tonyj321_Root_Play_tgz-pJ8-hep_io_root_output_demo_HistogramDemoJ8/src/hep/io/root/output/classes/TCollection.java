package hep.io.root.output.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import hep.io.root.output.RootOutput;
import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.annotations.Title;

/**
 * Collection abstract base class.
 * @see <a href="http://root.cern.ch/root/htmldoc/TCollection.html">TCollection</a>
 * @author tonyj
 */
@ClassDef(version = 3, checkSum = -1882108578, hasStandardHeader = false)
public abstract class TCollection<A> extends TObject implements Iterable<A> {
    @Title("name of the collection")
    String name = "";
    @Title("number of elements in the collection")
    private int fSize;
    transient Collection<A> list = new ArrayList<>();

    private void write(RootOutput out) throws IOException {
    }

    public void add(A record) {
        list.add(record);
    }

    @Override
    public Iterator<A> iterator() {
        return list.iterator();
    }
    
}
