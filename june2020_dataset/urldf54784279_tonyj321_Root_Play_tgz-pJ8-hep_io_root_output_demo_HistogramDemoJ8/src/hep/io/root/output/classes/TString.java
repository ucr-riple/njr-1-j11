package hep.io.root.output.classes;

import java.io.IOException;
import hep.io.root.output.RootOutput;
import hep.io.root.output.annotations.ClassDef;

/**
 * Basic string class.
 *
 * @see <a href="http://root.cern.ch/root/htmldoc/TString.html">TString</a>
 * @author tonyj
 */
@ClassDef(version = 0, hasStandardHeader = false)
public class TString {
    private transient String string;
    static final TString empty = new TString("");

    public TString(String string) {
        this.string = string;
    }

    public static TString empty() {
        return empty;
    }

    private void write(RootOutput out) throws IOException {
        if (string == null) {
            out.writeByte(0);
        } else {
            byte[] chars = string.getBytes();
            int l = chars.length;
            if (l < 255) {
                out.writeByte(l);
            } else {
                out.writeByte(-1);
                out.writeInt(l);
            }
            out.write(chars);
        }
    }

    @Override
    public String toString() {
        return string;
    }
}
