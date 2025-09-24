package tp1;

import com.sun.istack.internal.NotNull;
import tp1.visitor.QueryVisitor;
import tp1.visitor.Visitable;

public class Offset implements Visitable {
    private final int offset;

    public Offset(int offset) {
        this.offset = offset;
    }

    public void accept(@NotNull QueryVisitor visitor) {
        visitor.visit(this);
    }

    public int getOffset() {
        return offset;
    }

    public boolean isEmpty() {
        return offset == 0;
    }
}
