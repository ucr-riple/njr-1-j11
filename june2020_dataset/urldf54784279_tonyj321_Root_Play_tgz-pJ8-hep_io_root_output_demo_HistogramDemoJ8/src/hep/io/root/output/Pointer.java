package hep.io.root.output;

import java.io.IOException;
import hep.io.root.output.annotations.ClassDef;

/**
 * A class which encapsulated a "pointer" within a root file. Depending on
 * how big the file is, this may be written as either a 32bit or 64 bit
 * integer.
 */
@ClassDef(hasStandardHeader = false, suppressTStreamerInfo = true)
class Pointer {
    private long value;
    private final boolean immutable;
    public static Pointer ZERO = new Pointer(0, true);

    Pointer(long value) {
        this.value = value;
        this.immutable = false;
    }

    private Pointer(long value, boolean immutable) {
        this.value = value;
        this.immutable = immutable;
    }

    void set(long value) {
        if (immutable) {
            throw new RuntimeException("Attempt to modify immutable pointer");
        }
        this.value = value;
    }

    long get() {
        return value;
    }

    private void write(RootOutput out) throws IOException {
        if (out.isLargeFile()) {
            out.writeLong(value);
        } else {
            out.writeInt((int) value);
        }
    }
    
}
