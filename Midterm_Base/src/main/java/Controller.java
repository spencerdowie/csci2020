import java.io.*;
import java.net.*;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {
  // Your FXML references go here
  @FXML private Button previous, next;
  @FXML private TextField ObjectID, StreetName, PropertyClass, ShapeArea, ShapeLength;
  ObservableList<LodgingHouse> houses;
  private int cID;

  public static String summaryFilename = "summary.csv";
  public static String url = "http://csundergrad.science.uoit.ca/courses/csci2020u/data_mtle/lodging_houses.csv";

  public void initialize() {
    previous.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        OnClickPrevious();
      }
    });
    next.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        OnClickNext();
      }
    });
    // Your code to do the following goes here:
    // 1. Load the data from 'url' into a list of LodgingHouse objects
    // 2. Show the data from the first lodging house in the user interface
    // 3. Save the summary of all of the lodging house records to 'summaryFilename'
    houses = FXCollections.observableArrayList();
    try {
      String url = "http://csundergrad.science.uoit.ca/courses/csci2020u/data_mtle/lodging_houses.csv";
      URL netURL = new URL(url);

      URLConnection conn = netURL.openConnection();
      conn.setDoOutput(false);
      conn.setDoInput(true);

      InputStream inStream = conn.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

      String inLine = "";
      int i = 0;
      reader.readLine();
      while((inLine = reader.readLine()) != null)
      {
        String[] splitLine = inLine.split(",");
        houses.add(new LodgingHouse(i, splitLine[3], splitLine[4], Double.parseDouble(splitLine[8]), Double.parseDouble(splitLine[9])));
        i++;
      }
      cID = 1;
      OnClickPrevious();
      if(houses.size() > 0)
      {
        PrintWriter fileOut = new PrintWriter(new File("summaryFile.csv"));
        fileOut.println("Object ID,Street Name,Property Class,Shape Area,Shape Length");
        for (LodgingHouse house : houses)
        {
          fileOut.println(house.toString());
        }
        fileOut.close();
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public void OnClickPrevious()
  {
    cID--;
    LodgingHouse house = houses.get(cID);
    ObjectID.setText(String.valueOf(house.getObjectId()));
    StreetName.setText(house.getStreetName());
    PropertyClass.setText(house.getPropertyClass());
    ShapeArea.setText(String.valueOf(house.getShapeArea()));
    ShapeLength.setText(String.valueOf(house.getShapeLength()));
    if(cID == 0)
    {
      previous.setDisable(true);
    }
    if(next.isDisabled() && cID < houses.size() - 1)
    {
      next.setDisable(false);
    }
  }

  public void OnClickNext()
  {
    cID++;
    LodgingHouse house = houses.get(cID);
    ObjectID.setText(String.valueOf(house.getObjectId()));
    StreetName.setText(house.getStreetName());
    PropertyClass.setText(house.getPropertyClass());
    ShapeArea.setText(String.valueOf(house.getShapeArea()));
    ShapeLength.setText(String.valueOf(house.getShapeLength()));
    if(cID == houses.size() - 1)
    {
      next.setDisable(true);
    }
    if(previous.isDisabled() && cID > 0)
    {
      previous.setDisable(false);
    }
  }
  // Your code to handle the 'next' and 'previous' events goes here
}
