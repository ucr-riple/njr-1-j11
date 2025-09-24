package hep.io.root.output.classes;

import java.io.IOException;
import hep.io.root.output.RootOutput;
import hep.io.root.output.annotations.ClassDef;

/**
 * An array of TObjects.
 * @see <a href="http://root.cern.ch/root/htmldoc/TObjArray.html">TObjArray</a>
 * @author tonyj
 */
@ClassDef(version = 3)
public class TObjArray<A> extends TSeqCollection<A> {
    private int fLowerBound = 0;

    private void write(RootOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(list.size());
        out.writeInt(fLowerBound);
        for (Object o : list) {
            out.writeObjectRef(o);
        }
    }
    
}
