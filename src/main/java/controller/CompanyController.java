/**
 * Sample Skeleton for 'companyView.fxml' Controller Class
 */

package controller;

import javafx.application.Platform;
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

import java.io.IOException;

public class CompanyController {

    @FXML // fx:id="tblProduct"
    private TableView<?> tblProduct; // Value injected by FXMLLoader

    @FXML // fx:id="tcName"
    private TableColumn<?, ?> tcName; // Value injected by FXMLLoader

    @FXML // fx:id="tcCategory"
    private TableColumn<?, ?> tcCategory; // Value injected by FXMLLoader

    @FXML // fx:id="tcPrice"
    private TableColumn<?, ?> tcPrice; // Value injected by FXMLLoader

    @FXML // fx:id="tcQuantity"
    private TableColumn<?, ?> tcQuantity; // Value injected by FXMLLoader

    @FXML // fx:id="tfSearch"
    private TextField tfSearch; // Value injected by FXMLLoader

    @FXML // fx:id="cbLess5"
    private CheckBox cbLess5; // Value injected by FXMLLoader

    @FXML // fx:id="cbMedium"
    private CheckBox cbMedium; // Value injected by FXMLLoader

    @FXML // fx:id="cbMore10"
    private CheckBox cbMore10; // Value injected by FXMLLoader

    @FXML // fx:id="comboCategory"
    private ComboBox<?> comboCategory; // Value injected by FXMLLoader

    @FXML // fx:id="btnUpdate"
    private Button btnUpdate; // Value injected by FXMLLoader

    @FXML // fx:id="btnDelete"
    private Button btnDelete; // Value injected by FXMLLoader

    @FXML
    void addAction(ActionEvent event) {

    }

    @FXML
    void closeAction(ActionEvent event) {
        Platform.exit();
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
