package common;

/**
 * Created by Sobercheg on 12/7/13.
 */
public class TreeParentNode<T> {
    public T data;
    public TreeParentNode<T> left;
    public TreeParentNode<T> right;
    public TreeParentNode<T> parent;

    public TreeParentNode(T data, TreeParentNode<T> parent) {
        this.data = data;
        this.parent = parent;
    }

    public TreeParentNode() {

    }
}
