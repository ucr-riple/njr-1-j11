package mi.match;

import mi.common.CharHashMap;
import mi.common.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * User: goldolphin
 * Time: 2013-06-12 17:25
 */
public class ACSearcher {
    private Node root;

    public ACSearcher(Collection<String> patterns) {
        root = new Node();
        for (String pattern: patterns) {
            add(root, pattern, 0);
        }
        buildFails(root, root);
    }

    public void search(String source, IMatchHandler handler) {
        int sourceLen = source.length();
        int pos = 0;
        Node current = root;
        while (true) {
            if (current.pattern != null) {
                handler.handle(pos, current.pattern);
            }
            if (pos == sourceLen) {
                if (current == root) {
                    break;
                }
                current = current.fail;
                continue;
            }
            char c = source.charAt(pos);
            Node child = current.children.get(c);
            if (child != null) {
                current = child;
                pos ++;
            } else if (current == root) {
                pos ++;
            } else {
                current = current.fail;
            }
        }
    }

    private static void add(Node current, String pattern, int i) {
        int patternLen = pattern.length();
        if (i == patternLen) {
            current.pattern = pattern;
            return;
        }

        char c = pattern.charAt(i);
        Node child = current.children.get(c);
        if (child == null) {
            child = new Node();
            current.children.put(c, child);
        }
        add(child, pattern, i+1);
    }

    private static void buildFails(Node current, Node root) {
        for (CharHashMap.Entry<Node> entry: current.children) {
            Node child = entry.getValue();
            for (Node fail = current.fail;;) {
                if (fail == null) {
                    child.fail = root;
                    break;
                }
                Node f = fail.children.get(entry.getKey());
                if (f != null) {
                    child.fail = f;
                    break;
                } else {
                    fail = fail.fail;
                }
            }
            buildFails(child, root);
        }
    }

    private static void dumpTree(Node parent, int indent) {
        for (CharHashMap.Entry<Node> entry: parent.children) {
            for (int i = 0; i < indent; i++) {
                System.out.print(' ');
            }
            Node child = entry.getValue();
            System.out.println(child + ": " + entry.getKey() + ", " + child.fail);
            dumpTree(child, indent+2);
        }
    }

    private static class Node {
        private String pattern = null;
        private CharHashMap<Node> children = new CharHashMap<>();
        private Node fail = null;
    }

    public static void main(String[] args) {
        String p = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
        String e = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        KMPSearcher kmp = new KMPSearcher(p);
        System.out.println(kmp.search(s));
        System.out.println(kmp.search(e));

        ACSearcher ac = new ACSearcher(Arrays.asList(new String[]{p, "ab", "aab"}));
        IMatchHandler handler = new IMatchHandler() {
            @Override
            public void handle(int pos, String pattern) {
                System.out.println("pos: " + pos + ", pattern:" + pattern);
            }
        };
        ac.search(s, handler);
        ac.search(e, handler);

        long begin = System.currentTimeMillis();
        for (int i = 0; i < 100000; i ++) {
            Utils.verify(kmp.search(s));
        }
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        for (int i = 0; i < 100000; i ++) {
            Utils.verify(s.indexOf(p) > 0);
        }
        System.out.println(System.currentTimeMillis() - begin);
    }
}
