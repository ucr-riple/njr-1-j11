package ikrs.util;

/**
 * A path is an abstract sequence of tokens that is usually used to address an item
 * inside a tree.
 *
 * Example: inside the tree
 *       A
 *     /   \
 *    B     C
 *   / \     \
 *  D   E     F
 *
 * the element 'E' would be located at the path [ A, B, E ].
 * A path must not necesarily address a leaf; inner nodes have paths too.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-12-11
 * @version 1.0.0
 **/


public interface Path<T> {

    /**
     * Get the length of this path.
     * The length of a path is the exact number of items. An empty path has the size 0.
     *
     * @return The length of this path which is the number of path elements.
     **/
    public int getLength();

    /**
     * Get the first element of this path.
     * If the path is empty the method must return null.
     *
     * @return The first path element or null if the path is empty.
     **/
    public T getFirstElement();

    /**
     * Get the trailing path from this path. The trailing path is this path without
     * the first element.
     * Retrieving the trailing path is equivalent to going down one level inside the 
     * tree structure.
     *
     * If this path is empty the returned trailing path is null.
     *
     * @return The trailing path or null if this path is empty.
     **/
    public Path<T> getTrailingPath();


}
