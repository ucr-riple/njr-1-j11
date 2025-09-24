package hep.io.root.output;

import java.io.IOException;
import java.lang.reflect.Field;
import hep.io.root.output.annotations.Counter;
import hep.io.root.output.annotations.FieldType;
import hep.io.root.output.annotations.Super;
import hep.io.root.output.annotations.Title;

/**
 * Summarizes the information known about a field. This information comes from
 * reflection and annotations.
 *
 * @author tonyj
 */
class StreamerFieldInfo {

    private final StreamerClassInfo parentClassInfo;
    private final Field field;
    private final String title;
    private final boolean isBase;
    private final boolean isArray;
    private final String counter;
    private final Class fClass;
    private final StreamerClassInfo fieldClassInfo;
    private Type type;

    StreamerFieldInfo(StreamerClassInfo c, Field f) throws StreamerInfoException {
        this.parentClassInfo = c;
        this.field = f;
        Class tClass = f.getType();
        this.isArray = tClass.isArray();
        this.fClass = isArray ? tClass.getComponentType() : tClass;
        this.fieldClassInfo = new StreamerClassInfo(fClass);
        Title titleAnnotation = f.getAnnotation(Title.class);
        title = titleAnnotation == null ? "" : titleAnnotation.value();
        isBase = f.isAnnotationPresent(Super.class);
        Counter counterAnnotation = f.getAnnotation(Counter.class);
        counter = counterAnnotation == null ? null : counterAnnotation.value();
        if (counterAnnotation != null) {
            if (!isArray) {
                throw new StreamerInfoException("Field with counter is not an array");
            }
            StreamerFieldInfo cInfo = c.findField(counter);
            if (cInfo == null || cInfo.getType() != Type.kInt) {
                throw new StreamerInfoException("Reference to non-existent or non-integer element " + counter);
            }
            cInfo.type = Type.kCounter;
        }
        FieldType typeAnnotation = f.getAnnotation(FieldType.class);
        Type explicitType = typeAnnotation == null ? null : typeAnnotation.value();
        if (explicitType == null) {
            type = Type.forClass(fClass);
            if (isArray) {
                type = type.arrayType();
            }
        } else {
            type = explicitType;
        }
        field.setAccessible(true);
    }

    boolean isBasicType() {
        return type.getValue() < 20;
    }

    boolean isArray() {
        return isArray;
    }

    public boolean isSuper() {
        return isBase;
    }

    String getName() {
        return field.getName();
    }

    String getTypeName() {
        String name = type.getName() == null ? fieldClassInfo.getName() : type.getName();
        if (isArray() || type == Type.kObjectp || type == Type.kObjectP) {
            name += "*";
        }
        return name;
    }

    int getArrayDim() {
        return 0;
    }

    int getMaxIndex(int i) {
        return 0;
    }

    String getTitle() {
        return title;
    }

    Type getType() {
        return type;
    }

    StreamerClassInfo getAsSuperClass() {
        return fieldClassInfo;
    }

    String getCountName() {
        return counter;
    }

    String getCountClass() {
        return parentClassInfo.getName();
    }

    int getCountVersion() {
        return parentClassInfo.getVersion();
    }

    boolean isBasicPointer() {
        return counter != null;
    }

    int getSize() {
        return type.getSize();
    }

    /**
     * Write a single field of an object to the output stream using the
     * information in the streamer element.
     *
     * @param out
     * @param object
     */
    void write(RootOutput out, Object object) throws IOException {
        try {
            switch (type) {
                case kInt:
                    out.writeInt(field.getInt(object));
                    break;
                case kShort:
                case kUShort:
                    out.writeShort(field.getShort(object));
                    break;
                case kFloat:
                    out.writeFloat(field.getFloat(object));
                    break;
                case kDouble:
                    out.writeDouble(field.getDouble(object));
                    break;
                case kBool:
                    out.writeByte(field.getBoolean(object) ? 1 : 0);
                    break;
                case kEnum:
                    out.writeInt(((Enum) field.get(object)).ordinal());
                    break;
                case kAny:
                case kTString:
                    out.writeObject(field.get(object));
                    break;
                case kObjectP:
                case kObjectp:
                    out.writeObjectRef(field.get(object));
                    break;
                default:
                    throw new IOException("Unable to handle field " + field.getName() + " of type " + type);
            }
        } catch (IllegalArgumentException | IllegalAccessException x) {
            throw new IOException("Unable to stream field: " + field.getName(), x);
        }
    }
}
