package com.gesoftware.venta.math.trees;

import com.gesoftware.venta.math.shapes.Box2r;
import com.gesoftware.venta.math.vectors.Vec2r;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Quad tree class definition
 **/
public final class QuadTree<T extends Box2r> implements Serializable {
    private final Box2r m_Bounds;
    private final List<T> m_Objects;

    private QuadTree<T> m_ChildNW;
    private QuadTree<T> m_ChildNE;
    private QuadTree<T> m_ChildSW;
    private QuadTree<T> m_ChildSE;

    /* Synchronization object */
    private final Byte m_Sync = (byte) 0;

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] area - quad tree covering region
     * AUTHOR: Eliseev Dmitry
     * */
    public QuadTree(final Box2r area) {
        m_Objects = new ArrayList<T>();
        m_Bounds  = area;
    } /* End of 'QuadTree::QuadTree' method */

    /* *
     * METHOD: Checks if this vertex has children
     * RETURN: True if vertex has 4 subregions, False otherwise
     * AUTHOR: Eliseev Dmitry
     * */
    private boolean hasChildren() {
        return m_ChildNE != null && m_ChildNW != null && m_ChildSE != null && m_ChildSW != null;
    } /* End of 'QuadTree::hasChildren' method */

    /* *
     * METHOD: Removes all stored object at current vertex and all children
     * AUTHOR: Eliseev Dmitry
     * */
    public final void clear() {
        synchronized (m_Sync) {
            m_Objects.clear();
            if (m_ChildNE != null)
                m_ChildNE.clear();
            if (m_ChildNW != null)
                m_ChildNW.clear();
            if (m_ChildSE != null)
                m_ChildSE.clear();
            if (m_ChildSW != null)
                m_ChildSW.clear();

            m_ChildNE = null;
            m_ChildNW = null;
            m_ChildSE = null;
            m_ChildSW = null;
        }
    } /* End of 'QuadTree::clear' method */

    /* *
     * METHOD: Splits current vertex region for 4 subregions
     * AUTHOR: Eliseev Dmitry
     * */
    private void split() {
        if ((m_ChildNW != null)&&(m_ChildNE != null)&&(m_ChildSW != null)&&(m_ChildSE != null))
            return;

        /* Determine sub-node size */
        int subHeight = (int)(m_Bounds.getSize().getY() / 2);
        int subWidth  = (int)(m_Bounds.getSize().getX() / 2);

        /* Get current node position */
        int x = (int) m_Bounds.getPosition().getX();
        int y = (int) m_Bounds.getPosition().getY();

        /* Create child nodes */
        m_ChildNE = new QuadTree<T>(new Box2r(x + subWidth, y, subWidth, subHeight));
        m_ChildNW = new QuadTree<T>(new Box2r(x, y, subWidth, subHeight));
        m_ChildSW = new QuadTree<T>(new Box2r(x, y + subHeight, subWidth, subHeight));
        m_ChildSE = new QuadTree<T>(new Box2r(x + subWidth, y + subHeight, subWidth, subHeight));
    } /* End of 'QuadTree::split' method */

    /* *
     * METHOD: Remove an object from tree
     * RETURN: True if object was removed, False otherwise (object wasn't found)
     *  PARAM: [IN] object - object to remove
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean remove(final T object) {
        if (object == null)
            return false;

        synchronized (m_Sync) {
            if (!m_Bounds.intersects(object))
                return false;

            /* Try to remove object */
            if (m_Objects.remove(object))
                return true;

            /* Try to remove object from children */
            boolean result = false;
            if (m_ChildNE != null)
                result = m_ChildNE.remove(object) || result;
            if (m_ChildNW != null)
                result = m_ChildNW.remove(object) || result;
            if (m_ChildSE != null)
                result = m_ChildSE.remove(object) || result;
            if (m_ChildSW != null)
                result = m_ChildSW.remove(object) || result;

            return result;
        }
    } /* End of 'QuadTree::remove' method */

    /* *
     * METHOD: Distributes given object to one of children
     * RETURN: True object was successfully distributed, False otherwise
     *  PARAM: [IN] object - object to distribute
     * AUTHOR: Eliseev Dmitry
     * */
    private boolean distribute(final T object) {
        return m_ChildNW.insert(object) || m_ChildNE.insert(object) || m_ChildSW.insert(object) || m_ChildSE.insert(object) ;
    } /* End of 'QuadTree::distribute' method */

    /* *
     * METHOD: Inserts object to tree
     * RETURN: True if object was successfully inserted, False otherwise
     *  PARAM: [IN] object - object to insert
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean insert(final T object) {
        if (object == null)
            return false;

        synchronized (m_Sync) {
            if (!object.inside(m_Bounds))
                return false;

            /* Try to divide vertex (if not divided yet) */
            split();

            /* If vertex has children, try to insert object to them, otherwise add to current node */
            boolean objectInserted = false;
            if (hasChildren())
                objectInserted = distribute(object);

            return objectInserted || m_Objects.add(object);
        }
    } /* End of 'QuadTree::insert' method */

    /* *
     * METHOD: Retrieves all objects, that has intersections with given area
     * RETURN: List of objects, that has intersections with given area
     *  PARAM: [IN] intersectedObject - structure to place objects, that has intersections
     *  PARAM: [IN] area              - area to check intersections with
     * AUTHOR: Eliseev Dmitry
     * */
    private List<T> retrieve(final List<T> intersectedObjects, final Box2r area) {
        if (area == null)
            return intersectedObjects;

        if (!m_Bounds.intersects(area))
            return intersectedObjects;

        /* Calculate intersection */
        for (final T object : m_Objects)
            if (object.intersects(area))
                intersectedObjects.add(object);

        /* Intersect area with children */
        if (m_ChildNE != null)
            m_ChildNE.retrieve(intersectedObjects, area);
        if (m_ChildNW != null)
            m_ChildNW.retrieve(intersectedObjects, area);
        if (m_ChildSE != null)
            m_ChildSE.retrieve(intersectedObjects, area);
        if (m_ChildSW != null)
            m_ChildSW.retrieve(intersectedObjects, area);

        /* That's it */
        return intersectedObjects;
    } /* End of 'QuadTree::retrieve' method */

    /* *
     * METHOD: Retrieves all objects, that has intersections with given area
     * RETURN: List of objects, that has intersections with given area
     *  PARAM: [IN] area - area to check intersections with
     * AUTHOR: Eliseev Dmitry
     * */
    public final List<T> retrieve(final Box2r area) {
        synchronized (m_Sync) {
            return retrieve(new ArrayList<T>(), area);
        }
    } /* End of 'QuadTree::retrieve' method */

    /* *
     * METHOD: Retrieves all objects, stored at tree
     * RETURN: List of all objects, stored at tree
     * AUTHOR: Eliseev Dmitry
     * */
    public final List<T> getAll() {
        return retrieve(new Box2r(0, 0, m_Bounds.getSize().getX(), m_Bounds.getSize().getY()));
    } /* End of 'QuadTree:getAll' method */

    /* *
     * METHOD: Determines if given area has intersections with at least one object, stored at tree
     * RETURN: True if given area has intersections with at least one object, stored at tree, False otherwise
     *  PARAM: [IN] area - area to check intersections with
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean hasIntersections(final Box2r area) {
        if (area == null)
            return false;

        synchronized (m_Sync) {
            if (!m_Bounds.intersects(area))
                return false;

            boolean intersections = false;
            if (hasChildren())
                intersections = m_ChildNE.hasIntersections(area) || m_ChildNW.hasIntersections(area) || m_ChildSE.hasIntersections(area) || m_ChildSW.hasIntersections(area);

            for (final T object : m_Objects)
                if (object.intersects(area))
                    intersections |= true;

            /* There are no intersections */
            return intersections;
        }
    } /* End of 'QuadTree::hasIntersections' method */

    /* *
     * METHOD: Finds object if point is inside it
     * RETURN: Object if it has point inside it, null otherwise
     *  PARAM: [IN] point - point to find objects
     * AUTHOR: Eliseev Dmitry
     * */
    public final T getObject(final Vec2r position) {
        if (position == null)
            return null;

        synchronized (m_Sync) {
            if (!m_Bounds.contains(position))
                return null;

            for (final T object : m_Objects)
                if (object.contains(position))
                    return object;

            /* Try to locate object at children */
            if (hasChildren()) {
                final T objectNE = m_ChildNE.getObject(position);
                if (objectNE != null)
                    return objectNE;

                final T objectNW = m_ChildNW.getObject(position);
                if (objectNW != null)
                    return objectNW;

                final T objectSE = m_ChildSE.getObject(position);
                if (objectSE != null)
                    return objectSE;

                final T objectSW = m_ChildSW.getObject(position);
                if (objectSW != null)
                    return objectSW;
            }

            /* Object wasn't found */
            return null;
        }
    } /* End of 'QuadTree::getObject' method */
} /* End of 'QuadTree' class */
