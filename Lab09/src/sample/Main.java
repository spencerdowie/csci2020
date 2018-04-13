package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import com.google.gson.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Main extends Application {

    public NumberAxis xAxis = new NumberAxis();
    public NumberAxis yAxis = new NumberAxis();

    public String[] symbols = {"GOOG", "AMZN"};

    public LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Lab 09");
        drawLinePlot(downloadStockPrices(symbols[0]), downloadStockPrices(symbols[1]), symbols[0], symbols[1]);

        xAxis.setLabel("Days");
        yAxis.setUpperBound(1500);
        yAxis.setLowerBound(500);

        yAxis.setAutoRanging(true);

        primaryStage.setScene(new Scene(lineChart, 800, 600));
        primaryStage.show();
    }

    public ObservableList<Float> downloadStockPrices(String symbol)
    {
        ObservableList<Float> outData = FXCollections.observableArrayList();
        try
        {
            String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&apikey=V3TYFURMZMBL59ZN\n";
            URL stockURL = new URL(url);
            BufferedReader reader = new BufferedReader( new InputStreamReader(stockURL.openStream(), Charset.forName("UTF-8")));
            String jsonData = "", line = "";
            while((line = reader.readLine()) != null)
            {
                //System.out.println(line);
                jsonData += line;
            }
            JsonParser parser = new JsonParser();
            JsonObject data = parser.parse(jsonData).getAsJsonObject();
            JsonObject items = data.getAsJsonObject("Time Series (Daily)");
            for (Map.Entry<String, JsonElement> entry : items.entrySet()) {
                String date = entry.getKey();
                Float closePrice = entry.getValue().getAsJsonObject().get("4. close").getAsFloat();
                //System.out.printf("%s : $%.2f \n", date, closePrice);
                outData.add(closePrice);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return outData;
    }

    public void drawLinePlot(ObservableList<Float> a, ObservableList<Float> b, String symbolA, String symbolB)
    {
        XYChart.Series seriesA = plotLine(a);
        seriesA.setName(symbolA);

        XYChart.Series seriesB = plotLine(b);
        seriesB.setName(symbolB);

        lineChart.getData().addAll(seriesA, seriesB);
    }

    public XYChart.Series plotLine(ObservableList<Float> inData)
    {
        XYChart.Series series = new XYChart.Series();
        ObservableList<XYChart.Data> dataA = FXCollections.observableArrayList();
        for(int i = 0; i < inData.size(); i++)
        {
            dataA.add(new XYChart.Data(i, inData.get(i)));
            System.out.println(inData.get(i));
        }
        series.getData().addAll(dataA);
        return series;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
