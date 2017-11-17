package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MyRootPathView {

    private String id;
    private ObservableList<MyEdgeView> edges = FXCollections.observableArrayList();
    private PathLengthProperty length;

    public MyRootPathView(String id) {
        this.id = id;
        this.length = new PathLengthProperty(edges);
    }

    public void addEdge(MyEdgeView e) {
        edges.add(e);
    }

    public double getLength() {
        return length.get();
    }

    public PathLengthProperty lengthProperty() {
        return length;
    }

    public ObservableList<MyEdgeView> getEdges() {
        return edges;
    }

    public String getId() {
        return id;
    }


}
