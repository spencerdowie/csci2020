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

        XYChart.Series[] series = new XYChart.Series[5];
        series[0].setName("1 bed flats");
        series[1].setName("2 bed flats");
        series[2].setName("2 bed houses");
        series[3].setName("3 bed houses");
        series[4].setName("4 bed houses");

        Set<String> keySet = map.keySet();
        int i = 0;
        for(String key : keySet)
        {
            List<Integer> price = map.get(key);
            for (int j = 0; j < series.length; j++)
            {
                series[i].getData().add(new XYChart.Data(i, price.get(i)));
            }
            i++;
        }
        barChart.getData().addAll(series);

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
