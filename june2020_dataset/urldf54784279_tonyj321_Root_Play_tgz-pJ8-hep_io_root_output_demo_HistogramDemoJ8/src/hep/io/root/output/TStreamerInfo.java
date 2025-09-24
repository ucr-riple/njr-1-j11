package hep.io.root.output;

import java.io.IOException;
import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.classes.TNamed;
import hep.io.root.output.classes.TObjArray;

/**
 *
 * @author tonyj
 */
@ClassDef(version = 9)
class TStreamerInfo extends TNamed {

    private int fClassVersion;
    private int fCheckSum;
    private TObjArray<TStreamerElement> fElements = new TObjArray<>();

    TStreamerInfo(StreamerClassInfo info) {
        super(info.getName(), info.getTitle());
        fClassVersion = info.getVersion();
        fCheckSum = info.getCheckSum();
        if (info.getSuperClass()!=null) {
            fElements.add(TStreamerElement.create(info.getSuperClass()));
        }
        for (StreamerFieldInfo sfi : info.getFields()) {
            fElements.add(TStreamerElement.create(sfi));
        }
    }

    private void write(RootOutput out) throws IOException {
        out.writeInt(fCheckSum);
        out.writeInt(fClassVersion);
        out.writeObjectRef(fElements);
    }
}
