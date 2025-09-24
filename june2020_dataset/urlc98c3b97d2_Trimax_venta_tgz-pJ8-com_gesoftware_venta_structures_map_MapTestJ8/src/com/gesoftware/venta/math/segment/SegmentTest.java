package com.gesoftware.venta.math.segment;

import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.math.vectors.Vec2i;
import com.gesoftware.venta.math.vectors.Vec2r;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public final class SegmentTest extends JFrame {
    private final static Random c_Random = new Random();
    private final static Vec2i c_Size = new Vec2i(800, 600);
    private final static int c_SegmentsCount = 15;

    private static void testIntersection(final Segment a, final Segment b) {
        LoggingUtility.info("Intersection of " + a + " and " + b + " = " + a.intersects(b));
    }

    private class Demo extends Panel {
        private final Collection<Segment> m_Intersection;
        private final Collection<Segment> m_Segments;
        private final Segment m_Test;

        public Demo(final Collection<Segment> segments, final Segment testSegment) {
            m_Segments     = segments;
            m_Test         = testSegment;

            m_Intersection = getIntersections();
        }

        private void paintSegment(final Graphics g, final Segment segment) {
            g.drawLine((int) segment.getA().getX(), (int) segment.getA().getY(), (int) segment.getB().getX(), (int) segment.getB().getY());
        }

        private void paintList(final Graphics g, final Collection<Segment> segments) {
            for (final Segment segment : segments)
                paintSegment(g, segment);
        }

        private Collection<Segment> getIntersections() {
            final Collection<Segment> intersectedSegments = new ArrayList<Segment>();

            for (final Segment segment : m_Segments)
                if (segment.intersects(m_Test))
                    intersectedSegments.add(segment);

            return intersectedSegments;
        }

        private void drawGrid(final Graphics g) {
            g.setColor(new Color(100, 100, 100, 155));
            for (int x = 0; x < c_Size.getX(); x += 10)
                g.drawLine(x, 0, x, c_Size.getY());

            for (int y = 0; y < c_Size.getY(); y += 10)
                g.drawLine(0, y, c_Size.getX(), y);
        }

        @Override
        public void paint(final Graphics g) {
            super.paint(g);

            /* Draw grid */
            drawGrid(g);

            /* Draw all objects */
            g.setColor(new Color(100, 200, 100, 255));
            paintList(g, m_Segments);

            /* Draw intersected objects */
            g.setColor(new Color(200, 100, 100, 255));
            paintList(g, m_Intersection);

            /* Draw test segment */
            g.setColor(new Color(100, 100, 200, 255));
            paintSegment(g, m_Test);
        }
    }

    private Segment createRandomSegment() {
        return new Segment(Vec2r.multiply(new Vec2r(c_Random.nextInt(80), c_Random.nextInt(60)), 10.0),
                           Vec2r.multiply(new Vec2r(c_Random.nextInt(80), c_Random.nextInt(60)), 10.0));
    }

    private Collection<Segment> createRandomSegments() {
        final Collection<Segment> segments = new ArrayList<Segment>(c_SegmentsCount);
        for (int segmentID = 0; segmentID < c_SegmentsCount; segmentID++)
            segments.add(createRandomSegment());

        return segments;
    }

    private SegmentTest() {
        super();

        /* Setup default window style */
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quad-tree test");

        /* Set demo panel */
        getContentPane().add(new Demo(createRandomSegments(), createRandomSegment()));

        pack();
        setVisible(true);
        setSize(c_Size.getX(), c_Size.getY());
    }

    public static void main(final String args[]) {
        LoggingUtility.setLoggingLevel(LoggingUtility.LoggingLevel.LEVEL_DEBUG);

        final Segment segmentVertical   = new Segment(new Vec2r(0.0, -1.0), new Vec2r(0.0, 1.0));
        final Segment segmentHorizontal = new Segment(new Vec2r(-1.0, 0.0), new Vec2r(1.0, 0.0));

        final Segment segmentUpperLong = new Segment(new Vec2r(-1.0, 1.0), new Vec2r(1.0, 1.0));
        final Segment segmentUpper     = new Segment(new Vec2r(0.0, 1.0),  new Vec2r(1.0, 1.0));

        final Segment segmentDiagonal1 = new Segment(new Vec2r(-1.0, 1.0), new Vec2r(1.0, -1.0));
        final Segment segmentDiagonal2 = new Segment(new Vec2r(1.0, 1.0),  new Vec2r(-1.0, -1.0));

        final Segment segment1 = new Segment(new Vec2r(1.0, 1.0), new Vec2r(10.0, 1.0));
        final Segment segment2  = new Segment(new Vec2r(5.0, 0.5), new Vec2r(0.5, 5.0));

        testIntersection(segmentHorizontal, segmentVertical);
        testIntersection(segmentHorizontal, segmentHorizontal);
        testIntersection(segmentVertical,   segmentVertical);

        testIntersection(segmentVertical,   segmentUpper);
        testIntersection(segmentHorizontal, segmentUpper);

        testIntersection(segmentVertical,   segmentUpperLong);

        testIntersection(segmentDiagonal1, segmentDiagonal2);

        testIntersection(segment1, segment2);

        final Segment segmentA = new Segment(new Vec2r(1.0, 3.0), new Vec2r(2.0, 3.0));
        final Segment segmentB = new Segment(new Vec2r(7.0, 7.0), new Vec2r(7.0, 6.0));
        testIntersection(segmentB, segmentA);
        testIntersection(segmentA, segmentB);

        /* Setup default window style */
        new SegmentTest();
    }
}
