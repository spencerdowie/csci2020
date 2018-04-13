package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {
    TableView<StudentRecord> table;
    BorderPane layout;
    String currentFilename;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Lab 08");

        MenuItem newItem = new MenuItem("New");
        newItem.setOnAction(e -> clear());

        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("."));
                File file = fileChooser.showOpenDialog(primaryStage);
                if(file != null)
                    load(file);
            }
        });


        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(e -> save(new File(currentFilename)));

        MenuItem saveAsItem = new MenuItem("Save As");
        saveAsItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("."));
                save(fileChooser.showSaveDialog(primaryStage));
            }
        });


        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> System.exit(0));

        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(newItem, openItem, saveItem, saveAsItem, exitItem);
        MenuBar bar = new MenuBar();
        bar.getMenus().add(fileMenu);

        table = new TableView<>();
        table.setItems(DataSource.getAllMarks());
        table.setEditable(false);

        TableColumn<StudentRecord, String> sidColumn = null;
        sidColumn = new TableColumn<>("SID");
        sidColumn.setMinWidth(100);
        sidColumn.setCellValueFactory(new PropertyValueFactory<>("SID"));

        TableColumn<StudentRecord, Double> assignmentColumn = null;
        assignmentColumn = new TableColumn<>("Assignments");
        assignmentColumn.setMinWidth(100);
        assignmentColumn.setCellValueFactory(new PropertyValueFactory<>("Assignments"));

        TableColumn<StudentRecord, Double> midtermColumn = null;
        midtermColumn = new TableColumn<>("Midterm");
        midtermColumn.setMinWidth(100);
        midtermColumn.setCellValueFactory(new PropertyValueFactory<>("Midterm"));

        TableColumn<StudentRecord, Double> finalExamColumn = null;
        finalExamColumn = new TableColumn<>("Final Exam");
        finalExamColumn.setMinWidth(100);
        finalExamColumn.setCellValueFactory(new PropertyValueFactory<>("FinalExam"));

        TableColumn<StudentRecord, Double> finalColumn = null;
        finalColumn = new TableColumn<>("Final Mark");
        finalColumn.setMinWidth(100);
        finalColumn.setCellValueFactory(new PropertyValueFactory<>("FinalMark"));

        TableColumn<StudentRecord, Character> LetterGradeColumn = null;
        LetterGradeColumn = new TableColumn<>("Letter Column");
        LetterGradeColumn.setMinWidth(100);
        LetterGradeColumn.setCellValueFactory(new PropertyValueFactory<>("LetterGrade"));

        table.getColumns().addAll(sidColumn, assignmentColumn, midtermColumn, finalExamColumn, finalColumn, LetterGradeColumn);

        GridPane summary = new GridPane();
        summary.setPadding(new Insets(10, 10, 10, 10));
        summary.setVgap(10);
        summary.setHgap(10);

        Label sidLabel = new Label("SID: ");
        summary.add(sidLabel, 0, 0);
        TextField sidField = new TextField();
        sidField.setEditable(true);
        summary.add(sidField, 1, 0);

        Label midtermLabel = new Label("Midterm: ");
        summary.add(midtermLabel, 0, 1);
        TextField midtermField = new TextField();
        sidField.setEditable(true);
        summary.add(midtermField, 1, 1);

        Label assignmentsLabel = new Label("Assignments: ");
        summary.add(assignmentsLabel, 2, 0);
        TextField assignmentsField = new TextField();
        sidField.setEditable(true);
        summary.add(assignmentsField, 3, 0);

        Label FinalLabel = new Label("Final Exam: ");
        summary.add(FinalLabel, 2, 1);
        TextField FinalField = new TextField();
        sidField.setEditable(true);
        summary.add(FinalField, 3, 1);

        Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                table.getItems().add(new StudentRecord(sidField.getText(), Float.parseFloat(assignmentsField.getText()), Float.parseFloat(midtermField.getText()), Float.parseFloat(FinalField.getText())));
            }
        });
        summary.add(addButton, 1, 3);

        layout = new BorderPane();
        layout.setTop(bar);
        layout.setCenter(table);
        layout.setBottom(summary);

        primaryStage.setScene(new Scene(layout, 600, 500));
        primaryStage.show();
    }

    public void clear()
    {
        table.setItems(FXCollections.observableArrayList());
    }

    public File changeFilename(Stage stage)
    {
        FileChooser fileChooser = new FileChooser();
        return fileChooser.showSaveDialog(stage);
    }

    public void load(File file)
    {
        try
        {
            if(file.exists())
            {
                BufferedReader inFile = new BufferedReader(new FileReader(file));
                String line = inFile.readLine();
                ObservableList<StudentRecord> newRecords = FXCollections.observableArrayList();
                while((line = inFile.readLine()) != null)
                {
                    String[] splitLine = line.split(",");
                    newRecords.add(new StudentRecord(splitLine[0], Float.parseFloat(splitLine[1]), Float.parseFloat(splitLine[2]), Float.parseFloat(splitLine[3])));
                }
                table.setItems(newRecords);
            }
        }
        catch (IOException e){e.printStackTrace();}
    }

    public void save(File file)
    {
        if(file != null)
        {
            try
            {
                if(!file.exists())
                {
                    file.createNewFile();
                }
                PrintWriter outStream = new PrintWriter(file);
                outStream.println("SID,Assignments,Midterm,Final Exam");
                for(StudentRecord record : table.getItems())
                {
                    outStream.println(record.toString());
                }
                outStream.close();
            }
            catch (IOException e){e.printStackTrace();}
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
