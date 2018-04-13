import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class Server extends Application{
    public static Server instance;
    public static Server getInstance()
    {
        if(instance == null)
        {
            instance = new Server();
        }
        return instance;
    }

    protected  ServerSocket serverSocket = null;
    public static int SERVER_PORT = 49000;
    TableView<String> table;
    ObservableList<String> messages;
    BorderPane root;

    public void start(Stage primaryStage)
    {
        messages = FXCollections.observableArrayList();
        root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));

        table = new TableView();
        table.setItems(messages);

        TableColumn<String, String> column = new TableColumn<>();
        column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        table.getColumns().add(column);

        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(e->System.exit(0));

        root.setCenter(table);
        root.setBottom(exitBtn);

        primaryStage.setTitle("Lab 10 Server");
        primaryStage.setScene(new Scene(root, 300, 400));
        primaryStage.show();
        StartServer();
    }

    public void addMessage(String msg)
    {
        messages.add(msg);
        table.setItems(messages);
    }

    public void StartServer()
    {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server Listening on port " + SERVER_PORT);

            while (true) {
                ServerThread serverThread = new ServerThread(serverSocket.accept(), messages);
                serverThread.run();
            }
        } catch (Exception e) {
            System.err.println("Error creating server connection");
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
