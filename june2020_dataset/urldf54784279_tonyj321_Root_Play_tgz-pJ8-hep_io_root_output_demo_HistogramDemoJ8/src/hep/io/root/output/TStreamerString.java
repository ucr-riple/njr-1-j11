package hep.io.root.output;

import hep.io.root.output.annotations.ClassDef;

/**
 *
 * @author tonyj
 */
@ClassDef(version=2)
class TStreamerString extends TStreamerElement {
    
    TStreamerString(StreamerFieldInfo field) {
        super(field);
    }

}
