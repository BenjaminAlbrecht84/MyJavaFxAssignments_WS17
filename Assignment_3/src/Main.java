import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presenter.Presenter;
import view.View;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // setting up view and presenter
        View view = new View();
        Presenter presenter = new Presenter(view);
        presenter.loadFile(new File("/Users/Benjamin/git/MyJavaFxAssignments_WS17/tree-coordinates.txt"));

        // starting gui
        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MyTreeViewer");
        primaryStage.show();

    }

}
