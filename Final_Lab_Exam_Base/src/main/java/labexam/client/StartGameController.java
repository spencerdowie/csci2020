package labexam.client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class StartGameController {
  @FXML private TextField maxField;

  public void initialize() {
  }

  // called when the Start Game button is clicked
  public void startGame(ActionEvent event) {
      int max = Integer.parseInt(maxField.getText());

      try {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getClassLoader().getResource("playgame.fxml"));
        Parent root = loader.load();
        PlayGameController controller = loader.getController();
        Scene scene = new Scene(root, 600, 600);
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
        controller.startGame(max);
      } catch (IOException e) {
        e.printStackTrace();
      }
  }

  public void quit(ActionEvent event) {
    System.exit(0);
  }
}
