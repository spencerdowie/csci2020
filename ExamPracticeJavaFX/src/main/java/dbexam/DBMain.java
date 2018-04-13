package dbexam;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DBMain extends Application {

    private static int width = 800, height = 600;
    private DBManager manager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
		
        FXMLLoader loader = new FXMLLoader(getClass().getResource("db-main.fxml"));
        Parent root = loader.load();

        //Give our controller a reference to the stage for file choosing, exiting, etc.
        ((DBMainController)loader.getController()).setStage(primaryStage);

        primaryStage.setTitle("CSCI 2020U - Super Lit Exam Practice");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();

        //Initialize persistence services.
        manager = DBManager.getInstance();
        manager.clearProducts();
    }

    @Override
    public void stop() {
        //Clean up persistence services.
        manager.destroy();
    }
}
