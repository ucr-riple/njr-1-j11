/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.littlemancommands.LittleManCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import littlemangame.littleman.GenericLittleMan;

/**
 * a little man action that represents a sequence of little man action. The
 * little man can do at most one action at once. This class has state to
 * remember which action in the sequence the little man is on.
 *
 * @author brian
 */
public final class LittleManActionSequence extends LittleManAction {

    private final List<LittleManAction> littleManActions;
    private final int numActions;
    private int currentAction = 0;

    /**
     * constructs a sequence which corresponds to the given list of little man
     * actions
     *
     * @param littleManActions the list of actions to this object is to
     * represent
     */
    public LittleManActionSequence(List<LittleManAction> littleManActions) {
        this.littleManActions = new ArrayList<>(littleManActions);
        numActions = littleManActions.size();
    }

    /**
     * constructs an action sequence which corresponds to the given little man
     * actions in the order they were given.
     *
     * @param littleManActions a sequence of parameters that this action
     * sequence is to correspond to
     */
    public LittleManActionSequence(LittleManAction... littleManActions) {
        this(Arrays.asList(littleManActions));
    }

    @Override
    /**
     * makes the given little man perform the next action in the sequence of
     * little man actions. If the little man completes the final action in the
     * list, this action sequence is reset so that the next action to be
     * completed is the first action in the sequence.
     *
     * @param littleMan the little man which is to complete this action's
     *
     * @return whether the action sequence was successfully completed
     */
    public boolean doAction(GenericLittleMan<?> littleMan) {
        boolean isComplete = littleManActions.get(currentAction).doAction(littleMan);
        if (isComplete) {
            incrementAction();
            return currentAction == 0;
        } else {
            return false;
        }
    }

    private void incrementAction() {
        currentAction++;
        currentAction %= numActions;
    }

    @Override
    /**
     * generates a copy of this little man action sequence with the sequence
     * reset so that the next action to be performed is the first action in the
     * sequence. Additionally, each action in the sequence is reset, so for
     * example, a sequence of sequences would be correctly reset.
     *
     * @return a reset copy of this action sequence.
     */
    public LittleManAction getResetCopy() {
        List<LittleManAction> littleManActionsCopy = new ArrayList<>(numActions);
        for (LittleManAction littleManAction : littleManActions) {
            littleManActionsCopy.add(littleManAction.getResetCopy());
        }
        return new LittleManActionSequence(littleManActionsCopy);
    }

}
