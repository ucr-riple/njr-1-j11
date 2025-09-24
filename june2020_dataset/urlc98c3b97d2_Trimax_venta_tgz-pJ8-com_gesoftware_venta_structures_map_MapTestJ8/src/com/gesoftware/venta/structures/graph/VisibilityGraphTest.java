package com.gesoftware.venta.structures.graph;

import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.math.shapes.Box2r;
import com.gesoftware.venta.math.vectors.Vec2i;
import com.gesoftware.venta.math.vectors.Vec2r;
import com.gesoftware.venta.structures.pair.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public final class VisibilityGraphTest extends JFrame {
    private final static Random c_Random = new Random();
    private final static Vec2i c_Size = new Vec2i(800, 600);
    private final static double c_Scale = 50.0;

    private static Collection<Box2r> createRectangles() {
        final Collection<Box2r> rectangles = new ArrayList<Box2r>();

        rectangles.add(new Box2r(new Vec2r(1.0, 3.0), new Vec2r(1.0, 1.0)));
        rectangles.add(new Box2r(new Vec2r(6.0, 6.0), new Vec2r(1.0, 1.0)));
        rectangles.add(new Box2r(new Vec2r(4.0, 2.0), new Vec2r(1.0, 4.0)));
        rectangles.add(new Box2r(new Vec2r(2.0, 0.0), new Vec2r(4.0, 1.0)));
        rectangles.add(new Box2r(new Vec2r(9.0, 1.0), new Vec2r(1.0, 5.0)));
        rectangles.add(new Box2r(new Vec2r(1.0, 8.0), new Vec2r(10.0, 2.0)));
        rectangles.add(new Box2r(new Vec2r(12.0, 1.0), new Vec2r(1.0, 10.0)));
        rectangles.add(new Box2r(new Vec2r(6.0, 3.0), new Vec2r(2.0, 1.0)));

        return rectangles;
    }

    private class Demo extends Panel implements MouseListener {
        private final VisibilityGraph m_VisibilityGraph;
        private final Collection<Box2r> m_Objects;

        private Vec2r m_Start;
        private Vec2r m_End;

        private ArrayList<Vec2r> m_Path;

        public Demo(final Collection<Box2r> objects, final VisibilityGraph visibilityGraph, final Vec2r start, final Vec2r end) {
            m_VisibilityGraph = visibilityGraph;
            m_Objects         = objects;

            m_Start = start;
            m_End   = end;

            addMouseListener(this);

            m_Path = (ArrayList<Vec2r>) visibilityGraph.getShortestPath(start, end);
        }

        private void paintGrid(final Graphics g) {
            g.setColor(new Color(100, 100, 100, 155));
            for (int x = 0; x < c_Size.getX(); x += (int) c_Scale)
                g.drawLine(x, 0, x, c_Size.getY());

            for (int y = 0; y < c_Size.getY(); y += (int) c_Scale)
                g.drawLine(0, y, c_Size.getX(), y);

            g.drawRect(0, 0, c_Size.getX(), c_Size.getY());
        }

        private void paintObject(final Graphics g, final Box2r rectangle) {
            final Vec2r position = Vec2r.multiply(rectangle.getPosition(), c_Scale);
            final Vec2r size     = Vec2r.multiply(rectangle.getSize(), c_Scale);

            g.fillRect((int)position.getX(), (int)position.getY(), (int)size.getX(), (int)size.getY());
        }

        private void paintObjects(final Graphics g, final Collection<Box2r> rectangles) {
            for (final Box2r rectangle : rectangles)
                paintObject(g, rectangle);
        }

        private void paintVertex(final Graphics g, final Vec2r position) {
            g.fillOval((int) position.getX() - 3, (int) position.getY() - 3, 6, 6);
        }

        private void paintVertices(final Graphics g, final Box2r object) {
            final Vec2r size = Vec2r.multiply(object.getSize(), c_Scale);

            final Vec2r LT = Vec2r.multiply(object.getPosition(), c_Scale);
            final Vec2r RT = new Vec2r(LT.getX() + size.getX(), LT.getY());
            final Vec2r LB = new Vec2r(LT.getX(), LT.getY() + size.getY());
            final Vec2r RB = Vec2r.add(LT, size);

            paintVertex(g, LT);
            paintVertex(g, RT);
            paintVertex(g, LB);
            paintVertex(g, RB);
        }

        private void paintVertices(final Graphics g, final Collection<Box2r> rectangles) {
            for (final Box2r rectangle : rectangles)
                paintVertices(g, rectangle);
        }

        private void paintEdge(final Graphics g, final Pair<Vec2r, Vec2r> edge) {
            final Vec2r start = Vec2r.multiply(edge.getFirst(), c_Scale);
            final Vec2r end   = Vec2r.multiply(edge.getSecond(), c_Scale);
            g.drawLine((int) start.getX(), (int) start.getY(),
                       (int) end.getX(), (int) end.getY());
        }

        private void paintEdges(final Graphics g, final Collection<Pair<Vec2r, Vec2r>> edges) {
            for (final Pair<Vec2r, Vec2r> edge : edges)
                paintEdge(g, edge);
        }

        private void paintPath(final Graphics g) {
            for (int vertexID = 0; vertexID < m_Path.size()-1; vertexID++) {
                final Vec2r current = Vec2r.multiply(m_Path.get(vertexID),   c_Scale);
                final Vec2r next    = Vec2r.multiply(m_Path.get(vertexID+1), c_Scale);

                g.drawLine((int) current.getX(), (int) current.getY(), (int) next.getX(), (int) next.getY());
            }
        }

        @Override
        public void paint(final Graphics g) {
            super.paint(g);

            /* Draw grid */
            paintGrid(g);

            /* Draw all objects */
            g.setColor(new Color(100, 200, 100, 255));
            paintObjects(g, m_Objects);

            /* Draw vertices */
            g.setColor(new Color(200, 100, 100, 255));
            paintVertices(g, m_Objects);

            /* Draw edges */
            g.setColor(new Color(100, 100, 200, 255));
            paintEdges(g, m_VisibilityGraph.getEdges());

            /* Draw start and end vertices */
            g.setColor(new Color(255, 56, 234, 255));
            paintVertex(g, Vec2r.multiply(m_Start, c_Scale));
            paintVertex(g, Vec2r.multiply(m_End,   c_Scale));

            /* Drawing path */
            g.setColor(new Color(255, 0, 0, 255));
            paintPath(g);
        }

        @Override
        public final void mouseClicked(final MouseEvent e) {
        }

        @Override
        public final void mousePressed(final MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1)
                m_Start = Vec2r.multiply(new Vec2r(e.getX(), e.getY()), 1.0 / c_Scale);
            else if (e.getButton() == MouseEvent.BUTTON3)
                m_End = Vec2r.multiply(new Vec2r(e.getX(), e.getY()), 1.0 / c_Scale);

            m_Path = (ArrayList<Vec2r>) m_VisibilityGraph.getShortestPath(m_Start, m_End);
            invalidate();
            repaint();
        }

        @Override
        public final void mouseReleased(final MouseEvent e) {
        }

        @Override
        public final void mouseEntered(final MouseEvent e) {
        }

        @Override
        public final void mouseExited(final MouseEvent e) {
        }
    }

    private VisibilityGraphTest(final VisibilityGraph visibilityGraph, final Vec2r start, final Vec2r end) {
        super();

        /* Setup default window style */
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Visibility graph test");

        /* Set demo panel */
        getContentPane().add(new Demo(createRectangles(), visibilityGraph, start, end));

        pack();
        setVisible(true);
        setSize(c_Size.getX(), c_Size.getY());
    }

    public static void main(final String args[]) {
        LoggingUtility.setLoggingLevel(LoggingUtility.LoggingLevel.LEVEL_DEBUG);

        final VisibilityGraph visibilityGraph = new VisibilityGraph(createRectangles());
        LoggingUtility.info(visibilityGraph.toString());

        final Vec2r start = new Vec2r(0.5, c_Random.nextDouble() * 6.0);
        final Vec2r end   = new Vec2r(8.5, c_Random.nextDouble() * 6.0);

        new VisibilityGraphTest(visibilityGraph, start, end);
    }
}
