package frank.lucassen.java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Locale.setDefault(Locale.ENGLISH);

        RootPane root = new RootPane();
        Scene scene = new Scene(root, 1300, 400);

        stage.setTitle("Questionnaire");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}