package view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import model.MyEdge;
import model.MyRootPath;

import java.util.*;

public class MyTreeView extends Group {

    final static int NODE_RADIUS = 5;
    final static int OFFSET = 10;

    public enum EDGE_TYPE {STRAIGHT, ANGULAR, QUAD_CURVE, CUBIC_CURVE}

    private EDGE_TYPE edgeType = EDGE_TYPE.STRAIGHT;

    private HashMap<String, MyRootPathView> rootPathViews = new HashMap<>();
    private HashMap<String, MyNodeView> nodeViews = new HashMap<>();
    private HashMap<String, MyEdgeView> edgeViews = new HashMap<>();
    private Group nodeViewGroup = new Group();
    private Group edgeViewGroup = new Group();
    private Group animationGroup = new Group();

    private MyView view;

    public MyTreeView(MyView view) {
        this.view = view;
        getChildren().addAll(edgeViewGroup);
        getChildren().addAll(nodeViewGroup);
        getChildren().addAll(animationGroup);
    }

    public void addNode(String id, int x, int y, String label) {
        MyNodeView nodeView = new MyNodeView(id, x + OFFSET, y + OFFSET, label, Color.BLACK, this);
        nodeViewGroup.getChildren().add(nodeView);
        nodeViews.put(id, nodeView);
    }

    public void removeNode(String id) {
        MyNodeView nodeView = nodeViews.get(id);
        nodeViewGroup.getChildren().remove(nodeView);
        nodeViews.remove(id);
    }

    public void addEdge(String id1, String id2) {
        MyNodeView source = nodeViews.get(id1);
        MyNodeView target = nodeViews.get(id2);
        MyEdgeView edgeView = new MyEdgeView(source, target, edgeType);
        edgeViewGroup.getChildren().add(edgeView);
        edgeViews.put(edgeView.getIdentifier(), edgeView);
    }

    public void removeEdge(String id) {
        MyEdgeView edgeView = edgeViews.get(id);
        edgeViewGroup.getChildren().remove(edgeView);
        edgeViews.remove(id);
    }

    public void changeEdgeType(EDGE_TYPE edgeType) {
        animationGroup.getChildren().clear();
        this.edgeType = edgeType;
        for (Node edgeView : edgeViewGroup.getChildren())
            ((MyEdgeView) edgeView).setEdgeType(edgeType);
    }


    public void addRootPath(MyRootPath p) {
        MyRootPathView pathView = new MyRootPathView(p.getId());
        for (MyEdge e : p.getEdges())
            pathView.addEdge(edgeViews.get(e.getId()));
        rootPathViews.put(p.getId(), pathView);
    }

    public void removeRootPath(MyRootPath p) {
        rootPathViews.remove(p.getId());
    }

    public void spreadNodes(int size, boolean horizontal) {
        animationGroup.getChildren().clear();
        ArrayList<Double> coordinates = new ArrayList();
        for (MyNodeView v : nodeViews.values()) {
            double c = horizontal ? v.getX() : v.getY();
            if (!coordinates.contains(c))
                coordinates.add(c);
        }
        if (coordinates.size() > 1) {
            Collections.sort(coordinates);
            double shift = (size - (2 * OFFSET)) / (coordinates.size() - 1);
            for (MyNodeView v : nodeViews.values()) {
                if (horizontal)
                    v.setX((shift * coordinates.indexOf(v.getX())) + OFFSET);
                else
                    v.setY((shift * coordinates.indexOf(v.getY())) + OFFSET);
            }
        }
    }

    public void nodeDoubleClicked(MyNodeView nodeView) {
        view.nodeDoubleClicked(nodeView);
    }

    public void setUpAnimationOne() {
        MyEdgeAnimations.setUpAnimationOne(this);
    }

    public void setUpAnimationTwo() {
        MyEdgeAnimations.setUpAnimationTwo(this);
    }

    public HashMap<String, MyNodeView> getNodeViews() {
        return nodeViews;
    }

    public HashMap<String, MyEdgeView> getEdgeViews() {
        return edgeViews;
    }

    public Group getAnimationGroup() {
        return animationGroup;
    }

    public MyView getView() {
        return view;
    }

    public HashMap<String, MyRootPathView> getRootPathViews() {
        return rootPathViews;
    }

}
