package hep.io.root.output.classes;

import java.io.IOException;
import java.util.UUID;
import hep.io.root.output.RootOutput;
import hep.io.root.output.annotations.ClassDef;

/**
 * A root universal unique identifier written into the TFile header, and
 * each TDirectory within the file. This implementation uses the java
 * built-in UUID support which may or may not be strictly compatible with
 * Root's expected definition of the UUID.
 * @see <a href="http://root.cern.ch/root/htmldoc/TUUID.html">TUUID</a>
 */
@ClassDef(version = 1, hasStandardHeader = false, suppressTStreamerInfo = true)
public class TUUID {
    private UUID uuid;
    
    public TUUID() {
        this(null);
    }
    
    public TUUID(UUID uuid) {
        this.uuid = uuid == null ? UUID.randomUUID() : uuid;
    }
    
    private void write(RootOutput out) throws IOException {
        out.writeShort(TUUID.class.getAnnotation(ClassDef.class).version());
        out.writeLong(uuid.getMostSignificantBits());
        out.writeLong(uuid.getLeastSignificantBits());
    }
}
