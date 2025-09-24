package hep.io.root.output.classes;

import java.io.IOException;
import java.util.Collection;
import hep.io.root.output.RootOutput;
import hep.io.root.output.annotations.ClassDef;

/**
 * A doubly linked list.
 * @see <a href="http://root.cern.ch/root/htmldoc/TList.html">TList</a>
 * @author tonyj
 */
@ClassDef(version = 5)
public class TList<A> extends TSeqCollection<A> {

    /**
     * Creates a TList backed by a java.util.Collection;
     * @param list The list to use as a backing list
     */
    public TList(Collection<A> list) {
        this.list = list;
    }
    /**
     * Creates an empty TList
     */
    public TList() {
        
    }

    private void write(RootOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(list.size());
        for (Object o : list) {
            out.writeObjectRef(o);
            out.writeByte(0);
        }
    }
    
}
