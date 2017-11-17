
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

import java.util.ArrayList;

public class Presenter {

    private View view;
    private DNATranslator dnaTranslator;

    public Presenter(View view, DNATranslator dnaTranslator) {

        this.view = view;
        this.dnaTranslator = dnaTranslator;

        setUpActions();
        setUpBindings();

    }


    private void setUpBindings() {
        view.getClearButton().disableProperty().bind(view.getDnaField().textProperty().isEmpty().and(view.getOutput().textProperty().isEmpty()));
        dnaTranslator.sequenceProperty().bindBidirectional(view.getDnaField().textProperty());
        dnaTranslator.translationResultProperty().bindBidirectional(view.getOutput().textProperty());
    }

    private void setUpActions() {

        view.getSlider().valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                view.getOutput().setStyle("-fx-font-family: monospace; -fx-font-size: " + view.getSlider().getValue() + "em;");
            }
        });

        for (CheckBox frameBox : view.getCheckBoxes()) {
            frameBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    enableTranslateButton();
                }
            });
        }
        view.getDnaField().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                enableTranslateButton();
            }
        });

        view.getAllButton().setOnAction(e -> {
            for (CheckBox c : view.getCheckBoxes())
                c.setSelected(true);
        });

        view.getDnaField().setOnKeyTyped(e -> {
            enableTranslateButton();
        });

        view.getTranslateButton().setOnAction(e -> {
            cmpTranslationResult();
        });

        view.getClearButton().setOnAction(e -> {
            view.getDnaField().setText("");
            view.getOutput().setText("");
        });

    }

    private void cmpTranslationResult() {
        ArrayList<Object[]> frameInfo = new ArrayList<Object[]>();
        for (CheckBox c : view.getCheckBoxes()) {
            if (c.isSelected()) {
                int frame = view.getCheckBoxes().indexOf(c);
                boolean rev = frame > 2;
                frame = rev ? frame - 3 : frame;
                Object[] o = {frame, rev};
                frameInfo.add(o);
            }
        }
        dnaTranslator.translate(frameInfo);
    }

    private void enableTranslateButton() {
        boolean isSelected = false;
        for (CheckBox c : view.getCheckBoxes()) {
            if (c.isSelected())
                isSelected = true;
        }
        if (isSelected)
            view.getTranslateButton().setDisable(view.getDnaField().getText().length() < 3);
        else
            view.getTranslateButton().setDisable(true);
    }

}
