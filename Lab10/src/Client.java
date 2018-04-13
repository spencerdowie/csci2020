import java.io.*;
import java.net.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Client extends Application
{
    public static String SERVER_ADDRESS = "localhost";
    public static int SERVER_PORT = 49000;
    private BorderPane root;

    public void start(Stage primaryStage)
    {
        root = new BorderPane();
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(10);
        pane.setHgap(10);

        Label usrLbl = new Label("Username:");
        Label msgLbl = new Label("Message:");

        TextField usrField = new TextField();
        TextField msgField = new TextField();

        Button sendBtn = new Button("Send");
        sendBtn.setOnAction(e->SendMessage(usrField.getText(), msgField.getText()));
        Button exitBtn = new Button("Exit");
        exitBtn.setOnAction(e->System.exit(0));

        pane.addRow(0, usrLbl, usrField);
        pane.addRow(1, msgLbl, msgField);
        pane.addRow(2, sendBtn);
        pane.addRow(3, exitBtn);

        root.setCenter(pane);
        primaryStage.setTitle("Lab 10 Client");
        primaryStage.setScene(new Scene(root, 350, 200));
        primaryStage.show();
    }

    public void SendMessage(String username, String message)
    {
        Socket socket = null;
        PrintWriter netOut = null;

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try
        {
            System.out.println("Client Sending on port " + SERVER_PORT);
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        }
        catch(UnknownHostException e)
        {
            System.err.println("Unknown host: " + SERVER_ADDRESS);
        }
        catch(IOException e)
        {
            System.err.println("IOException while connecting");
        }

        if(socket != null)
        {
            try
            {
                netOut = new PrintWriter(socket.getOutputStream());
            }
            catch(IOException e)
            {
                System.err.println("IOException while opening output stream");
            }
        }

        if(netOut != null && username != null && message != null)
        {
            netOut.write(username + " : " + message);
            netOut.flush();
        }
        else
        {
            System.err.println("Error while writing to socket");
        }

        if(netOut != null)
        {
            netOut.close();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
