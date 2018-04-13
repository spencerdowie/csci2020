import javafx.collections.ObservableList;

import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
    protected Socket socket = null;
    protected BufferedReader in = null;
    protected ObservableList<String> messages;

    public ServerThread(Socket socket, ObservableList<String> msgs)
    {
        super();
        this.socket = socket;
        this.messages = msgs;

        try
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(IOException e)
        {
            System.err.println("IOException while opening socket");
        }
    }

    public void run()
    {
        try
        {
            String msg = "";
            String line;

            while((line = in.readLine()) != null)
            {
                msg += line;
            }
            socket.close();
            messages.add(msg);

            //System.out.println(msg);
        }
        catch(IOException e)
        {
            System.err.println("Error reading message");
        }
    }
}
