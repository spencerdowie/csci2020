package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import jdk.jfr.Category;

import java.util.Locale;

public class Main extends Application {
    private static double[] avgHousingPricesByYear = {
            247381.0,264171.4,287715.3,294736.1,
            308431.4,322635.9,340253.0,363153.7
    };
    private static double[] avgCommercialPricesByYear = {
            1121585.3,1219479.5,1246354.2,1295364.8,
            1335932.6,1472362.0,1583521.9,1613246.3
    };
    private static String[] ageGroups = {
            "18-25", "26-35", "36-45", "46-55", "56-65", "65+"
    };
    private static int[] purchasesByAgeGroup = {
            648, 1021, 2453, 3173, 1868, 2247
    };
    private static Color[] pieColours = {
            Color.AQUA, Color.GOLD, Color.DARKORANGE,
            Color.DARKSALMON, Color.LAWNGREEN, Color.PLUM
    };

    @Override
    public void start(Stage stage) throws Exception{
        Stage primaryStage = stage;

        Group root = new Group();

        Canvas canvas = new Canvas();
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for(int i = 0; i < ageGroups.length; i++)
        {
            pieData.add(new PieChart.Data(ageGroups[i], purchasesByAgeGroup[i]));
        }
        final PieChart pieChart = new PieChart(pieData);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        for (int i = 0; i < avgHousingPricesByYear.length; i++)
        {
            series1.getData().add(new XYChart.Data(i, avgCommercialPricesByYear[i]));
            series2.getData().add(new XYChart.Data(i, avgHousingPricesByYear[i]));

        }
        barChart.getData().addAll(series1, series2);



        root.getChildren().add(pieChart);
        //root.getChildren().add(barChart);
        primaryStage.setTitle("Lab 06");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
