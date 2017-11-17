import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class View extends BorderPane {

    private TextArea output;
    private TextField dnaField;
    private ArrayList<CheckBox> checkBoxes;
    private Button translateButton, clearButton, allButton;
    private Slider slider;

    public View() {

        dnaField = new TextField();
        setTop(dnaField);

        output = new TextArea();
        output.setWrapText(true);
        output.setStyle("-fx-font-family: monospace");
        setCenter(output);

        VBox frameBoxes = new VBox();
        checkBoxes = new ArrayList<CheckBox>();
        for (int i = 1; i < 7; i++) {

            CheckBox frameBox = new CheckBox();
            char c = i < 4 ? '+' : '-';
            int frame = i < 4 ? i : i - 3;
            frameBox.setText(c + "" + frame);
            frameBox.setStyle("-fx-font-family: monospace");

            frameBoxes.getChildren().add(frameBox);
            checkBoxes.add(frameBox);

        }
        allButton = new Button("All");
        frameBoxes.getChildren().add(allButton);
        setRight(frameBoxes);

        HBox bottomBox = new HBox();
        clearButton = new Button("Clear");
        bottomBox.getChildren().add(clearButton);
        translateButton = new Button("Translate!");
        translateButton.setDisable(true);
        bottomBox.getChildren().add(translateButton);
        slider = new Slider(0.5,1.5,1);
        bottomBox.getChildren().add(slider);
        setBottom(bottomBox);

    }

    public Slider getSlider() {
        return slider;
    }

    public TextArea getOutput() {
        return output;
    }

    public Button getAllButton() {
        return allButton;
    }

    public TextField getDnaField() {
        return dnaField;
    }

    public ArrayList<CheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    public Button getTranslateButton() {
        return translateButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

}
