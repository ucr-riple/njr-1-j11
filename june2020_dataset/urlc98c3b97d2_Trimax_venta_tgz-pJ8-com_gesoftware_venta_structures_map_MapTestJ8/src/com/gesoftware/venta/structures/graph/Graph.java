package com.gesoftware.venta.structures.graph;

import com.gesoftware.venta.structures.pair.Pair;

import java.io.Serializable;
import java.util.Collection;

/**
 * Graph class definition
 **/
public final class Graph<V> implements Serializable {
    private final KeyGraph<V, V> m_Graph;

    /* *
     * METHOD: Constructs oriented not-multi graph
     * AUTHOR: Eliseev Dmitry
     * */
    public Graph() {
        this(true, false);
    } /* End of 'Graph::Graph' method */

    /* *
     * METHOD: Constructs not-multi graph
     *  PARAM: [IN] isDirected - is graph oriented flag
     * AUTHOR: Eliseev Dmitry
     * */
    public Graph(final boolean isDirected) {
        this(isDirected, false);
    } /* End of 'Graph::Graph' method */

    /* *
     * METHOD: Constructs graph
     *  PARAM: [IN] isDirected - is graph oriented flag
     *  PARAM: [IN]    isMulti - is multi-graph flag
     * AUTHOR: Eliseev Dmitry
     * */
    public Graph(final boolean isDirected, final boolean isMulti) {
        m_Graph    = new KeyGraph<V, V>(isDirected, isMulti);
    } /* End of 'Graph::Graph' method */

    /* *
     * METHOD: Adds vertex to graph
     * RETURN: True if vertex added, False otherwise
     *  PARAM: [IN] vertex - vertex to add
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean addVertex(final V vertex) {
        return m_Graph.addVertex(vertex, vertex);
    } /* End of 'Graph::addVertex' method */

    /* *
     * METHOD: Adds an edge between to vertices
     *  PARAM: [IN] vertexFrom - start vertex
     *  PARAM: [IN] vertexTo   - end vertex
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean addEdge(final V vertexFrom, final V vertexTo) {
        return addEdge(vertexFrom, vertexTo, 1.0f);
    } /* End of 'Graph::addEdge' method */

    /* *
     * METHOD: Adds an edge between to vertices
     *  PARAM: [IN] vertexFrom - start vertex
     *  PARAM: [IN] vertexTo   - end vertex
     *  PARAM: [IN] cost       - edge's cost
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean addEdge(final V vertexFrom, final V vertexTo, final double cost) {
        return m_Graph.addEdge(vertexFrom, vertexTo, cost);
    } /* End of 'Graph::addEdge' method */

    /* *
     * METHOD: Remove vertex and all edges, connected with it
     *  PARAM: [IN] vertex - vertex to remove
     * AUTHOR: Eliseev Dmitry
     * */
    public final void removeVertex(final V vertex) {
        m_Graph.removeVertex(vertex);
    } /* End of 'Graph::removeVertex' method */

    /* *
     * METHOD: Removes an edge from graph
     *  PARAM: [IN] vertexFrom - start vertex
     *  PARAM: [IN] vertexTo   - end vertex
     * AUTHOR: Eliseev Dmitry
     * */
    public final void removeEdge(final V vertexFrom, final V vertexTo) {
        m_Graph.removeEdge(vertexFrom, vertexTo);
    } /* End of 'Graph::removeEdge' method */

    /* *
     * METHOD: Gets collection of all graph edges
     * RETURN: Collection of all edges
     * AUTHOR: Eliseev Dmitry
     * */
    public final Collection<Pair<V, V>> getEdges() {
        return m_Graph.getEdges();
    } /* End of 'Graph::getEdges' method */

    /* *
     * METHOD: Gets collection of all vertices
     * RETURN: Collection of all vertices
     * AUTHOR: Eliseev Dmitry
     * */
    public final Collection<V> getVertices() {
        return m_Graph.getKeys();
    } /* End of 'Graph::getVertices' method */

    @Override
    public final int hashCode() {
        return m_Graph.hashCode();
    } /* End of 'Graph::hashCode' method */

    @Override
    public final String toString() {
        return m_Graph.toString();
    } /* End of 'Graph::toString' method */

    @Override
    public final boolean equals(final Object obj) {
        return obj instanceof Graph && m_Graph.equals(((Graph) obj).m_Graph);
    } /* End of 'Graph::equals' method */

    /*** Algorithms ***/

    /* *
     * METHOD: Find shortest path between two vertices if it's exists using Dijkstra algorithm
     * RETURN: Found path if success, null otherwise
     *  PARAM: [IN] vertexFrom - start vertex key
     *  PARAM: [IN] vertexTo   - end vertex key
     * AUTHOR: Eliseev Dmitry
     * */
    public final Collection<V> getShortestPath(final V vertexFrom, final V vertexTo) {
        return m_Graph.getShortestPath(vertexFrom, vertexTo);
    } /* End of 'Graph::getShortestPath' method */
} /* End of 'Graph' class */
