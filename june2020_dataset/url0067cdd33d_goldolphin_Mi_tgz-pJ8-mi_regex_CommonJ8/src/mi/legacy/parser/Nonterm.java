package mi.legacy.parser;

/**
 * @author goldolphin
 *         2014-02-13 20:21
 */
public class Nonterm {
    public static final int ANY = -1;
    public static final Transition.NontermTransition NULL_LEFTMOST_TRANSITION =
            new Transition.NontermTransition(ANY, null);

    public final int id;
    public final String name;
    private Transition.NontermTransition leftMostTransition = NULL_LEFTMOST_TRANSITION;

    public Nonterm(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Transition.NontermTransition getLeftMostTransition() {
        return leftMostTransition;
    }

    public State addLeftMostRule(int headNontermId, State.StateGenerator generator) {
        if (leftMostTransition == NULL_LEFTMOST_TRANSITION) {
            leftMostTransition = new Transition.NontermTransition(id, generator.generate());
        }
        leftMostTransition.addHeadNonterm(headNontermId);
        return leftMostTransition.target;
    }

    @Override
    public String toString() {
        return String.format("[N: %d, %s, %s]", id, name, leftMostTransition);
    }
}
