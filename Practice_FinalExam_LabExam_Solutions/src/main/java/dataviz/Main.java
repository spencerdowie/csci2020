package dataviz;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.util.*;

public class Main extends Application {
    private Canvas canvas = null;
    private int width = 1100;
    private int height = 500;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        primaryStage.setTitle("DataViz v1.0");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.show();

        canvas = new Canvas();
        canvas.setWidth(width);
        canvas.setHeight(height);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        HousingPricesLoader loader = new HousingPricesLoader();
        Map<String,List<Integer>> housingPrices = loader.loadPrices();

        drawBarChart(gc,
                     housingPrices.get("1 bed flats"),
                     housingPrices.get("2 bed flats"),
                     housingPrices.get("2 bed houses"),
                     housingPrices.get("3 bed houses"),
                     housingPrices.get("4 bed houses"));
        drawLegend(gc);

        FileStorage.saveData(housingPrices);
    }

    private void drawLegend(GraphicsContext gc) {
        int left = 900;
        int top = 100;
        int barHeight = 25;
        int barWidth = 40;
        int itemHeight = 40;

        Font font = new Font("Arial", 12);
        gc.setFont(font);

        gc.setStroke(Color.BLACK);
        gc.strokeRect(left, top, 160, (itemHeight * 5) + 10);

        int currentX = left + 10;
        int currentY = top + 10;

        gc.setFill(Color.RED);
        gc.fillRect(currentX, currentY, barWidth, barHeight);
        gc.strokeRect(currentX, currentY, barWidth, barHeight);
        gc.setFill(Color.BLACK);
        gc.fillText("1 bed flats", currentX + barWidth + 20, currentY + 15);

        currentY += itemHeight;
        gc.setFill(Color.BLUE);
        gc.fillRect(currentX, currentY, barWidth, barHeight);
        gc.strokeRect(currentX, currentY, barWidth, barHeight);
        gc.setFill(Color.BLACK);
        gc.fillText("2 bed flats", currentX + barWidth + 20, currentY + 15);

        currentY += itemHeight;
        gc.setFill(Color.ORANGE);
        gc.fillRect(currentX, currentY, barWidth, barHeight);
        gc.strokeRect(currentX, currentY, barWidth, barHeight);
        gc.setFill(Color.BLACK);
        gc.fillText("2 bed houses", currentX + barWidth + 20, currentY + 15);

        currentY += itemHeight;
        gc.setFill(Color.GREEN);
        gc.fillRect(currentX, currentY, barWidth, barHeight);
        gc.strokeRect(currentX, currentY, barWidth, barHeight);
        gc.setFill(Color.BLACK);
        gc.fillText("3 bed houses", currentX + barWidth + 20, currentY + 15);

        currentY += itemHeight;
        gc.setFill(Color.YELLOW);
        gc.fillRect(currentX, currentY, barWidth, barHeight);
        gc.strokeRect(currentX, currentY, barWidth, barHeight);
        gc.setFill(Color.BLACK);
        gc.fillText("4 bed houses", currentX + barWidth + 20, currentY + 15);
    }

    private void drawBarChart(GraphicsContext gc,
                              List<Integer> series1,
                              List<Integer> series2,
                              List<Integer> series3,
                              List<Integer> series4,
                              List<Integer> series5) {
        int itemWidth = 58;
        int barWidth = 10;
        int bottom = height - 50;
        int left = 50;
        float maxValue = 800f;
        int currentX = left + 5;
        int maxHeight = height - 100;

        for (int i = 0; i < series1.size(); i++) {
            int year = 2002 + i;

            gc.setFill(Color.BLACK);
            gc.fillText("" + (2002 + i), currentX, bottom + 20);

            int barHeight = (int)(series1.get(i)/maxValue * maxHeight);
            gc.setFill(Color.RED);
            gc.fillRect(currentX, bottom-barHeight, barWidth, barHeight);

            barHeight = (int)(series2.get(i)/maxValue * maxHeight);
            gc.setFill(Color.BLUE);
            gc.fillRect(currentX+barWidth, bottom-barHeight, barWidth, barHeight);

            barHeight = (int)(series3.get(i)/maxValue * maxHeight);
            gc.setFill(Color.ORANGE);
            gc.fillRect(currentX+(2*barWidth), bottom-barHeight, barWidth, barHeight);

            barHeight = (int)(series4.get(i)/maxValue * maxHeight);
            gc.setFill(Color.GREEN);
            gc.fillRect(currentX+(3*barWidth), bottom-barHeight, barWidth, barHeight);

            barHeight = (int)(series5.get(i)/maxValue * maxHeight);
            gc.setFill(Color.YELLOW);
            gc.fillRect(currentX+(4*barWidth), bottom-barHeight, barWidth, barHeight);

            currentX += itemWidth;
        }

        gc.setStroke(Color.BLACK);
        gc.strokeLine(left, bottom, left + (itemWidth * 14), bottom);
        gc.strokeLine(left, bottom, left, bottom-maxHeight);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
