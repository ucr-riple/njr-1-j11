package tp1;


import com.sun.istack.internal.NotNull;
import tp1.visitor.QueryVisitor;
import tp1.visitor.Visitable;

public abstract class Statement<T> implements Visitable {
    public abstract void accept(@NotNull QueryVisitor visitor);
}
