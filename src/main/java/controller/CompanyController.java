/**
 * Sample Skeleton for 'companyView.fxml' Controller Class
 */

package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Category;
import model.Product;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class CompanyController {

    @FXML // fx:id="tblProduct"
    private TableView<Product> tblProduct;

    @FXML // fx:id="tcName"
    private TableColumn<Product, String> tcName;

    @FXML // fx:id="tcCategory"
    private TableColumn<Product, String> tcCategory;

    @FXML // fx:id="tcPrice"
    private TableColumn<Product, Double> tcPrice;

    @FXML // fx:id="tcQuantity"
    private TableColumn<Product, Integer> tcQuantity; // Value injected by FXMLLoader

    @FXML // fx:id="tfSearch"
    private TextField tfSearch; // Value injected by FXMLLoader

    @FXML
    private CheckBox cbLess5;

    @FXML
    private CheckBox cbMedium;

    @FXML
    private CheckBox cbMore10;

    @FXML
    private ComboBox<?> comboCategory;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    void addAction(ActionEvent event) {

    }

    @FXML
    void closeAction(ActionEvent event) {
        Platform.exit();
    }
    private ObservableList<Product> products = FXCollections.observableArrayList();


    private void getProductsFromFile() throws FileNotFoundException {
        String path = Paths.get("").toAbsolutePath().toString() + "\\src\\main\\java\\utility\\products.csv";
        Scanner scanner = new Scanner(new File(path));
        scanner.nextLine();         // pominięcie nagłówka w pliku .csv
        while (scanner.hasNextLine()) {
            String line [] = scanner.nextLine().split(";");
            products.add(new Product(Integer.valueOf(line[0]),line[1],
                    Category.valueOf(line[2]),
                            Double.valueOf(line[3]), Integer.valueOf(line[4])));
        }
    }

    private void setProductsIntoTable() {

    }

    @FXML
    void deleteAction(ActionEvent event) {

    }

    @FXML
    void filterAction(ActionEvent event) {

    }

    @FXML
    void logOutAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/loginView.fxml"));
        primaryStage.setTitle("Logowanie");             // tytuł okna
        primaryStage.setResizable(false);               // brak skalowania
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
        // zamknięcie okna logowania na obiekcie typu Stage
        Stage companyStage = (Stage) btnDelete.getScene().getWindow();
        companyStage.close();
    }

    @FXML
    void selectAction(MouseEvent event) {

    }

    @FXML
    void updateAction(ActionEvent event) {

    }

}
