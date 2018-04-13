package dbexam;

import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DBMainController {

    @FXML
    private TableView<Product> tblProducts;

    @FXML
    private TextField txtProductKeyword;

    private DBManager manager;
    private Stage stage;

    private ObservableList<Product> currentProducts;

    public void initialize() {
        manager = DBManager.getInstance();

        //Set up our table columns.
        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.prefWidthProperty().bind(tblProducts.widthProperty().divide(4));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.prefWidthProperty().bind(tblProducts.widthProperty().divide(6));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, String> descColumn = new TableColumn<>("Description");
        descColumn.prefWidthProperty().bind(tblProducts.widthProperty().multiply(7.0 / 12.0));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        tblProducts.getColumns().add(nameColumn);
        tblProducts.getColumns().add(priceColumn);
        tblProducts.getColumns().add(descColumn);

        currentProducts = FXCollections.observableArrayList();
        tblProducts.setItems(currentProducts);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void filterProducts() {
        //To update our table/filter, we just need to clear out our current products,
        //retrieve objects matching the current filter text from the database,
        //and use those objects to repopulate the product list referenced by the table.

        currentProducts.clear();
        currentProducts.addAll(manager.findProducts(txtProductKeyword.getText()));
    }

    public void loadProducts() {

        //Create a FileChooser dialog with an *.xml extension filter.
        FileChooser chooser = new FileChooser();

        chooser.setInitialDirectory(new File("."));
        chooser.setTitle("Choose an XML products document...");

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "XML Files (*.xml)",
                        "*.xml"));

        File selected = chooser.showOpenDialog(stage);

        if(selected != null) {
            try {
                //Create our XML parsing utilities.
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

                //Generate the DOM.
                Document doc = docBuilder.parse(selected);
                doc.getDocumentElement().normalize();

                //Get all products.
                NodeList products = doc.getElementsByTagName("product");

                for(int i = 0; i < products.getLength(); ++i) {

                    //For each product, we need to extract the UPC code, name,
                    //price, and description.
                    Element currentProduct = (Element)products.item(i);

                    long upc = Long.parseLong(getTagText("upc", currentProduct));
                    String name = getTagText("name", currentProduct);
                    double price = Double.parseDouble(getTagText("price", currentProduct));
                    String description = getTagText("desc", currentProduct);

                    Product newProduct = new Product(upc, name, price, description);

                    //Persist every new product to the database.
                    manager.saveProduct(newProduct);
                }

                filterProducts();

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getTagText(String tagName, Element e) {
        NodeList tags = e.getElementsByTagName(tagName);

        if(tags.getLength() > 0) {
            return tags.item(0).getTextContent();
        }

        return null;
    }

    public void exit() {
        stage.close();
    }
}
