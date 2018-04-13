package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.PieChart;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException
    {
        Stage primaryStage = stage;

        Group root = new Group();

        Canvas canvas = new Canvas();
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());

        String inFile = "weatherwarnings-2015.csv";
        String deliminator = ",";

        BufferedReader br = new BufferedReader(new FileReader(inFile));
        Map<String, Long> lineList = br.lines().map(Object::toString).collect(Collectors.groupingBy(s -> s.split(deliminator)[5], Collectors.counting()));
        br.close();

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for(Map.Entry<String, Long> e : lineList.entrySet())
        {
            data.add( new PieChart.Data(e.getKey(), e.getValue()));
            System.out.println(e.getKey() + " : " + e.getValue());
        }
        final PieChart chart = new PieChart(data);
        chart.setLegendSide(Side.LEFT);
        chart.setLabelsVisible(false);

        root.getChildren().add(chart);
        primaryStage.setTitle("Lab 07");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
