package model;

public class MyEdge {

    private MyNode source, target;
    private String id;

    public MyEdge(MyNode source, MyNode target) {
        this.source = source;
        this.target = target;
        this.id = target.getId();
        source.addChildren(target);
    }

    public MyNode getSource() {
        return source;
    }

    public MyNode getTarget() {
        return target;
    }

    public String getId() {
        return id;
    }

}
