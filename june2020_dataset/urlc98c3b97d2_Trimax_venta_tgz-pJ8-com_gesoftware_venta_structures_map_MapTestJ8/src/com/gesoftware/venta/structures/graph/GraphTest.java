package com.gesoftware.venta.structures.graph;

import com.gesoftware.venta.logging.LoggingUtility;

import java.util.Collection;
import java.util.Random;

public final class GraphTest {
    private static final String c_Vertices[] = {"V0", "V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8", "V9"};
    private static final Random c_Generator = new Random();
    private static final int c_VerticesCount = 10;

    private static Graph<String> createRandomGraph(final int verticesCount, final int edgesCount) {
        final Graph<String> graph = new Graph<String>();
        for (int vertexID = 0; vertexID < verticesCount; vertexID++)
            graph.addVertex(c_Vertices[vertexID]);

        for (int edgeID = 0; edgeID < edgesCount; edgeID++)
            graph.addEdge(c_Vertices[c_Generator.nextInt(verticesCount)], c_Vertices[c_Generator.nextInt(verticesCount)]);

        return graph;
    }

    private static Graph<String> createGraph(final boolean isDirected, final boolean isMulti) {
        final Graph<String> graph = new Graph<String>(isDirected, isMulti);
        for (int vertexID = 0; vertexID < 4; vertexID++)
            graph.addVertex(c_Vertices[vertexID]);

        /* Edges */
        graph.addEdge(c_Vertices[0], c_Vertices[1]);
        graph.addEdge(c_Vertices[0], c_Vertices[1]);
        graph.addEdge(c_Vertices[0], c_Vertices[2]);
        graph.addEdge(c_Vertices[2], c_Vertices[3]);
        graph.addEdge(c_Vertices[1], c_Vertices[2]);
        graph.addEdge(c_Vertices[3], c_Vertices[1]);

        return graph;
    }

    public static void main(final String args[]) {
        LoggingUtility.setLoggingLevel(LoggingUtility.LoggingLevel.LEVEL_CORE);

        System.out.println("Directed not multi-graph:");
        final Graph<String> directedGraph = createGraph(true, false);
        System.out.println(directedGraph);

        System.out.println("Directed multi-graph:");
        final Graph<String> multiGraph = createGraph(true, true);
        System.out.println(multiGraph.toString());

        System.out.println("Undirected not multi-graph:");
        final Graph<String> undirectedGraph = createGraph(false, false);
        System.out.println(undirectedGraph.toString());

        System.out.println("Random directed graph graph:");
        final Graph<String> randomGraph = createRandomGraph(c_VerticesCount, c_Generator.nextInt(c_VerticesCount * c_VerticesCount / 2));
        System.out.println(randomGraph);

        /*** Removing vertex test ***/
        System.out.println("Removing vertex V0");

        System.out.println("Directed not multi-graph without V0:");
        directedGraph.removeVertex(c_Vertices[0]);
        System.out.println(directedGraph);

        System.out.println("Directed multi-graph without V0:");
        multiGraph.removeVertex(c_Vertices[0]);
        System.out.println(multiGraph);

        System.out.println("Undirected not multi-graph without V0:");
        undirectedGraph.removeVertex(c_Vertices[0]);
        System.out.println(undirectedGraph);

        /*** Removing edge test ***/
        System.out.println("Removing edge V2 -> V3");

        System.out.println("Directed not multi-graph without V2->V3 edge:");
        directedGraph.removeEdge(c_Vertices[2], c_Vertices[3]);
        System.out.println(directedGraph);

        System.out.println("Directed multi-graph without V2->V3 edge:");
        multiGraph.removeEdge(c_Vertices[2], c_Vertices[3]);
        System.out.println(multiGraph);

        System.out.println("Undirected not multi-graph without V2->V3 edge:");
        undirectedGraph.removeEdge(c_Vertices[2], c_Vertices[3]);
        System.out.println(undirectedGraph);

        /*** Multi graph removing multiple edge ***/
        final Graph<String> multiGraph2 = createGraph(true, true);

        System.out.println("Directed multi-graph without V0->V1 edge:");
        multiGraph2.removeEdge(c_Vertices[0], c_Vertices[1]);
        System.out.println(multiGraph2);

        /*** Testing Dijkstra algorithm ***/
        final Graph<String> graphDijkstra = createRandomGraph(c_VerticesCount, 20);

        System.out.println("Directed not multi-graph:");
        final Collection<String> path = graphDijkstra.getShortestPath(c_Vertices[0], c_Vertices[3]);
        System.out.println(graphDijkstra);

        System.out.println("Shortest path from V0 -> V3:");
        System.out.println(path);
        System.out.println();

        /* Path to self */
        System.out.println("Shortest path from V0 -> V0:");
        final Collection<String> selfPath = graphDijkstra.getShortestPath(c_Vertices[0], c_Vertices[0]);
        System.out.println(selfPath);
        System.out.println();

        /* Try to find not-existing path at unlinked graph */
        final Collection<String> notExistingPath = directedGraph.getShortestPath(c_Vertices[3], c_Vertices[0]);
        System.out.println("Shortest path from V3 -> V0:");
        System.out.println(notExistingPath);
    }
}
