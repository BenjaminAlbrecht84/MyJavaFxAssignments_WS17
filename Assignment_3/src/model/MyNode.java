package model;

import java.util.ArrayList;

public class MyNode {

    private ArrayList<MyNode> children = new ArrayList<MyNode>();
    private MyNode parent;
    private String id, label;

    public MyNode(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public void addChildren(MyNode v) {
        if (!children.contains(v))
            children.add(v);
        v.setParent(this);
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public void setParent(MyNode parent) {
        this.parent = parent;
    }

    public ArrayList<MyNode> getChildren() {
        return children;
    }

    public MyNode getParent() {
        return parent;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

}
