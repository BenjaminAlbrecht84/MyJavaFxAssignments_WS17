package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;

public class MyTree {

    private HashMap<String, MyNode> idToNode = new HashMap<>();
    private HashMap<String, MyEdge> idToEdge = new HashMap<>();
    private HashMap<String, MyRootPath> idToPath = new HashMap<>();
    private ObservableList<MyNode> nodes = FXCollections.observableArrayList();
    private ObservableList<MyEdge> edges = FXCollections.observableArrayList();
    private ObservableList<MyRootPath> rootPaths = FXCollections.observableArrayList();
    private MyNode root;

    public void addNode(String id, String label) {
        MyNode v = new MyNode(id, label);
        idToNode.put(id, v);
        nodes.add(v);
    }

    public void addEdge(String id1, String id2) {
        MyEdge e = new MyEdge(idToNode.get(id1), idToNode.get(id2));
        idToEdge.put(e.getId(), e);
        edges.add(e);
    }

    public void assessRootPaths() {
        rootPaths.clear();
        for (MyNode v : nodes) {
            if (v.isLeaf()) {
                MyRootPath rootPath = new MyRootPath(v, this);
                rootPaths.add(rootPath);
                idToPath.put(rootPath.getId(), rootPath);
            }
        }
    }

    public void assessRoot() {
        root = null;
        for (MyNode v : nodes) {
            if (v.getParent() == null) {
                if (root != null)
                    throw new IllegalStateException("ERROR: Multiples potential nodes detected!");
                root = v;
            }
        }
    }

    public void clear() {
        root = null;
        idToNode.clear();
        idToEdge.clear();
        nodes.clear();
        edges.clear();
    }

    public void removeLeaf(String id) {
        MyNode v = idToNode.get(id);
        if (v.getChildren().isEmpty()) {
            v.getParent().getChildren().remove(v);
            MyEdge e = idToEdge.get(id);
            idToEdge.remove(e);
            edges.remove(e);
            idToNode.remove(v);
            nodes.remove(v);
            rootPaths.remove(idToPath.get(id));
            idToPath.remove(id);
            if (e.getSource().isLeaf()) {
                MyRootPath rootPath = new MyRootPath(e.getSource(), this);
                rootPaths.add(rootPath);
                idToPath.put(rootPath.getId(), rootPath);
            }
        }
    }

    public MyEdge getEdge(String id) {
        return idToEdge.get(id);
    }

    public ObservableList<MyNode> getNodes() {
        return nodes;
    }

    public ObservableList<MyEdge> getEdges() {
        return edges;
    }

    public ObservableList<MyRootPath> getRootPaths() {
        return rootPaths;
    }

}
