package com.example.lab7.functional;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class GraphServiceImpl implements GraphService{

    private final Matrix matrix;

    public GraphServiceImpl(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public Graph<Integer, String> getGraph() {
        Graph<Integer, String> graph = new DirectedSparseGraph<>();
        Integer[][] matrixPayload = matrix.getMatrix();
        for (int i = 0; i < matrixPayload.length; i++) {
            graph.addVertex(i+1);
        }
        for (int i = 0; i < matrixPayload.length; i++) {
            for (int j = 0; j < matrixPayload[0].length; j++) {
                if (matrixPayload[i][j] != 0) {
                    String edge = i+1 + "-" + j+1;
                    graph.addEdge(edge, i+1, matrixPayload[i][j], EdgeType.DIRECTED);
                }
            }
        }
        return graph;
    }
}
