package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Category;
import model.Product;
import utility.UserData;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CompanyController {
    @FXML
    private TableView<Product> tblProducts;
    @FXML
    private TableColumn<Product, Integer> tcId;
    @FXML
    private TableColumn<Product, String> tcName;
    @FXML
    private TableColumn<Product, String> tcCategory;
    @FXML
    private TableColumn<Product, Double> tcPrice;
    @FXML
    private TableColumn<Product, Integer> tcQuantity;
    @FXML
    private TextField tfSearch;
    @FXML
    private CheckBox cbLess5;
    @FXML
    private CheckBox cbMedium;
    @FXML
    private CheckBox cbMore10;
    @FXML
    private ComboBox<Category> comboCategory;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        Parent parent = FXMLLoader.load(getClass().getResource("/view/loginView.fxml"));
        primaryStage.setTitle("Logowanie");             // tytuł okna
//        primaryStage.setResizable(false);               // brak skalowania
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
        // zamknięcie okna logowania na obiekcie typu Stage
        Stage companyStage = (Stage) btnDelete.getScene().getWindow();
        companyStage.close();
    }

    @FXML
    void closeAction(ActionEvent event) {

        Platform.exit();
    }

    private ObservableList<Product> products = FXCollections.observableArrayList();

    private String path = Paths.get("").toAbsolutePath().toString() +
            "\\src\\main\\java\\utility\\products.csv";

    private void getProductsFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        scanner.nextLine(); // pominięcie nagłówka w pliku .csv
        while (scanner.hasNextLine()){
            String line [] = scanner.nextLine().split(";");
            products.add(new Product(
                    Integer.valueOf(line[0]),line[1],
                    Arrays.stream(Category.values())                                        // Category []
                            .filter(category -> category.getCategoryName().equals(line[2])) // filtrowanie po nazwie kategorii
                            .findAny()                                                      // Optional<Category>
                            .get(),                                                          // Category
                    Double.valueOf(line[3].replace(",",".")),Integer.valueOf(line[4])));
        }
    }

    private void setProductsIntoTable() {
        // konfiguracja wartości wporwadzanych do tabeli z pól klasy modelu Product
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        // przekazanie wartości do tabeli z ObservableList
        tblProducts.setItems(products);
    }

    public void saveProductsToFile() throws IOException {
        PrintWriter pw = new PrintWriter(new File(path));
        pw.println("id;nazwa;kategoria;cena;lość");
        for (Product product : products) {
            pw.println(
                    String.format(
                            Locale.US,
                            "%d;%s;%s;%s;%d",
                            product.getId(),
                            product.getName(),
                            product.getCategory().getCategoryName(),
                            String.format("%.2f",product.getPrice()).replace(".",","),
                            product.getQuantity()
                    ));
        }
        pw.close();
    }

    public void initialize() throws FileNotFoundException {
        getProductsFromFile();
        setProductsIntoTable();
        // wprowadzenie kategorii
        comboCategory.setItems(FXCollections.observableArrayList(Category.values()));
    }

    @FXML
    void addAction(ActionEvent event) throws IOException {
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Dodaj produkt");
        dialog.setHeaderText("Dodaj produkt");
        // ustawienie kontrolek
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tfProductName = new TextField();
        tfProductName.setPromptText("nazwa");
        ComboBox<Category> comboProductCategory = new ComboBox<>();
        comboProductCategory.setItems(FXCollections.observableArrayList(Category.values()));
        comboProductCategory.setPromptText("kategoria");
        TextField tfProductPrice = new TextField();
        tfProductPrice.setPromptText("cena");
        TextField tfProductQuantity = new TextField();
        tfProductQuantity.setPromptText("ilość");

        grid.add(tfProductName, 0, 0);
        grid.add(comboProductCategory, 0, 1);
        grid.add(tfProductPrice, 0, 2);
        grid.add(tfProductQuantity, 0, 3);

        dialog.getDialogPane().setContent(grid);
        // przyciski
        ButtonType btnOk = new ButtonType("Dodaj", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnOk);

        Optional<Product> productOpt = dialog.showAndWait();
        if (productOpt.isPresent()) {
            if (!tfProductPrice.getText().matches("[0-9]+\\.{0,1}[0-9]{0,2}") ||
                    !tfProductQuantity.getText().matches("[0-9]+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd danych");
                alert.setHeaderText("Błąd danych. Produkt nie został dodany!");
                alert.showAndWait();
            } else {
                products.add(new Product(products.stream().mapToInt(p -> p.getId()).max().getAsInt() + 1,
                        tfProductName.getText(), comboProductCategory.getValue(),
                        Double.valueOf(tfProductPrice.getText()), Integer.valueOf(tfProductQuantity.getText())));
                saveProductsToFile();
            }
        }
    }

    @FXML
    void deleteAction(ActionEvent event) throws IOException {
        Product product = tblProducts.getSelectionModel().getSelectedItem();
        if (product != null) {
            products.remove(product);
            saveProductsToFile();
            setProductsIntoTable();
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);
        }
    }

    @FXML
    void selectAction(MouseEvent event) {
        Product product = tblProducts.getSelectionModel().getSelectedItem();
        if (product != null) {
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        } else {
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);
        }
    }

    @FXML
    void filterAction(ActionEvent event) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList(
                products.stream().
                        filter(product -> product.getName().toLowerCase().contains(tfSearch.getText().toLowerCase()))
                .collect(Collectors.toList()));
        tblProducts.setItems(filteredProducts);
    }



    @FXML
    void updateAction(ActionEvent event) {
    }
}