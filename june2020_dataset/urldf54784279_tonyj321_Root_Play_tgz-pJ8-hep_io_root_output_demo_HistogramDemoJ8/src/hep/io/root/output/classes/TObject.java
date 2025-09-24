package hep.io.root.output.classes;

import java.io.IOException;
import hep.io.root.output.RootOutput;
import hep.io.root.output.Type;
import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.annotations.FieldType;
import hep.io.root.output.annotations.Title;

/**
 * Mother of all ROOT objects.
 * @see <a href="http://root.cern.ch/root/htmldoc/TObject.html">TObject</a>
 * @author tonyj
 */
@ClassDef(version = 1, checkSum = 1389979441, hasStandardHeader = false)
@Title("Basic ROOT object")
public class TObject {
    
    @Title("object unique identifier")
    @FieldType(Type.kUInt)
    private final int fUniqueID = 0;
    @Title("bit field status word")
    @FieldType(Type.kUInt)
    private int fBits = 0x03000000;

    private void write(RootOutput out) throws IOException {
        out.writeShort(TObject.class.getAnnotation(ClassDef.class).version());
        out.writeInt(fUniqueID);
        out.writeInt(fBits);
    }
    
}
