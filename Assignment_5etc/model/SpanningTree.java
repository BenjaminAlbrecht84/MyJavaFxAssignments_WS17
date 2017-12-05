package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class SpanningTree {

    public static AGraph run(AGraph g) {

        // turning directed graph into an undirected one
        undirectEdges(g);

        // computing spanning tree
        ANode v;
        while ((v = chooseNode(g.getNodes())) != null)
            cmpSpanningTreeRec(v);

        ArrayList<AEdge> edges = new ArrayList<>(g.getEdges());
        for (AEdge e : edges) {
            if ((int) e.getUserData() == 0)
                g.removeEdge(e.getId());
        }

        return g;

    }

    private static void cmpSpanningTreeRec(ANode v) {
        if (!isSelected(v)) {
            selectNode(v);
            for (AEdge e : v.getOutEdges()) {
                ANode w = e.getTarget();
                if (isSelected(w) && !isSelected(e))
                    deselectEdges(v, w);
                else if (!isSelected(w)) {
                    selectEdges(v, w);
                    cmpSpanningTreeRec(w);
                }
            }
        }
    }

    private static void selectNode(ANode v) {
        v.setUserData(true);
    }

    private static void deselectEdges(ANode v, ANode w) {
        setEdgeValue(v, w, 0);
        setEdgeValue(w, v, 0);
    }

    private static void selectEdges(ANode v, ANode w) {
        setEdgeValue(v, w, 1);
        setEdgeValue(w, v, 1);
    }

    private static void setEdgeValue(ANode v, ANode w, int value) {
        for (AEdge e : v.getOutEdges()) {
            if (e.getTarget().equals(w))
                e.setUserData(value);
        }
    }

    private static boolean isSelected(AEdge e) {
        if (e.getUserData() == null)
            return false;
        return true;
    }

    private static boolean isSelected(ANode v) {
        if (v.getUserData() == null)
            return false;
        return true;
    }

    private static ANode chooseNode(ObservableList<ANode> nodes) {
        for (ANode v : nodes) {
            if (!isSelected(v))
                return v;
        }
        return null;
    }

    private static void undirectEdges(AGraph g) {
        ArrayList<AEdge> edges = new ArrayList<>(g.getEdges());
        for (AEdge e : edges)
            g.addEdge(e.getTarget(), e.getSource(), e.getText(), true);
    }

}
