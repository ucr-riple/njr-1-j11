package tp1;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import tp1.visitor.QueryVisitor;
import tp1.visitor.Visitable;

public class Const<T> extends Value<T>{
    @NotNull private final T str;

    private Const(@NotNull T str) {
        this.str = str;
    }

    public static Const<String> cons(@NotNull String str) {
        return new Const<String>(str);
    }

    public static Const<Integer> cons(@NotNull Integer integer) {
        return new Const<Integer>(integer);
    }

    @Override
    public void accept(@NotNull QueryVisitor visitor) {
        visitor.visit(this);
    }

    public T getValue(){
        return str;
    }

}
