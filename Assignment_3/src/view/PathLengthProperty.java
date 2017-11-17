package view;

import javafx.beans.binding.DoubleBinding;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class PathLengthProperty extends DoubleBinding {

    private ObservableList<MyEdgeView> edges;
    private DoubleBinding[] edgeDistances = {};

    private final ListChangeListener<MyEdgeView> BOUND_LIST_CHANGE_LISTENER
            = (ListChangeListener.Change<? extends MyEdgeView> change) -> {
        refreshBinding();
    };

    public PathLengthProperty(ObservableList<MyEdgeView> edges) {
        this.edges = edges;
        edges.addListener(BOUND_LIST_CHANGE_LISTENER);
        refreshBinding();
    }

    private void refreshBinding() {
        unbind(edgeDistances);
        edgeDistances = new DoubleBinding[edges.size()];
        for (int i = 0; i < edges.size(); i++)
            edgeDistances[i] = edges.get(i).lengthProperty();
        super.bind(edgeDistances);
        invalidate();
    }

    @Override
    protected double computeValue() {
        double length = 0;
        for (DoubleBinding distance : edgeDistances)
            length += Math.abs(distance.get());
        return length;
    }

    @Override
    public void dispose() {
        edges.removeListener(BOUND_LIST_CHANGE_LISTENER);
        unbind(edgeDistances);
    }

}
