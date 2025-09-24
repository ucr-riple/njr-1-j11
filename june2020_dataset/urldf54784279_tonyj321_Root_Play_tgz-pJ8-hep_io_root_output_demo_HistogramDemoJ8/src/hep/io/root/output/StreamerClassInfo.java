package hep.io.root.output;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.annotations.Title;

/**
 * Summarizes all the information known about a class to be used for streaming.
 * Information is gathered using reflection and annotations.
 *
 * @author tonyj
 */
class StreamerClassInfo {

    private final Class javaClass;
    private final ClassDef classDef;
    private final String title;
    private int checkSum;
    private Map<String, StreamerFieldInfo> fields = new LinkedHashMap<>();
    private StreamerClassInfo superClass;
    private final Type type;
    private boolean depenciesResolved;

    StreamerClassInfo(Class c) {
        this.javaClass = c;
        this.classDef = (ClassDef) c.getAnnotation(ClassDef.class);
        if (classDef != null) {
            checkSum = classDef.checkSum();
        }
        Title titleAnnotation = (Title) c.getAnnotation(Title.class);
        title = titleAnnotation == null ? "" : titleAnnotation.value();
        type = Type.forClass(c);
    }

    void resolveDependencies() throws StreamerInfoException {

        if (!depenciesResolved) {
            depenciesResolved = true;

            Class s = javaClass.getSuperclass();
            if (s != Object.class) {
                superClass = new StreamerClassInfo(s);
            }

            for (Field f : javaClass.getDeclaredFields()) {
                try {
                    if ((f.getModifiers() & (Modifier.TRANSIENT | Modifier.STATIC)) == 0) {
                        StreamerFieldInfo fieldInfo = new StreamerFieldInfo(this, f);
                        addField(fieldInfo);
                    }
                } catch (StreamerInfoException x) {
                    x.setField(javaClass.getName(), f.getName());
                    throw x;
                }
            }
        }
    }

    public String getName() {
        String className = javaClass.getSimpleName();
        if (classDef != null) {
            String tmpName = classDef.className();
            if (tmpName.length() > 0) {
                className = tmpName;
            }
        }
        return className;
    }

    public int getVersion() {
        return classDef == null ? 0 : classDef.version();
    }

    public boolean hasStandardHeader() {
        return classDef == null ? true : classDef.hasStandardHeader();
    }

    public String getTitle() {
        return title;
    }

    boolean suppressStreamerInfo() {
        return classDef == null ? false : classDef.suppressTStreamerInfo();
    }

    int getCheckSum() {
        if (checkSum == 0) {
            checkSum = computeCheckSum();
        }
        return checkSum;
    }

    Collection<StreamerFieldInfo> getFields() {
        return fields.values();
    }

    StreamerFieldInfo findField(String name) {
        return fields.get(name);
    }

    void addField(StreamerFieldInfo info) {
        fields.put(info.getName(), info);
    }

    /**
     * Based on: http://root.cern.ch/root/html/src/TStreamerInfo.cxx.html#erZjI
     */
    private int computeCheckSum() {
        Checksum ck = new Checksum();
        ck.compute(getName());
        if (superClass != null) {
            ck.compute(superClass.getName());
        }
        for (StreamerFieldInfo f : fields.values()) {
            if (f.isSuper()) {
                ck.compute(f.getName());
            }
        }
        for (StreamerFieldInfo f : fields.values()) {
            if (!f.isSuper()) {
                ck.compute(f.getName());
                ck.compute(f.getTypeName());
                int dim = f.getArrayDim();
                for (int i = 0; i < dim; i++) {
                    ck.compute(f.getMaxIndex(i));
                }
            }
        }
        return ck.getValue();
    }

    Type getType() {
        return type == Type.kAny ? Type.kBase : type;
    }

    int getSize() {
        return type.getSize();
    }

    String getTypeName() {
        return "BASE";
    }

    StreamerClassInfo getSuperClass() {
        return superClass;
    }

    /**
     * Writes the given object to the output stream using the information in the
     * streamer info.
     *
     * @param out The output stream
     * @param object The object to write
     */
    void write(RootOutput out, Object object) throws IOException, StreamerInfoException {
        resolveDependencies();
        for (StreamerFieldInfo fieldInfo : fields.values()) {
            fieldInfo.write(out, object);
        }
    }
}
