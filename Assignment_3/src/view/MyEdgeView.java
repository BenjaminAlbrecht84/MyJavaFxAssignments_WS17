package view;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class MyEdgeView extends Group {

    private DoubleBinding length;

    private MyNodeView source, target;
    private MyTreeView.EDGE_TYPE edgeType;
    private Shape shape;
    private String id;

    public MyEdgeView(MyNodeView source, MyNodeView target, MyTreeView.EDGE_TYPE edgeType) {
        this.source = source;
        this.target = target;
        this.id = target.getIdentifier();
        this.edgeType = edgeType;
        drawLine();
        setUpBindings();
    }

    private void setUpBindings() {

        length = new DoubleBinding() {
            {
                bind(source.xProperty(), source.yProperty(), target.xProperty(), target.yProperty());
            }

            @Override
            protected double computeValue() {
                double a = source.xProperty().get() - target.xProperty().get();
                double b = source.yProperty().get() - target.yProperty().get();
                return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
            }
        };

    }

    public Object[] setUpAnimation(int radius, Color c1, Color c2, Duration duration) {

        Ellipse oval = new Ellipse(radius * 2, radius);
        oval.setStrokeWidth(2);
        oval.setStroke(Color.BLACK);
        oval.setFill(c1);
        oval.setOpacity(0);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(oval);
        pathTransition.setDuration(duration);
        pathTransition.setPath(shape);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        FillTransition fillTransition = new FillTransition();
        fillTransition.setShape(oval);
        fillTransition.setDuration(duration);
        fillTransition.setFromValue(c1);
        fillTransition.setToValue(c2);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0), oval);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        ParallelTransition parallelTransition = new ParallelTransition(pathTransition, fillTransition, fadeTransition);
        parallelTransition.setOnFinished(e -> {
            oval.setOpacity(0);
        });
        Object[] result = {parallelTransition, oval};
        return result;

    }

    private void drawLine() {
        getChildren().remove(shape);
        switch (edgeType) {
            case ANGULAR:
                shape = new Path();
                setUpPath((Path) shape);
                break;
            case QUAD_CURVE:
                shape = new QuadCurve();
                setUpQuadCurve((QuadCurve) shape);
                break;
            case CUBIC_CURVE:
                shape = new CubicCurve();
                setUpCubicCurve((CubicCurve) shape);
                break;
            default:
                shape = new Line();
                setUpLine((Line) shape);
        }
        shape.setStrokeWidth(3);
        shape.setFill(null);
        shape.setStroke(Color.DARKGREEN);
        getChildren().add(shape);
    }

    public void setUpQuadCurve(QuadCurve quadCurve) {
        quadCurve.startXProperty().bind(source.xProperty());
        quadCurve.startYProperty().bind(source.yProperty());
        quadCurve.controlXProperty().bind(source.xProperty());
        quadCurve.controlYProperty().bind(target.yProperty());
        quadCurve.endXProperty().bind(target.xProperty());
        quadCurve.endYProperty().bind(target.yProperty());
    }

    private void setUpLine(Line line) {
        line.startXProperty().bind(source.xProperty());
        line.startYProperty().bind(source.yProperty());
        line.endXProperty().bind(target.xProperty());
        line.endYProperty().bind(target.yProperty());
    }

    public void setUpPath(Path path) {
        path.getElements().add(createBoundMoveTo(source.xProperty(), source.yProperty()));
        path.getElements().add(createBoundLineTo(source.xProperty(), target.yProperty()));
        path.getElements().add(createBoundMoveTo(source.xProperty(), target.yProperty()));
        path.getElements().add(createBoundLineTo(target.xProperty(), target.yProperty()));
    }

    private LineTo createBoundLineTo(DoubleProperty x, DoubleProperty y) {
        LineTo lineTo = new LineTo();
        lineTo.xProperty().bind(x);
        lineTo.yProperty().bind(y);
        return lineTo;
    }

    private MoveTo createBoundMoveTo(DoubleProperty x, DoubleProperty y) {
        MoveTo moveTo = new MoveTo();
        moveTo.xProperty().bind(x);
        moveTo.yProperty().bind(y);
        return moveTo;
    }

    private void setUpCubicCurve(CubicCurve cubicCurve) {
        DoubleBinding offset = (target.xProperty().subtract(source.xProperty())).multiply(1. / 10.);
        cubicCurve.startXProperty().bind(source.xProperty());
        cubicCurve.startYProperty().bind(source.yProperty());
//         cubicCurve.controlX1Property().bind(source.xProperty().add(50));
        cubicCurve.controlX1Property().bind(source.xProperty().add(offset));
        cubicCurve.controlY1Property().bind(source.yProperty());
//        cubicCurve.controlX1Property().bind(source.xProperty().add(50));
        cubicCurve.controlX2Property().bind(source.xProperty().add(offset));
        cubicCurve.controlY2Property().bind(target.yProperty());
        cubicCurve.endXProperty().bind(target.xProperty());
        cubicCurve.endYProperty().bind(target.yProperty());
    }

    public double getLength() {
        return Math.abs(length.get());
    }

    public DoubleBinding lengthProperty() {
        return length;
    }

    public MyNodeView getSource() {
        return source;
    }

    public MyNodeView getTarget() {
        return target;
    }

    public void setEdgeType(MyTreeView.EDGE_TYPE edgeType) {
        this.edgeType = edgeType;
        drawLine();
    }

    public String getIdentifier() {
        return id;
    }

}

