package hep.io.root.output;

import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.annotations.Title;

/**
 *
 * @author tonyj
 */
@ClassDef(version=2)
class TStreamerBasicPointer extends TStreamerElement {

    @Title("version number of the class with the counter")
    private int      fCountVersion;
    @Title("name of data member holding the array count")
    private String  fCountName;
    @Title("name of the class with the counter")
    private String  fCountClass;
   
    TStreamerBasicPointer(StreamerFieldInfo field) {
        super(field);
        fCountName = field.getCountName();
        fCountClass = field.getCountClass();
        fCountVersion = field.getCountVersion();
    }
}
