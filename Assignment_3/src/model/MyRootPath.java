package model;

import java.util.ArrayList;

public class MyRootPath {

    private String id;
    private ArrayList<MyEdge> edges;

    public MyRootPath(MyNode v, MyTree tree) {
        id = v.getId();
        edges = new ArrayList<MyEdge>();
        while (v.getParent() != null) {
            edges.add(0, tree.getEdge(v.getId()));
            v = v.getParent();
        }
    }

    public ArrayList<MyEdge> getEdges() {
        return edges;
    }

    public String getId() {
        return id;
    }

}
