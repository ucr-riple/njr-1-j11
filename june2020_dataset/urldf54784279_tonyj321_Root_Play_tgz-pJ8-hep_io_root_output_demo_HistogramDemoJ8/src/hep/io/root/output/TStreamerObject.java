package hep.io.root.output;

import hep.io.root.output.annotations.ClassDef;

/**
 *
 * @author tonyj
 */
@ClassDef(version = 2)
class TStreamerObject extends TStreamerElement {

    TStreamerObject(StreamerFieldInfo field) {
        super(field);
    }

}
