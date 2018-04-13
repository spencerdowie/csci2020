package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class Controller {
    @FXML private TableView<StudentRecord> MarkTable;

    public Controller()
    {
        //MarkTable.setItems(DataSource.getAllMarks());
    }
}
