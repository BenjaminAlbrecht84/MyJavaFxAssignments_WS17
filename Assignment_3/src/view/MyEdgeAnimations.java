package view;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MyEdgeAnimations {

    public static void setUpAnimationOne(MyTreeView treeView) {

        Collection<MyEdgeView> edgeViews = treeView.getEdgeViews().values();
        ParallelTransition parallelTransition = new ParallelTransition();
        ArrayList<Node> animationNodes = new ArrayList<>();

        for (MyEdgeView e : edgeViews) {
            Object[] animationResult = e.setUpAnimation(MyTreeView.NODE_RADIUS, Color.GOLD, Color.GOLD, Duration.seconds(2));
            parallelTransition.getChildren().add((Animation) animationResult[0]);
            treeView.getAnimationGroup().getChildren().add((Node) animationResult[1]);
            animationNodes.add((Node) animationResult[1]);
        }

        parallelTransition.setOnFinished(e -> {
            treeView.getAnimationGroup().getChildren().removeAll(animationNodes);
        });

        parallelTransition.play();
    }

    public static void setUpAnimationTwo(MyTreeView treeView) {

        Duration totalDuration = Duration.seconds(3);

        ParallelTransition parallelTransition = new ParallelTransition();
        Group animationNodes = new Group();
        for (MyRootPathView path : treeView.getRootPathViews().values()) {

            SequentialTransition sequentialTransition = new SequentialTransition();
            for (MyEdgeView e : path.getEdges()) {

                // creating path transition for each edge
                int level = path.getEdges().indexOf(e);
                int radius = (path.getEdges().size() - level + 1) * MyTreeView.NODE_RADIUS;
                Duration duration = cmpDurations(totalDuration, path.getLength(), e);
                Object[] animationResult = e.setUpAnimation(radius, Color.GOLD, Color.DARKRED, duration);

                // adding animations to pane
                animationNodes.getChildren().add((Node) animationResult[1]);

                // adding transition to sequential transition
                sequentialTransition.getChildren().add((Animation) animationResult[0]);

            }
            parallelTransition.getChildren().add(sequentialTransition);

        }

        treeView.getAnimationGroup().getChildren().addAll(animationNodes);
        parallelTransition.setOnFinished(e -> {
            treeView.getAnimationGroup().getChildren().removeAll(animationNodes);
        });
        parallelTransition.play();

    }

    private static Duration cmpDurations(Duration totalDuration, double totalLength, MyEdgeView e) {

        double length = e.getLength();
        double p = length / totalLength;
        double s = totalDuration.toSeconds() * p;

        return Duration.seconds(s);

    }
}
