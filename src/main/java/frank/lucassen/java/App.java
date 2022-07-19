package frank.lucassen.java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

/**
 * JavaFX App
 */
public class App extends Application {
    public static int APP_WIDTH = 1300;
    public static int APP_HEIGHT = 400;

    @Override
    public void start(Stage stage) {
        Locale.setDefault(Locale.ENGLISH);

        RootPane root = new RootPane();
        Scene scene = new Scene(root, APP_WIDTH, APP_HEIGHT);

        stage.setTitle("Questionnaire");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}