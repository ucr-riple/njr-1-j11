package hep.io.root.output;

import hep.io.root.output.annotations.ClassDef;

/**
 *
 * @author tonyj
 */
@ClassDef(version=2)
class TStreamerObjectPointer extends TStreamerElement {
    
    TStreamerObjectPointer(StreamerFieldInfo field) {
        super(field);
    }
}
