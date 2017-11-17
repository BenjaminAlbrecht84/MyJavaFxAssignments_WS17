package view;

import javafx.animation.ScaleTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class MyNodeView extends Group {

    private MyTreeView treeView;
    private Color nodeColor;
    private SimpleStringProperty label;
    private String id;
    private double downX, downY;

    public MyNodeView(String id, int x, int y, String label, Color nodeColor, MyTreeView treeView) {

        this.treeView = treeView;
        this.id = id;
        this.nodeColor = nodeColor;

        setTranslateX(x);
        setTranslateY(y);

        this.label = label != null ? new SimpleStringProperty(label) : new SimpleStringProperty("");

        createCircle();
        createLabel();

    }

    private void createLabel() {
        Label l = new Label(label.getValue());
        l.setTranslateX(3);
        l.setTranslateY(3);
        getChildren().add(l);
    }

    private void createCircle() {
        Circle c = new Circle();
        c.setRadius(MyTreeView.NODE_RADIUS);
        c.setFill(nodeColor);
        getChildren().add(c);

        setUpMouseEvents(c);

    }

    private void setUpMouseEvents(Circle c) {

        c.setOnMouseEntered(e -> {
            setUpTransition(c, 4);
        });

        c.setOnMouseExited(e -> {
            setUpTransition(c, 1);
        });

        c.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2)
                treeView.nodeDoubleClicked(this);
        });

        c.setOnMousePressed((me) -> {
            downX = me.getSceneX();
            downY = me.getSceneY();
            me.consume();
        });

        c.setOnMouseDragged((me) -> {

            double deltaX = me.getSceneX() - downX;
            double deltaY = me.getSceneY() - downY;

            double newX = ensureBounds(getTranslateX() + deltaX, 0, treeView.getView().getTreePane().getWidth());
            double newY = ensureBounds(getTranslateY() + deltaY, 0, treeView.getView().getTreePane().getHeight());
            setTranslateX(newX);
            setTranslateY(newY);

            downX = me.getSceneX();
            downY = me.getSceneY();

            me.consume();
        });

    }

    private double ensureBounds(double v, int min, double max) {
        v = v > max ? max : v;
        v = v < min ? min : v;
        return v;
    }

    private void setUpTransition(Node node, double toValue) {
        ScaleTransition t = new ScaleTransition(Duration.millis(250), node);
        t.setToX(toValue);
        t.setToY(toValue);
        t.play();
    }

    public double getX() {
        return translateXProperty().get();
    }

    public DoubleProperty xProperty() {
        return translateXProperty();
    }

    public void setX(double x) {
        translateXProperty().setValue(x);
    }

    public double getY() {
        return translateYProperty().get();
    }

    public DoubleProperty yProperty() {
        return translateYProperty();
    }

    public void setY(double y) {
        translateYProperty().setValue(y);
    }

    public String getIdentifier() {
        return id;
    }

    public SimpleStringProperty getLabel() {
        return label;
    }

}
