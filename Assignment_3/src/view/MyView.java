package view;

import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import presenter.Presenter;

import java.util.ArrayList;

public class MyView extends BorderPane {

    private Presenter presenter;
    private Button animation1, animation2;
    private ToggleGroup toggleGroup;
    private MyTreeView treeView;
    private Pane treePane;
    private ScrollPane scrollPane;

    public MyView() {
        settingUpCenter();
        settingUpRight();
    }

    private void settingUpRight() {

        // creating radio buttons
        toggleGroup = new ToggleGroup();
        String[] buttonNames = {"Straight Edges", "Angular Edges", "Quad Curves", "Cubic Curves"};
        ArrayList<RadioButton> buttons = new ArrayList<>();
        for (String s : buttonNames)
            buttons.add(createRadioButton(s, toggleGroup, s.startsWith("Straight")));

        // creating animation buttons
        animation1 = new Button("Animate 1");
        animation2 = new Button("Animate 2");

        // adding buttons
        VBox radioBox = new VBox();
        radioBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));
        radioBox.setPadding(new Insets(10, 10, 10, 10));
        radioBox.setSpacing(5);
        for (RadioButton b : buttons)
            radioBox.getChildren().add(b);

        VBox buttonBox = new VBox();
        buttonBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));
        buttonBox.setPadding(new Insets(10, 10, 10, 10));
        buttonBox.setSpacing(5);
        buttonBox.getChildren().add(animation1);
        buttonBox.getChildren().add(animation2);

        VBox rightBox = new VBox(radioBox, buttonBox);
        rightBox.setSpacing(10);

        setRight(rightBox);
    }

    private RadioButton createRadioButton(String name, ToggleGroup group, boolean selected) {
        RadioButton button = new RadioButton(name);
        button.setToggleGroup(group);
        button.setSelected(selected);
        return button;
    }

    private void settingUpCenter() {
        treeView = new MyTreeView(this);
        treePane = new Pane();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        treePane.setPrefSize(primaryScreenBounds.getWidth() / 2, primaryScreenBounds.getHeight() / 2);
        treePane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));
        setMargin(treePane, new Insets(5, 5, 5, 5));
        treePane.getChildren().addAll(treeView);
        treePane.setMinSize(100, 100);
        setCenter(treePane);
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public MyTreeView getTreeView() {
        return treeView;
    }

    public ToggleGroup getToggleGroup() {
        return toggleGroup;
    }

    public Pane getTreePane() {
        return treePane;
    }

    public Button getAnimation1() {
        return animation1;
    }

    public Button getAnimation2() {
        return animation2;
    }

    public void nodeDoubleClicked(MyNodeView nodeView) {
        presenter.nodeDoubleClicked(nodeView);
    }
}
