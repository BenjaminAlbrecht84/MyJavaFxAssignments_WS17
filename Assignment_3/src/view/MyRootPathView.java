package view;

import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class MyRootPathView {

    private String id;
    private ObservableList<MyEdgeView> edges = FXCollections.observableArrayList();
    private PathLengthProperty length;

    public MyRootPathView(String id) {
        this.id = id;
        this.length = new PathLengthProperty();
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

    public class PathLengthProperty extends DoubleBinding {

        private DoubleBinding[] edgeLengths = {};

        private final ListChangeListener<MyEdgeView> BOUND_LIST_CHANGE_LISTENER
                = (ListChangeListener.Change<? extends MyEdgeView> change) -> {
            refreshBinding();
        };

        public PathLengthProperty() {
            edges.addListener(BOUND_LIST_CHANGE_LISTENER);
            refreshBinding();
        }

        private void refreshBinding() {
            unbind(edgeLengths);
            edgeLengths = new DoubleBinding[edges.size()];
            for (int i = 0; i < edges.size(); i++)
                edgeLengths[i] = edges.get(i).lengthProperty();
            super.bind(edgeLengths);
            invalidate();
        }

        @Override
        protected double computeValue() {
            double length = 0;
            for (DoubleBinding l : edgeLengths)
                length += Math.abs(l.get());
            return length;
        }

        @Override
        public void dispose() {
            edges.removeListener(BOUND_LIST_CHANGE_LISTENER);
            unbind(edgeLengths);
        }

    }

}
