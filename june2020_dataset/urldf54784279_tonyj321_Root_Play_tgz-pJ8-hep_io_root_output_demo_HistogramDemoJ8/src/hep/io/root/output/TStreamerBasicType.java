package hep.io.root.output;

import hep.io.root.output.annotations.ClassDef;

/**
 *
 * @author tonyj
 */
@ClassDef(version = 2)
class TStreamerBasicType extends TStreamerElement {

    TStreamerBasicType(StreamerFieldInfo field) {
        super(field);
    }
}
