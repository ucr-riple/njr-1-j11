package hep.io.root.output;

import java.util.HashMap;
import java.util.Map;
import hep.io.root.output.classes.TArrayD;
import hep.io.root.output.classes.TNamed;
import hep.io.root.output.classes.TObject;
import hep.io.root.output.classes.TString;

/**
 * Used to mark the type of fields within a Object.
 *
 * @see hep.io.root.output.annotations.FieldType
 * @author tonyj
 */
public enum Type {

    kBase(0, 0, "BASE"),
    kCounter(6, 4, "Int_t"),
    kCharStar(7),
    kChar(1),
    kShort(2, 2, "short"),
    kInt(3, 4, "Int_t"),
    kEnum(3, 4, null),
    kLong(4, 8, "Long_t"),
    kFloat(5, 4, "Float_t"),
    kDouble(8, 8, "Double_t"),
    kDouble32(9),
    kLegacyChar(10), // Equal to TDataType's kchar
    kUChar(11),
    kUShort(12, 2, "UShort_t"),
    kUInt(13, 4, "UInt_t"),
    kULong(14, 8, "ULong_t"),
    kBits(15),
    kLong64(16),
    kULong64(17),
    kBool(18, 1, "Bool_t"),
    kFloat16(19),
    kCharArray(40 + 1),
    kShortArray(40 + 2),
    kIntArray(40 + 3),
    kLongArray(40 + 4),
    kFloatArray(40 + 5),
    kDoubleArray(40 + 8),
    kDouble32Array(40 + 9),
    kObject(61),
    kAny(62),
    kObjectp(63),
    kObjectP(64),
    kTString(65, 8, "TString"),
    kTObject(66, 0, "TObject"),
    kTNamed(67, 0, "TNamed");
    private int value;
    private static Type[] types;
    private int size;
    private String typeName;
    static final Map<Class, Type> javaToRoot = new HashMap<>();

    static {
        javaToRoot.put(Integer.TYPE, Type.kInt);
        javaToRoot.put(Short.TYPE, Type.kShort);
        javaToRoot.put(Float.TYPE, Type.kFloat);
        javaToRoot.put(Double.TYPE, Type.kDouble);
        javaToRoot.put(Boolean.TYPE, Type.kBool);
        javaToRoot.put(TString.class, Type.kTString);
        javaToRoot.put(TNamed.class, Type.kTNamed);
        javaToRoot.put(TObject.class, Type.kTObject);
        javaToRoot.put(TArrayD.class, Type.kAny);
        javaToRoot.put(String.class, Type.kTString);
    }

    Type(int v) {
        this(v, 0, null);
    }

    Type(int v, int size, String typeName) {
        this.value = v;
        this.size = size;
        this.typeName = typeName;
    }

    int getValue() {
        return value;
    }

    private static Type[] types() {
        if (types == null) {
            types = new Type[100];
            for (Type t : values()) {
                if (t.getValue() >= 0) {
                    types[t.getValue()] = t;
                }
            }
        }
        return types;
    }

    Type arrayType() {
        return types()[value + 40];
    }

    String getName() {
        return typeName;
    }

    int getSize() {
        return size;
    }

    static Type forClass(Class c) {
        if (c.isEnum()) {
            return Type.kEnum;
        } else {
            Type type = javaToRoot.get(c);
            return type == null ? Type.kAny : type;
        }
    }
}
