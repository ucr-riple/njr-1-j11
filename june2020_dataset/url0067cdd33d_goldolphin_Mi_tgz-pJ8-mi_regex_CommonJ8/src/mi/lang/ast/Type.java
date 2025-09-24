package mi.lang.ast;

/**
 * User: goldolphin
 * Time: 2013-08-08 02:48
 */
public class Type {
    private final Type inType;
    private final Type outType;

    public Type(Type inType, Type outType) {
        this.inType = inType;
        this.outType = outType;
    }

    public static final Type Byte = new Type(null, null);
    public static final Type Integer = new Type(null, null);
    public static final Type Float = new Type(null, null);
    public static final Type Double = new Type(null, null);
    public static final Type Char = new Type(null, null);
}
