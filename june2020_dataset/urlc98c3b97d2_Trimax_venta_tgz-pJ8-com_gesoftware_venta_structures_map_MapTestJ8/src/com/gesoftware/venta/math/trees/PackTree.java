package com.gesoftware.venta.math.trees;

import com.gesoftware.venta.math.shapes.Box2i;
import com.gesoftware.venta.processing.IWalker;

public final class PackTree<T extends Box2i> {
    /* Vertex bounds */
    private final Box2i m_Rectangle;

    /* Data */
    private T m_Item;

    /* Children */
    private PackTree<T> m_Right;
    private PackTree<T> m_Left;

    public PackTree(final Box2i rectangle) {
        m_Rectangle = rectangle;
    }

    public final boolean insert(final T item) {
        if (item == null)
            return false;

        if (!isLeaf())
            return m_Left.insert(item) || m_Right.insert(item);

        if (m_Item != null)
            return false;

        if (isFitsImage(item)) {
            m_Item = item;
            m_Item.setPosition(m_Rectangle.getPosition());
            return true;
        }

        if (!isIncludesImage(item))
            return false;

        final int dw = m_Rectangle.getSize().getX()  - item.getSize().getX();
        final int dh = m_Rectangle.getSize().getY() - item.getSize().getY();

        if (dw > dh) {
            m_Left  = new PackTree<T>(new Box2i(m_Rectangle.getPosition().getX(),
                                                m_Rectangle.getPosition().getY(),
                                                item.getWidth(),
                                                m_Rectangle.getHeight()));

            m_Right = new PackTree<T>(new Box2i(m_Rectangle.getPosition().getX() + item.getWidth(),
                                                m_Rectangle.getPosition().getY(),
                                                m_Rectangle.getWidth() - item.getWidth(),
                                                m_Rectangle.getHeight()));
        } else {
            m_Left  = new PackTree<T>(new Box2i(m_Rectangle.getPosition().getX(),
                                                m_Rectangle.getPosition().getY(),
                                                m_Rectangle.getWidth(),
                                                item.getHeight()));

            m_Right = new PackTree<T>(new Box2i(m_Rectangle.getPosition().getX(),
                                                m_Rectangle.getPosition().getY() + item.getHeight(),
                                                m_Rectangle.getWidth(),
                                                m_Rectangle.getHeight() - item.getHeight()));
        }

        return m_Left.insert(item);
    }

    public final void foreach(final IWalker<T> walker) {
        if (isLeaf()) {
            if (m_Item != null)
                walker.walk(m_Item);
            return;
        }

        if (m_Left != null)
            m_Left.foreach(walker);

        if (m_Right != null)
            m_Right.foreach(walker);
    }

    private boolean isLeaf() {
        return m_Right == null && m_Left == null;
    }

    private boolean isIncludesImage(final T item) {
        try {
            return (item.getWidth() <= m_Rectangle.getWidth() && item.getHeight() <= m_Rectangle.getHeight());
        } catch (final Exception ignored) {}

        return false;
    }

    private boolean isFitsImage(final T item) {
        try {
            return (item.getWidth() == m_Rectangle.getWidth() && item.getHeight() == m_Rectangle.getHeight());
        } catch (final Exception ignored) {}

        return false;
    }
}
