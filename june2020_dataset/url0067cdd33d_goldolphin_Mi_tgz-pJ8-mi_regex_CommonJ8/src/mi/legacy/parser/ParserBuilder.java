package mi.legacy.parser;

import mi.common.CharHashMap;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author goldolphin
 *         2014-02-13 19:15
 */
public class ParserBuilder {

    public Parser build(Grammar grammar, boolean useHeadSet) {
        NontermTable nontermTable = new NontermTable();

        // Construct automation.
        State.StateGenerator stateGenerator = new State.StateGenerator();
        State startState = stateGenerator.generate();
        for (Grammar.Production production: grammar.getProductions()) {
            Nonterm head = nontermTable.get(production.head.name());
            State current = startState;
            int len = production.body.length;
            for (int i = 0; i < len; i ++) {
                Grammar.ISymbol symbol = production.body[i];
                if (symbol instanceof Grammar.Nonterm) {
                    Nonterm n = nontermTable.get(symbol.name());
                    if (i == 0) {
                        // For left-most nonterm situation
                        current = n.addLeftMostRule(head.id, stateGenerator);
                    } else {
                        current = current.addNontermTransition(n.id, head.id, stateGenerator);
                    }
                } else if (symbol instanceof Grammar.Term) {
                    current = current.addTermTransition(symbol.name(), head.id, stateGenerator);
                } else {
                    throw new ParseException("Unknown symbol type: " + symbol.getClass());
                }
            }

            // Set accepting state
            current.setAcceptedNonterm(head.id);
        }

        // Extend transitions' head set.
        if (useHeadSet) {
            extendTransitionHeadSet(stateGenerator, nontermTable);
        }

        // Top head
        Grammar.Nonterm topHead = grammar.getTopHead();
        int topHeadId = topHead == null ? Nonterm.ANY : nontermTable.get(topHead.name()).id;

        // Build parser.
        Nonterm[] table = new Nonterm[nontermTable.size()];
        for (Nonterm n: nontermTable.getAll()) {
            table[n.id] = n;
        }

        return new Parser(table, startState, topHeadId, useHeadSet);
    }

    private static void dump(State.StateGenerator stateGenerator, NontermTable nontermTable) {
        for (Nonterm n: nontermTable.getAll()) {
            System.out.println(n);
        }
        System.out.println();
        for (State s: stateGenerator.getAll()) {
            System.out.println(s);
        }
    }

    private static void extendTransitionHeadSet(State.StateGenerator stateGenerator, NontermTable nontermTable) {
        // Build leftmost head graph
        NontermGraph[] graph = new NontermGraph[nontermTable.size()];
        for (Nonterm n: nontermTable.getAll()) {
            graph[n.id] = new NontermGraph(toArray(n.getLeftMostTransition().getHeadNontermIds()));
        }

        // extend transition head set.
        for (State s: stateGenerator.getAll()) {
            for (Transition t: s.getNontermTransitions()) {
                extendHeads(t, graph);
            }

            for (CharHashMap.Entry<Transition.TermTransition> e: s.getTermTransitions()) {
                extendHeads(e.getValue(), graph);
            }
        }

        for (Nonterm n: nontermTable.getAll()) {
            extendHeads(n.getLeftMostTransition(), graph);
        }
    }

    private static int[] toArray(Collection<Integer> set) {
        int[] a = new int[set.size()];
        int i = 0;
        for (int e: set) {
            a[i ++] = e;
        }
        return a;
    }

    private static void extendHeads(Transition transition, NontermGraph[] graph) {
        int[] heads = toArray(transition.getHeadNontermIds());
        for (int h: heads) {
            extendHeads(transition, graph, h);
        }
    }

    private static void extendHeads(Transition transition, NontermGraph[] graph, int current) {
        for (int h: graph[current].heads) {
            if (!transition.containsHeadNonterm(h)) {
                transition.addHeadNonterm(h);
                extendHeads(transition, graph, h);
            }
        }
    }


    private static class NontermTable {
        private HashMap<String, Nonterm> map = new HashMap<>();

        public Nonterm get(String name) {
            Nonterm n = map.get(name);
            if (n == null) {
                n = new Nonterm(map.size(), name);
                map.put(name, n);
            }
            return n;
        }

        public Collection<Nonterm> getAll() {
            return map.values();
        }

        public int size() {
            return map.size();
        }
    }

    private static class NontermGraph {
        private final int[] heads;

        private NontermGraph(int[] heads) {
            this.heads = heads;
        }
    }

}
