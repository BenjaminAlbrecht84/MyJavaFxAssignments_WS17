package util;

import io.GraphWriter;
import javafx.collections.ObservableList;
import model.AEdge;
import model.AGraph;
import model.ANode;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class RandomGraphCreator {

    private static Random rand = new Random();

    public static AGraph run(int numOfNodes, int numOfEdges) {

        AGraph graph = new AGraph();
        for (int i = 0; i < numOfNodes; i++)
            graph.addNode(String.valueOf(i));

        int counter = 0;
        while (counter < numOfEdges) {
            ANode v = getRandomNode(graph);
            ANode w = getRandomNode(graph);
            AEdge e = graph.addEdge(v, w, String.valueOf(counter), true);
            counter = e == null ? counter : counter + 1;
        }

        return graph;
    }

    private static ANode getRandomNode(AGraph graph) {
        ObservableList<ANode> nodes = graph.getNodes();
        return nodes.get(rand.nextInt(nodes.size()));
    }


}
