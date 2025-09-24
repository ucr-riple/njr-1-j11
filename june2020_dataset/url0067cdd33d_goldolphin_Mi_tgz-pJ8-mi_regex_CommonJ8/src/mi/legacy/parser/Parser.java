package mi.legacy.parser;

import mi.stream.ICharStream;

import java.util.ArrayList;

/**
 * @author goldolphin
 *         2014-02-13 20:30
 */
public class Parser {
    private final Nonterm[] nontermTable;
    private final State startState;
    private final int topHeadId;
    private final boolean useHeadSet;

    Parser(Nonterm[] nontermTable, State startState, int topHeadId, boolean useHeadSet) {
        this.nontermTable = nontermTable;
        this.startState = startState;
        this.topHeadId = topHeadId;
        this.useHeadSet = useHeadSet;
    }

    public boolean parse(ICharStream stream) {
        SubParsers subParsers = new SubParsers();
        SubParsers backup = new SubParsers();
        SubParsers toConsume = new SubParsers();
        SubParsers success = new SubParsers();

        subParsers.add(startState, GraphStack.<Transition.NontermTransition>newBottom(
                new Transition.NontermTransition(topHeadId, null)));

        while (true) {
            if (subParsers.size() == 0) {
                System.out.format("subParsers = %d, toConsume = %d, success = %d\n", subParsers.size(), toConsume.size(), success.size());

                // Transitions need consuming input
                if (toConsume.size() == 0) {
                    break;
                }

                System.out.println(stream.poll());

                // Swap subParsers & toConsume
                SubParsers temp = subParsers;
                subParsers = toConsume;
                toConsume = temp;
            }

            backup.clear();
            char c = stream.peek();

            int num = subParsers.size();
            for (int i = 0; i < num; i++) {
                State state = subParsers.getState(i);
                GraphStack<Transition.NontermTransition> stack = subParsers.getStack(i);
                Transition.NontermTransition stackTop = stack.getData();
                int head = useHeadSet ? stackTop.nontermId : Nonterm.ANY;

                // Nonterm transitions
                for (Transition.NontermTransition transition: state.getNontermTransitions()) {
                    if (transition.containsHeadNonterm(head)) {
                        backup.add(startState, stack.push(transition));
                    }
                }

                // Accepting state
                if (state.hasAcceptedNonterm()) {
                    Nonterm accepted = nontermTable[state.getAcceptedNontermId()];
                    // Accepting transition
                    if (stackTop.nontermId == Nonterm.ANY || stackTop.nontermId == accepted.id) {
                        if (stackTop.target == null) {
                            if (c == stream.EOF) {
                                success.add(stackTop.target, stack.pop());
                            }
                        } else {
                            //System.out.format("[RPOP] %s @ %s\n", accepted.name, nontermTable[stackTop.nontermId].name);
                            backup.add(stackTop.target, stack.pop());
                        }
                    }

                    // Leftmost transitions
                    Transition.NontermTransition leftmostTransition = accepted.getLeftMostTransition();
                    if (leftmostTransition.containsHeadNonterm(head)) {
                        //System.out.println(leftmostTransition.getHeadNontermIds());
                        //System.out.format("[LPOP] %s @ %s\n", accepted.name, nontermTable[stackTop.nontermId].name);
                        backup.add(leftmostTransition.target, stack);
                    }
                }

                // Transitions need consuming input
                State next = state.getTermTransition(c, head);
                if (next != null) {
                    // Add to toConsume
                    toConsume.add(next, stack);
                }
            }

            // Swap subParsers & backup
            SubParsers temp = subParsers;
            subParsers = backup;
            backup = temp;
        }

        return success.size() > 0;
    }

    private static class SubParsers {
        private ArrayList<State> states = new ArrayList<>();
        private ArrayList<GraphStack<Transition.NontermTransition>> stacks = new ArrayList<>();

        public void add(State state, GraphStack<Transition.NontermTransition> stack) {
            states.add(state);
            stacks.add(stack);
        }

        public State getState(int i) {
            return states.get(i);
        }

        public GraphStack<Transition.NontermTransition> getStack(int i) {
            return stacks.get(i);
        }

        public int size() {
            return states.size();
        }

        public void clear() {
            states.clear();
            stacks.clear();
        }
    }
}
