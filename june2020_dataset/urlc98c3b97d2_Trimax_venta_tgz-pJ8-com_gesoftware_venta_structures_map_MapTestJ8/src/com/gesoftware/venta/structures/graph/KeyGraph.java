package com.gesoftware.venta.structures.graph;

import com.gesoftware.venta.structures.map.SynchronizedMap;
import com.gesoftware.venta.structures.pair.Pair;
import com.gesoftware.venta.structures.set.SynchronizedSet;

import java.io.Serializable;
import java.util.*;

/**
 * KeyGraph class definition
 **/
public final class KeyGraph<Key, V> implements Serializable {
    /* Is graph oriented */
    private final boolean m_IsDirected;

    /* Is multi-graph */
    private final boolean m_IsMulti;

    /* Real graph (internal -> Collection<internal>) */
    private final SynchronizedMap<Key, Collection<Edge>> m_Graph;

    /* Keys -> Vertices map */
    private final SynchronizedMap<Key, V> m_Vertices;

    /**
     * Edge class definition
     **/
    private final class Edge {
        /* Target vertex key */
        private final Key m_Target;

        /* Edge cost */
        private double m_Cost;

        /* *
         * METHOD: Class constructor
         *  PARAM: [IN] target - target vertex key
         * AUTHOR: Eliseev Dmitry
         * */
        public Edge(final Key target) {
            this(target, 1.0f);
        } /* End of 'Edge::Edge' method */

        /* *
         * METHOD: Class constructor
         *  PARAM: [IN] target - target vertex key
         *  PARAM: [IN] cost   - edge cost
         * AUTHOR: Eliseev Dmitry
         * */
        public Edge(final Key target, final double cost) {
            m_Target = target;
            m_Cost   = cost;
        } /* End of 'Edge::Edge' method */

        /* *
         * METHOD: Sets edge cost
         *  PARAM: [IN] cost - edge cost
         * AUTHOR: Eliseev Dmitry
         * */
        public final void setCost(final float cost) {
            m_Cost = cost;
        } /* End of 'Edge::setCost' method */

        /* *
         * METHOD: Gets edge's cost
         * RETURN: Edge cost
         * AUTHOR: Eliseev Dmitry
         * */
        public final double getCost() {
            return m_Cost;
        } /* End of 'Edge::getCost' method */

        /* *
         * METHOD: Gets edge's target vertex
         * RETURN: Edge's target vertex
         * AUTHOR: Eliseev Dmitry
         * */
        public final Key getTarget() {
            return m_Target;
        } /* End of 'Edge::getTarget' method */

        @Override
        public final int hashCode() {
            return m_Target.hashCode();
        } /* End of 'Edge::hashCode' method */

        @Override
        public final boolean equals(final Object obj) {
            return (obj instanceof KeyGraph.Edge && ((KeyGraph.Edge) obj).getTarget().equals(m_Target)) || m_Target.equals(obj);
        } /* End of 'Edge::equals' method */

        @Override
        public final String toString() {
            return m_Target.toString();
        } /* End of 'Edge::toString' method */
    } /* End of 'KeyGraph::Edge' class */

    /**
     * Vertex class definition
     **/
    private final class Vertex {
        /* Current vertex key */
        public final Key m_Key;

        /* Shortest known distance to current vertex */
        public double m_Distance;

        /* *
         * METHOD: Class constructor
         *  PARAM: [IN] vertex - vertex key
         * AUTHOR: Eliseev Dmitry
         * */
        public Vertex(final Key key) {
            this(key, Float.MAX_VALUE);
        } /* End of 'Vertex::Vertex' method */

        /* *
         * METHOD: Class constructor
         *  PARAM: [IN] vertex   - vertex key
         *  PARAM: [IN] distance - shortest known distance to current vertex
         * AUTHOR: Eliseev Dmitry
         * */
        public Vertex(final Key key, final double distance) {
            m_Distance = distance;
            m_Key      = key;
        } /* End of 'Vertex::Vertex' method */

        @Override
        public final int hashCode() {
            return m_Key.hashCode();
        } /* End of 'Vertex::hashCode' method */

        @Override
        public final boolean equals(final Object obj) {
            return obj instanceof KeyGraph.Vertex && (m_Key.equals(((KeyGraph.Vertex) obj).m_Key));
        } /* End of 'Vertex::equals' method */

        @Override
        public final String toString() {
            return m_Key.toString();
        } /* End of 'Vertex::toString' method */
    } /* End of 'Vertex' class */

    /* *
     * METHOD: Constructs oriented not-multi graph
     * AUTHOR: Eliseev Dmitry
     * */
    public KeyGraph() {
        this(true, false);
    } /* End of 'KeyGraph::KeyGraph' method */

    /* *
     * METHOD: Constructs not-multi graph
     *  PARAM: [IN] isDirected - is graph oriented flag
     * AUTHOR: Eliseev Dmitry
     * */
    public KeyGraph(final boolean isDirected) {
        this(isDirected, false);
    } /* End of 'KeyGraph::KeyGraph' method */

    /* *
     * METHOD: Constructs graph
     *  PARAM: [IN] isDirected - is graph oriented flag
     *  PARAM: [IN]    isMulti - is multi-graph flag
     * AUTHOR: Eliseev Dmitry
     * */
    public KeyGraph(final boolean isDirected, final boolean isMulti) {
        m_Vertices   = new SynchronizedMap<Key, V>();
        m_Graph      = new SynchronizedMap<Key, Collection<Edge>>();
        m_IsDirected = isDirected;
        m_IsMulti    = isMulti;
    } /* End of 'KeyGraph::KeyGraph' method */

    /* *
     * METHOD: Creates empty edges collection, depending on graph type
     * RETURN: Empty edges collection
     * AUTHOR: Eliseev Dmitry
     * */
    private Collection<Edge> createEdgesCollection() {
        return (m_IsMulti && m_IsDirected)?new ArrayList<Edge>():new SynchronizedSet<Edge>();
    } /* End of 'KeyGraph::createEdgesCollection' method */

    /* *
     * METHOD: Adds vertex to graph
     * RETURN: True if vertex added, False otherwise
     *  PARAM: [IN] key    - vertex key
     *  PARAM: [IN] vertex - vertex to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean addVertex(final Key key, final V vertex) {
        synchronized (m_Graph) {
            if (m_Vertices.containsKey(key))
                return false;

            m_Vertices.put(key, vertex);
            m_Graph.put(key, createEdgesCollection());
        }

        return true;
    } /* End of 'KeyGraph::addVertex' method */

    /* *
     * METHOD: Adds an edge between to vertices
     *  PARAM: [IN] vertexFrom - start vertex key
     *  PARAM: [IN] vertexTo   - end vertex key
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean addEdge(final Key vertexFrom, final Key vertexTo) {
        return addEdge(vertexFrom, vertexTo, 1.0f);
    } /* End of 'KeyGraph::addEdge' method */

    /* *
     * METHOD: Adds an edge between to vertices
     *  PARAM: [IN] vertexFrom - start vertex key
     *  PARAM: [IN] vertexTo   - end vertex key
     *  PARAM: [IN] cost       - edge's cost
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean addEdge(final Key vertexFrom, final Key vertexTo, final double cost) {
        /* Only multi-graph allows to create loops */
        if ((vertexFrom == vertexTo)&&(!m_IsMulti))
            return false;

        synchronized (m_Graph) {
            if (!(m_Graph.containsKey(vertexFrom) && m_Graph.containsKey(vertexTo)))
                return false;

            /* Adding edge */
            m_Graph.get(vertexFrom).add(new Edge(vertexTo, cost));

            /* If graph is not directed, then adding inversed edge */
            if (!m_IsDirected)
                m_Graph.get(vertexTo).add(new Edge(vertexFrom, cost));
        }

        return true;
    } /* End of 'KeyGraph::addEdge' method */

    /* *
     * METHOD: Remove vertex and all edges, connected with it
     *  PARAM: [IN] vertex - vertex to remove
     * AUTHOR: Eliseev Dmitry
     * */
    public final void removeVertex(final Key vertex) {
        synchronized (m_Graph) {
            /* Remove vertex from map */
            m_Vertices.remove(vertex);

            /* Remove all edges from graph */
            m_Graph.remove(vertex);
            removeIncomingEdges(vertex);
        }
    } /* End of 'KeyGraph::removeVertex' method */

    /* *
     * METHOD: Removes an edge from graph
     *  PARAM: [IN] vertexFrom - start vertex
     *  PARAM: [IN] vertexTo   - end vertex
     * AUTHOR: Eliseev Dmitry
     * */
    public final void removeEdge(final Key vertexFrom, final Key vertexTo) {
        remove(vertexFrom, vertexTo);

        if (!m_IsDirected)
            remove(vertexTo, vertexFrom);
    } /* End of 'KeyGraph::removeEdge' method */

    /* *
     * METHOD: Removes an edge from directed graph
     *  PARAM: [IN] vertexFrom - start vertex
     *  PARAM: [IN] vertexTo   - end vertex
     * AUTHOR: Eliseev Dmitry
     * */
    @SuppressWarnings("all")
    private void remove(final Key vertexFrom, final Key vertexTo) {
        while (m_Graph.get(vertexFrom).remove(vertexTo));
    } /* End of 'KeyGraph::remove' method */

    /* *
     * METHOD: Removes all incoming edges by vertex
     *  PARAM: [IN] vertex - vertex to remove all incoming edges
     * AUTHOR: Eliseev Dmitry
     * */
    @SuppressWarnings("all")
    private void removeIncomingEdges(final Key vertex) {
        for (final Key currrentVertex : m_Graph.keySet()) {
            final Collection<Edge> edges = createEdgesCollection();
            for (final Edge edge : m_Graph.get(currrentVertex))
                if (!edge.equals(vertex))
                    edges.add(edge);

            m_Graph.put(currrentVertex, edges);
        }
    } /* End of 'KeyGraph::removeIncomingEdges' method */

    /* *
     * METHOD: Gets collection of all graph edges
     * RETURN: Collection of all edges
     * AUTHOR: Eliseev Dmitry
     * */
    public final Collection<Pair<Key, Key>> getEdges() {
        final Collection<Pair<Key, Key>> edges = new ArrayList<Pair<Key, Key>>();

        for (final Key key : m_Graph.keySet())
            for (final Edge edge : m_Graph.get(key))
                edges.add(new Pair<Key, Key>(key, edge.m_Target));

        return edges;
    } /* End of 'KeyGraph::getEdges' method */

    /* *
     * METHOD: Gets collection of all vertices keys
     * RETURN: Collection of all vertices keys
     * AUTHOR: Eliseev Dmitry
     * */
    public final Collection<Key> getKeys() {
        return new ArrayList<Key>(m_Graph.keySet());
    } /* End of 'KeyGraph::getKeys' method */

    /* *
     * METHOD: Gets vertex by it's key
     * RETURN: Corresponding vertex if exists, null otherwise
     *  PARAM: [IN] key - vertex key
     * AUTHOR: Eliseev Dmitry
     * */
    public final V getVertex(final Key key) {
        return m_Vertices.get(key);
    } /* End of 'KeyGraph::getVertex' method */

    @Override
    public final String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final Key vertex : m_Graph.keySet())
            stringBuilder.append(vertex).append(" -> [ ").append(toString(m_Graph.get(vertex))).append("]").append("\n");

        return stringBuilder.toString();
    } /* End of 'KeyGraph::toString' method */

    /* *
     * METHOD: Prints edges list to string
     * RETURN: Edges list string representation
     *  PARAM: [IN] edges - edges list
     * AUTHOR: Eliseev Dmitry
     * */
    private String toString(final Collection<Edge> edges) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final Edge edge : edges)
            stringBuilder.append(edge.getTarget()).append(" ");

        return stringBuilder.toString();
    } /* End of 'KeyGraph::toString' method */

    @Override
    public final int hashCode() {
        synchronized (m_Graph) {
            return m_Graph.hashCode();
        }
    } /* End of 'KeyGraph::hashCode' method */

    @Override
    public final boolean equals(final Object obj) {
        return obj instanceof KeyGraph && m_Graph.equals(((KeyGraph) obj).m_Graph);
    } /* End of 'KeyGraph::equals' method */

    /*** Algorithms ***/

    /* *
     * METHOD: Find shortest path between two vertices if it's exists using Dijkstra algorithm
     * RETURN: Found path if success, null otherwise
     *  PARAM: [IN] vertexFrom - start vertex key
     *  PARAM: [IN] vertexTo   - end vertex key
     * AUTHOR: Eliseev Dmitry
     * */
    public final Collection<Key> getShortestPath(final Key vertexFrom, final Key vertexTo) {
        /* If the target vertex is equal to start vertex, then we should nothing to do */
        if (vertexFrom == vertexTo)
            return new ArrayList<Key>();

        /* Predcessors map */
        final HashMap<Key, Key> predcessors = new HashMap<Key, Key>();

        /* Distances map */
        final HashMap<Key, Double> distances = new HashMap<Key, Double>();

        /* Initialize remaining vertices */
        final PriorityQueue<Vertex> verticesRemaining = new PriorityQueue<Vertex>(m_Graph.keySet().size(), new Comparator<Vertex>() {
            @Override
            public final int compare(final Vertex v1, final Vertex v2) {
                return (int) (v1.m_Distance - v2.m_Distance);
            }
        });

        synchronized (m_Graph) {
            if (!(m_Vertices.containsKey(vertexFrom) && m_Vertices.containsKey(vertexTo)))
                return null;

            /* Initializing. Shortest distance from start vertex to itself is equal to zero */
            distances.put(vertexFrom, 0.0);

            /* Initializing. Predcessor of start vertex is itself */
            predcessors.put(vertexFrom, vertexFrom);

            /* Initializing. By default, all other distances is infinite */
            for (final Key vertex : m_Graph.keySet())
                if (!vertex.equals(vertexFrom)) {
                    verticesRemaining.add(new Vertex(vertex));
                    distances.put(vertex, Double.MAX_VALUE);
                }

            /* Initializing. Starting from first vertex */
            verticesRemaining.add(new Vertex(vertexFrom, 0.0f));

            /* While we have not visited vertices */
            while (!verticesRemaining.isEmpty()) {
                /* Obtain current vertex */
                final Vertex currentVertex = verticesRemaining.poll();

                /* Update all neighbors */
                for (final Edge edge : m_Graph.get(currentVertex.m_Key)) {
                    if (distances.get(currentVertex.m_Key) + edge.getCost() < distances.get(edge.getTarget())) {
                        /* We found more shortest way to vertex, store it */
                        distances.put(edge.getTarget(), distances.get(currentVertex.m_Key) + edge.getCost());

                        /* Update predcessor */
                        predcessors.put(edge.getTarget(), currentVertex.m_Key);

                        /* Update vertices */
                        verticesRemaining.remove(new Vertex(edge.m_Target));
                        verticesRemaining.add(new Vertex(edge.m_Target, distances.get(edge.m_Target)));
                    }
                }
            }
        }

        /* Building path */
        final ArrayList<Key> path = new ArrayList<Key>();

        /* Starting with target vertex, construct reversed path */
        Key currentVertex = vertexTo;
        while (currentVertex != vertexFrom) {
            path.add(currentVertex);
            if (!predcessors.containsKey(currentVertex))
                return null;

            currentVertex = predcessors.get(currentVertex);
        }

        /* Adding startup vertex to path */
        path.add(vertexFrom);

        /* Reverse path and return it */
        Collections.reverse(path);
        return path;
    } /* End of 'KeyGraph::getShortestPath' method */
} /* End of 'KeyGraph' class */
