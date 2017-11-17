import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        View view = new View();
        new Presenter(view, new DNATranslator());
        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MyDNATranslatorFX");
        primaryStage.show();

    }

}
