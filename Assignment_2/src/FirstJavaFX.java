import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FirstJavaFX extends Application {

    public static void main(String[] args) {

        System.out.println("Entered main() " + Platform.isFxApplicationThread());
        Application.launch(args);
        System.out.println("Exit main() " + Platform.isFxApplicationThread());

    }

    public void init() {

        System.out.println("Entered init() " + Platform.isFxApplicationThread());

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        System.out.println("Entered start() " + Platform.isFxApplicationThread());

        // calculating window size depending on screen resolution
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double width = primaryScreenBounds.getWidth() / 5;
        double height = primaryScreenBounds.getHeight() / 5;

        // setting up the scene
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, width, height);

        // setting up the text
        root.setCenter(new Text("Hello World!"));

        // setting up the button
        Button byeButton = new Button("Bye!");
        byeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Handling Bye button... " + Platform.isFxApplicationThread());
                Platform.exit();
            }
        });
        ButtonBar buttonBar = new ButtonBar();
        ButtonBar.setButtonData(byeButton, ButtonBar.ButtonData.LEFT);
        buttonBar.getButtons().add(byeButton);
        root.setBottom(buttonBar);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void stop() {

        System.out.println("Entered stop() " + Platform.isFxApplicationThread());

    }

}
