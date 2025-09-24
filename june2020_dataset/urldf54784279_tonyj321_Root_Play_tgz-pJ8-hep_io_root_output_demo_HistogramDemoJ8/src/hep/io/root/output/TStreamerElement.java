package hep.io.root.output;

import java.io.IOException;
import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.annotations.Title;
import hep.io.root.output.classes.TNamed;

@ClassDef(version = 4)
abstract class TStreamerElement extends TNamed {
    
    @Title("element type")
    int fType;
    @Title("sizeof element")
    int fSize;
    @Title("cumulative size of all array dims")
    private int fArrayLength = 0;
    @Title("number of array dimensions")
    private int fArrayDim = 0;
    @Title("Maximum array index for array dimension")
    private int[] fMaxIndex = new int[5];
    @Title("Data type name of data member")
    String fTypeName;

    TStreamerElement(StreamerFieldInfo field) {
        super(field.getName(), field.getTitle());
        this.fType = field.getType().getValue();
        this.fSize = field.getSize();
        this.fTypeName = field.getTypeName();
    }

    TStreamerElement(StreamerClassInfo superClass) {
        super(superClass.getName(), superClass.getTitle());
        this.fType = superClass.getType().getValue();
        this.fSize = superClass.getSize();
        this.fTypeName = superClass.getTypeName();
    }
    static TStreamerElement create(StreamerClassInfo c) {
        return new TStreamerBase(c);
    }
    /**
     * Factory for making TStreamerElement subclasses.
     * @param info The info from which to create the subclasses
     * @return The newly minted object.
     */
    static TStreamerElement create(StreamerFieldInfo info) {
        if (info.isSuper()) {
            return new TStreamerBase(info.getAsSuperClass());
        } else if (info.isBasicPointer()) {
            return new TStreamerBasicPointer(info);
        } else if (info.isBasicType()) {
            return new TStreamerBasicType(info);
        }
        switch (info.getType()) {
            case kTString:
                return new TStreamerString(info);
            case kObjectP:
            case kObjectp:
                return new TStreamerObjectPointer(info);
            case kAny:
                return new TStreamerObjectAny(info);
            default:
                return new TStreamerObject(info);
        }
    }

    private void write(RootOutput out) throws IOException {
        out.writeInt(fType);
        out.writeInt(fSize);
        out.writeInt(fArrayLength);
        out.writeInt(fArrayDim);
        for (int i : fMaxIndex) {
            out.writeInt(i);
        }
        out.writeObject(fTypeName);
    }
}
