package com.gesoftware.venta.structures.graph;

import com.gesoftware.venta.math.segment.Segment;
import com.gesoftware.venta.math.shapes.Box2r;
import com.gesoftware.venta.math.tools.Mathematics;
import com.gesoftware.venta.math.vectors.Vec2r;
import com.gesoftware.venta.structures.pair.Pair;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Visibility graph definition
 **/
public final class VisibilityGraph {
    /* Segments collection, obtained by rectangles */
    private final Collection<Segment> m_Segments;

    /* Real graph */
    private final Graph<Vec2r> m_Graph;

    /* *
     * METHOD: Constructs visibility graph by rectangles collection
     * AUTHOR: Eliseev Dmitry
     * */
    public VisibilityGraph(final Collection<? extends Box2r> rectangles) {
        m_Segments = getSegments(rectangles);
        m_Graph    = build();
    } /* End of 'VisibilityGraph::VisibilityGraph' method */

    /* *
     * METHOD: Gets segments collection by rectangles collection
     * RETURN: Segments collection
     *  PARAM: [IN] rectangles - collection of rectangles
     * AUTHOR: Eliseev Dmitry
     * */
    private Collection<Segment> getSegments(final Collection<? extends Box2r> rectangles) {
        final Collection<Segment> segments = new ArrayList<Segment>(rectangles.size() * 6);

        /* Segments creation */
        for (final Box2r rectangle : rectangles) {
            final Vec2r pointLT = rectangle.getPosition();
            final Vec2r pointRT = Vec2r.add(pointLT, new Vec2r(rectangle.getSize().getX(), 0.0));
            final Vec2r pointRB = Vec2r.add(rectangle.getPosition(), rectangle.getSize());
            final Vec2r pointLB = Vec2r.diff(pointRB, new Vec2r(rectangle.getSize().getX(), 0.0));

            segments.add(new Segment(pointLT, pointRT));
            segments.add(new Segment(pointRT, pointRB));
            segments.add(new Segment(pointRB, pointLB));
            segments.add(new Segment(pointLB, pointLT));
            segments.add(new Segment(pointLT, pointRB));
            segments.add(new Segment(pointRT, pointLB));
        }

        /* That's it */
        return segments;
    } /* End of 'VisibilityGraph::getSegments' method */

    /* *
     * METHOD: Builds visibility graph, based on collection of rectangles
     * RETURN: Visibility graph, corresponding to collection of rectangles
     *  PARAM: [IN] segments - collection of segments
     * AUTHOR: Eliseev Dmitry
     * */
    private Graph<Vec2r> build() {
        final Graph<Vec2r> visibilityGraph = new Graph<Vec2r>(false);

        final Collection<Vec2r> vertices = new ArrayList<Vec2r>(m_Segments.size());

        /* Adding segments vertices as graph vertics */
        for (final Segment segment : m_Segments) {
            if (visibilityGraph.addVertex(segment.getA()))
                vertices.add(segment.getA());

            if (visibilityGraph.addVertex(segment.getB()))
                vertices.add(segment.getB());
        }

        for (final Vec2r sourceVertex : vertices)
            for (final Vec2r destinationVertex : vertices)
                if (sourceVertex != destinationVertex)
                    if (areVerticesVisibleFromEachOther(sourceVertex, destinationVertex))
                        visibilityGraph.addEdge(sourceVertex, destinationVertex, Mathematics.distance(sourceVertex, destinationVertex));

        /* That's it */
        return visibilityGraph;
    } /* End of 'VisibilityGraph::buildVisibilityGraph' method */

    /* *
     * METHOD: Checks is vertex is visible from another vertex
     * RETURN: True if vertex is visible, False otherwise
     *  PARAM: [IN] vertex1  - first vertex
     *  PARAM: [IN] vertex2  - second vertex
     *  PARAM: [IN] segments - collection of segments
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean areVerticesVisibleFromEachOther(final Vec2r vertex1, final Vec2r vertex2) {
        final Segment currentSegment = new Segment(vertex1, vertex2);

        for (final Segment segment : m_Segments) {
            /* Skip segments, which are contained at least one end of current segment */
            if ((segment.contains(currentSegment.getA()))||(segment.contains(currentSegment.getB())))
                continue;

            /* Vertices are not visible from each other if we have at least one intersection between them */
            if (segment.intersects(currentSegment))
                return false;
        }

        return true;
    } /* End of 'VisibilityGraph::areVerticesVisibleFromEachOther' method */

    /* *
     * METHOD: Gets collection of all graph edges
     * RETURN: Collection of all edges
     * AUTHOR: Eliseev Dmitry
     * */
    public final Collection<Pair<Vec2r, Vec2r>> getEdges() {
        return m_Graph.getEdges();
    } /* End of 'VisibilityGraph::getEdges' method */

    /* *
     * METHOD: Find shortest path between two vertices if it's exists using Dijkstra algorithm
     * RETURN: Found path if success, null otherwise
     *  PARAM: [IN] vertexFrom - start vertex key
     *  PARAM: [IN] vertexTo   - end vertex key
     * AUTHOR: Eliseev Dmitry
     * */
    public final Collection<Vec2r> getShortestPath(final Vec2r from, final Vec2r to) {
        synchronized (m_Graph) {
            /* Adding start & finish vertices */
            m_Graph.addVertex(from);
            m_Graph.addVertex(to);

            /* Connect start & finish vertices with all visible existing vertices */
            for (final Vec2r vertex : m_Graph.getVertices()) {
                if (areVerticesVisibleFromEachOther(from, vertex))
                    m_Graph.addEdge(from, vertex, Mathematics.distance(from, vertex));

                if (areVerticesVisibleFromEachOther(vertex, to))
                    m_Graph.addEdge(vertex, to, Mathematics.distance(vertex, to));
            }

            /* Building path */
            final Collection<Vec2r> path = m_Graph.getShortestPath(from, to);

            /* Removing start & finish vertices */
            m_Graph.removeVertex(from);
            m_Graph.removeVertex(to);

            return path;
        }
    } /* End of 'VisibilityGraph::getShortestPath' method */

    public final void removeVertex(final Vec2r vertex) {
        m_Graph.removeVertex(vertex);
    } /* End of 'VisibilityGraph::removeVertex' method */

    @Override
    public final int hashCode() {
        return m_Graph.hashCode();
    } /* End of 'VisibilityGraph::hashCode' method */

    @Override
    public final boolean equals(final Object obj) {
        return obj instanceof VisibilityGraph && (m_Graph.equals(((VisibilityGraph)obj).m_Graph));
    } /* End of 'VisibilityGraph::equals' method */

    @Override
    public final String toString() {
        return m_Graph.toString();
    } /* End of 'VisibilityGraph::toString' method */
} /* End of 'VisibilityGraph' class */
