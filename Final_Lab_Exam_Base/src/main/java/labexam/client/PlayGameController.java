package labexam.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;

public class PlayGameController {
    @FXML private TextField rGuessField;
    @FXML private TextField guessField;
    @FXML private ListView<String> guessList;
    private boolean gameOver = false;
    private int remainingGuesses;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public  static String SERVER_ADDRESS = "localhost";
    public  static int    SERVER_PORT = 8080;

    public PlayGameController()
    {
    }

    public void startGame(int max)
    {
        rGuessField.setText(String.valueOf(remainingGuesses));

        try
        {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader( new InputStreamReader(socket.getInputStream()));

            remainingGuesses = Integer.parseInt(sendMessage("STARTGAME", max));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    private void endGame()
    {
        guessList.getItems().add("Game Over!");
        gameOver = true;
        sendMessage("ENDGAME", -1);
        try
        {
            out.close();
            in.close();
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private String sendMessage(String cmd, int val)
    {
        String line = "";
        try
        {
            out.println(cmd + " " + val);
            while (!in.ready()){}
            line = in.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return line;
    }

    public void guessBtn(ActionEvent event)
    {
        if(gameOver)
            return;

        int guess = Integer.parseInt(guessField.getText());
        String guessText = guess + " - ";
        String response = sendMessage("Guess ", guess);
        StringTokenizer tokenizer = new StringTokenizer(response);
        String cmd = tokenizer.nextToken();
        if(cmd.equalsIgnoreCase("SUCCESS"))
        {
            guessText += "Correct";
        }
        else
        {
            guessText += cmd;
            remainingGuesses = Integer.parseInt(tokenizer.nextToken());
            rGuessField.setText(String.valueOf(remainingGuesses));
        }
        guessText += " (" + remainingGuesses + " guesses remaining)";
        guessList.getItems().add(guessText);
        if(remainingGuesses <= 0)
        {
            endGame();
        }
    }
}
