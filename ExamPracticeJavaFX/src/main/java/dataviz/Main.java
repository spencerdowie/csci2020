package dataviz;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        Stage primaryStage = stage;
        Canvas canvas = new Canvas();
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());

        HousingPricesLoader loader = new HousingPricesLoader();
        FileStorage storage = new FileStorage();
        Map<String, List<Integer>> map = loader.loadPrices();

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        XYChart.Series series5 = new XYChart.Series();
        series1.setName("1 bed flats");
        series2.setName("2 bed flats");
        series3.setName("2 bed houses");
        series4.setName("3 bed houses");
        series5.setName("4 bed houses");

        Set<String> keySet = map.keySet();
        for(String key : keySet)
        {
            List<Integer> price = map.get(key);
            series1.getData().add(new XYChart.Data(key, price.get(0)));
            series2.getData().add(new XYChart.Data(key, price.get(1)));
            series3.getData().add(new XYChart.Data(key, price.get(2)));
            series4.getData().add(new XYChart.Data(key, price.get(3)));
            series5.getData().add(new XYChart.Data(key, price.get(4)));
        }
        barChart.getData().addAll(series1, series2, series3, series4, series5);

        primaryStage.setTitle("Final");
        primaryStage.setScene(new Scene(barChart, 600, 400));
        primaryStage.show();

        //storage.saveData(map);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
