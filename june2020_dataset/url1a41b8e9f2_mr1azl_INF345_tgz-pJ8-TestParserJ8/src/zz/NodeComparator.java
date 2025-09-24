package zz;

import java.util.Comparator;

import org.w3c.dom.Node;

/**
 */
public class NodeComparator implements Comparator<Node> {
    /**
     * Method compare.
     * @param o1 Node
     * @param o2 Node
     * @return int
     */
    @Override
    public int compare(Node o1, Node o2) {
        return o1.getNodeName().compareTo(o2.getNodeName());
    }
}