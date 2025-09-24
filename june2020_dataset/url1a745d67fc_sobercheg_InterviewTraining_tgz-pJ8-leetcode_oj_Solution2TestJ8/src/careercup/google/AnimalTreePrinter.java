package careercup.google;

import java.util.*;

/**
 * Created by Sobercheg on 10/20/13.
 * <p/>
 * <a href="http://www.careercup.com/question?id=5074387359236096"/>
 * <p/>
 * Q: Complete the below function which takes an arraylist and displays the list in the expected output
 * <p/>
 * public class TreePrinter {
 * public static void printTree(Iterable<Relation> rs) {
 * // your code
 * <p/>
 * }
 * }
 */
public class AnimalTreePrinter {
    public static void printTree(Iterable<Relation> rs) {
        Map<String, List<String>> relationMap = new HashMap<String, List<String>>();
        Set<String> allChildren = new HashSet<String>();

        for (Relation relation : rs) {
            if (!relationMap.containsKey(relation.parent)) {
                relationMap.put(relation.parent, new ArrayList<String>());
            }
            relationMap.get(relation.parent).add(relation.child);
            allChildren.add(relation.child);
        }

        Set<String> diffSet = new HashSet<String>(relationMap.keySet());
        diffSet.removeAll(allChildren);
        String root = diffSet.iterator().next();

        dfsPreorder(root, relationMap, 1);
    }

    private static int dfsPreorder(String root, Map<String, List<String>> map, int line) {
        System.out.println("line" + line + ": " + root);
        int newLine = line;
        if (!map.containsKey(root)) return newLine;
        int counter = 1;
        for (String child : map.get(root)) {
            newLine += dfsPreorder(child, map, line + counter);
            counter++;
        }
        return newLine;
    }

    public static void main(String[] args) {
        List<Relation> input = new ArrayList<Relation>();

        input.add(new Relation("animal", "mammal"));
        input.add(new Relation("animal", "bird"));
        input.add(new Relation("lifeform", "animal"));
        input.add(new Relation("cat", "lion"));
        input.add(new Relation("mammal", "cat"));
        input.add(new Relation("animal", "fish"));

        printTree(input);
    }

    public static class Relation {
        String parent;
        String child;

        public Relation(String parent, String child) {
            this.parent = parent;
            this.child = child;
        }
    }
}


