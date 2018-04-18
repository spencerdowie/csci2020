package labexam.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
      FXMLLoader loader = new FXMLLoader();

      URL location = getClass().getClassLoader().getResource("startgame.fxml");
      Parent root = loader.load(location);
      Scene scene = new Scene(root, 500, 200);

      primaryStage.setTitle("Number Guesser");
      primaryStage.setScene(scene);
      primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
