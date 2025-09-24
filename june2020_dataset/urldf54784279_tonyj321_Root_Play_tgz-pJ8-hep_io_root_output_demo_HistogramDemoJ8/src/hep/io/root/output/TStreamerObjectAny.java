package hep.io.root.output;

import hep.io.root.output.annotations.ClassDef;

/**
 *
 * @author tonyj
 */
@ClassDef(version = 2)
class TStreamerObjectAny extends TStreamerElement {

    TStreamerObjectAny(StreamerFieldInfo field) {
        super(field);
    }
}
