package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main
 *
 * This class loads the user interface, and gains access to the UI's
 * controller class.
 **/
public class Main extends Application implements FileModifiedListener {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = (Parent)loader.load();

        Controller controller = (Controller)loader.getController();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
