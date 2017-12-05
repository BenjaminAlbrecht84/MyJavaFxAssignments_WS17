package presenter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import model.MyEdge;
import model.MyNode;
import model.MyRootPath;
import model.MyTree;
import util.TreeParser;
import view.MyNodeView;
import view.MyTreeView;
import view.MyView;

import java.io.File;
import java.util.HashMap;

public class Presenter {

    private MyView view;

    private HashMap<String, int[]> idToCoordinate;
    private MyTree tree;
    private MyTreeView treeView;

    public Presenter(MyView view) {
        this.view = view;
        view.setPresenter(this);
        setUpFields();
        setUpListener();
        setUpActions();
    }

    private void setUpFields() {
        idToCoordinate = new HashMap<>();
        tree = new MyTree();
        treeView = view.getTreeView();
    }

    private void setUpActions() {

        view.getAnimation1().setOnAction(e -> {
            treeView.setUpAnimationOne();
        });

        view.getAnimation2().setOnAction(e -> {
            treeView.setUpAnimationTwo();
        });

    }

    public void loadFile(File f) {
        tree.clear();
        try {
            for (Object[] info : TreeParser.parseNodeInfo(f)) {
                int[] coordinates = {(int) info[1], (int) info[2]};
                idToCoordinate.put((String) info[0], coordinates);
                tree.addNode((String) info[0], (String) info[3]);
            }
            for (String[] info : TreeParser.parseEdgeInfo(f))
                tree.addEdge(info[0], info[1]);
            tree.assessRoot();
            tree.assessRootPaths();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpListener() {

        tree.getRootPaths().addListener(new ListChangeListener<MyRootPath>() {
            @Override
            public void onChanged(Change<? extends MyRootPath> changeList) {
                while (changeList.next()) {
                    if (changeList.wasAdded()) {
                        for (MyRootPath p : changeList.getAddedSubList())
                            treeView.addRootPath(p);
                    }
                    if (changeList.wasRemoved()) {
                        for (MyRootPath p : changeList.getRemoved())
                            treeView.removeRootPath(p);
                    }
                }
            }
        });

        tree.getNodes().addListener(new ListChangeListener<MyNode>() {
            @Override
            public void onChanged(Change<? extends MyNode> changeList) {
                while (changeList.next()) {
                    if (changeList.wasAdded()) {
                        for (MyNode v : changeList.getAddedSubList()) {
                            int[] point = idToCoordinate.get(v.getId());
                            treeView.addNode(v.getId(), point[0], point[1], v.getLabel());
                        }
                    }
                    if (changeList.wasRemoved()) {
                        for (MyNode v : changeList.getRemoved())
                            treeView.removeNode(v.getId());
                    }
                }
            }
        });

        tree.getEdges().addListener(new ListChangeListener<MyEdge>() {
            @Override
            public void onChanged(Change<? extends MyEdge> changeList) {
                while (changeList.next()) {
                    if (changeList.wasAdded()) {
                        for (MyEdge e : changeList.getAddedSubList())
                            treeView.addEdge(e.getSource().getId(), e.getTarget().getId());
                    }
                    if (changeList.wasRemoved()) {
                        for (MyEdge e : changeList.getRemoved())
                            treeView.removeEdge(e.getId());
                    }
                }
            }
        });

        view.getToggleGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                String s = ((RadioButton) newValue).getText();
                if (s.startsWith("Straight"))
                    treeView.changeEdgeType(MyTreeView.EDGE_TYPE.STRAIGHT);
                if (s.startsWith("Angular"))
                    treeView.changeEdgeType(MyTreeView.EDGE_TYPE.ANGULAR);
                if (s.startsWith("Quad"))
                    treeView.changeEdgeType(MyTreeView.EDGE_TYPE.QUAD_CURVE);
                if (s.startsWith("Cubic"))
                    treeView.changeEdgeType(MyTreeView.EDGE_TYPE.CUBIC_CURVE);
            }
        });

        view.getTreePane().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                treeView.spreadNodes(newValue.intValue() - 5, true);
            }
        });

        view.getTreePane().heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                treeView.spreadNodes(newValue.intValue() - 5, false);
            }
        });

    }

    public void nodeDoubleClicked(MyNodeView nodeView) {
        tree.removeLeaf(nodeView.getIdentifier());
    }

}
