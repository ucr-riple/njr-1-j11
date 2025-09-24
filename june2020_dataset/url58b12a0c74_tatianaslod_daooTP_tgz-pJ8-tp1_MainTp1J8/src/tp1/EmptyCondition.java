package tp1;

import com.sun.istack.internal.NotNull;
import tp1.visitor.QueryVisitor;

/**
 * Created with IntelliJ IDEA.
 * User: Tatiana
 * Date: 17/05/13
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */
public class EmptyCondition extends Condition {

    @Override
    public Operator getOperator(){
        return Operator.EMPTY;
    }

    @Override
    public void accept(@NotNull QueryVisitor visitor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isEmpty(){
        return true;
    }


}
