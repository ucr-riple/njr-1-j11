package hep.io.root.output;

import hep.io.root.output.annotations.ClassDef;

/**
 *
 * @author tonyj
 */
@ClassDef(version=3)
class TStreamerBase extends TStreamerElement {

    private int fBaseVersion;

    TStreamerBase(StreamerClassInfo info) {
        super(info);
        fBaseVersion = info.getVersion();
    }
}
