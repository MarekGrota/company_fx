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
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CompanyController {

    private String path = Paths.get("").toAbsolutePath().toString() +
            "\\src\\main\\java\\utility\\products.csv";

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

    private void getProductsFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        scanner.nextLine(); // pominięcie nagłówka w pliku .csv
        while (scanner.hasNextLine()) {
            String line[] = scanner.nextLine().split(";");
            products.add(new Product(
                    Integer.valueOf(line[0]), line[1],
                    Arrays.stream(Category.values())                                        // Category []
                            .filter(category -> category.getCategoryName().equals(line[2])) // filtrowanie po nazwie kategorii
                            .findAny()                                                      // Optional<Category>
                            .get(),                                                          // Category
                    Double.valueOf(line[3].replace(",", ".")), Integer.valueOf(line[4])));
        }
    }

    private void setProductsIntoTable() {
        // konfiguracja wartości wporwadzanych do tabeli z pól klasy modelu Product
        tcId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        // format walutowy
        Locale locale = new Locale("pl", "PL");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        tcPrice.setCellFactory(tc -> new TableCell<Product, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(price));
                }
            }
        });
        tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        // przekazanie wartości do tabeli z ObservableList
        tblProducts.setItems(products);
    }

    public void initialize() throws FileNotFoundException {
        getProductsFromFile();
        setProductsIntoTable();
        // wprowadzenie kategorii do combobox
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

        TextField tf_productName = new TextField();
        tf_productName.setPromptText("nazwa");
        ComboBox<Category> combo_productCategory = new ComboBox<>();
        combo_productCategory.setItems(FXCollections.observableArrayList(Category.values()));
        combo_productCategory.setPromptText("kategoria");
        TextField tf_productPrice = new TextField();
        tf_productPrice.setPromptText("cena");
        TextField tf_productQuantity = new TextField();
        tf_productQuantity.setPromptText("ilość");

        grid.add(tf_productName, 0, 0);
        grid.add(combo_productCategory, 0, 1);
        grid.add(tf_productPrice, 0, 2);
        grid.add(tf_productQuantity, 0, 3);

        dialog.getDialogPane().setContent(grid);
        // przyciski
        ButtonType btn_ok = new ButtonType("Dodaj", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btn_ok);

        Optional<Product> productOpt = dialog.showAndWait();
        if (productOpt.isPresent()) {
            if (!tf_productPrice.getText().matches("[0-9]+\\.{0,1}[0-9]{0,2}") ||
                    !tf_productQuantity.getText().matches("[0-9]+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd danych");
                alert.setHeaderText("Błąd danych. Produkt nie został dodany!");
                alert.showAndWait();
            } else {
                products.add(new Product(products.stream().mapToInt(p -> p.getId()).max().getAsInt() + 1,
                        tf_productName.getText(), combo_productCategory.getValue(),
                        Double.valueOf(tf_productPrice.getText()), Integer.valueOf(tf_productQuantity.getText())));
                saveProductsToFile();
                setProductsIntoTable();
            }
        }
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
                            String.format("%.2f", product.getPrice()).replace(".", ","),
                            product.getQuantity()
                    ));
        }
        pw.close();
    }

    @FXML
    void deleteAction(ActionEvent event) throws IOException {
        Product product = tblProducts.getSelectionModel().getSelectedItem();
        if (product != null) {
            products.remove(product);
            saveProductsToFile();   //aktualizacja pliku
            setProductsIntoTable();  //aktualizacja tabeli
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);
        }
    }

    @FXML
        // akcja zaznaczenia rekordu w tabelce
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
                products.stream()
                        .filter(product -> product.getName().toLowerCase().contains(tfSearch.getText().toLowerCase()))
                        .collect(Collectors.toList()));
        if (comboCategory.getValue() != null){
            filteredProducts = FXCollections.observableArrayList(filteredProducts.stream()
                    .filter(product -> product.getCategory().equals(comboCategory.getValue()))
                    .collect(Collectors.toList()));
        }
        // filtrowanie po ilości
        ObservableList<Product> productsToFilter = FXCollections.observableArrayList();
        if (cbLess5.isSelected()){
            productsToFilter.addAll(FXCollections.observableArrayList(filteredProducts.stream()
                    .filter(product -> product.getQuantity() < 5)
                    .collect(Collectors.toList())));
        }
        if (cbMedium.isSelected()){
            productsToFilter.addAll(FXCollections.observableArrayList(filteredProducts.stream()
                    .filter(product -> product.getQuantity() >= 5 && product.getQuantity() <= 10)
                    .collect(Collectors.toList())));
        }
        if (cbMore10.isSelected()){
            productsToFilter.addAll(FXCollections.observableArrayList(filteredProducts.stream()
                    .filter(product -> product.getQuantity() > 10)
                    .collect(Collectors.toList())));
        }
        ObservableList<Product> finalFilter = FXCollections.observableArrayList();
        for (Product p1: productsToFilter) {
            for (Product p2: filteredProducts) {
                if (p1.equals(p2)){
                    finalFilter.add(p1);
                }
            }
        }
        tblProducts.setItems(finalFilter);
        tfSearch.clear();
        comboCategory.setValue(null);
        cbLess5.setSelected(true);
        cbMedium.setSelected(true);
        cbMore10.setSelected(true);
    }

    @FXML
    void updateAction(ActionEvent event) throws IOException {
        // zaznaczone w tabelce
        Product product = tblProducts.getSelectionModel().getSelectedItem(); // odwołanie do obiektu zaznaczonego w tabeli

        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Edytuj produkt");
        dialog.setHeaderText("Edytuj produkt");
        // ustawienie kontrolek
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tf_productName = new TextField();
        tf_productName.setText(product.getName());
        ComboBox<Category> combo_productCategory = new ComboBox<>();
        combo_productCategory.setItems(FXCollections.observableArrayList(Category.values()));
        combo_productCategory.setValue(product.getCategory());
        TextField tf_productPrice = new TextField();
        tf_productPrice.setText(String.valueOf(product.getPrice()));
        TextField tf_productQuantity = new TextField();
        tf_productQuantity.setText(String.valueOf(product.getQuantity()));

        grid.add(tf_productName, 0, 0);
        grid.add(combo_productCategory, 0, 1);
        grid.add(tf_productPrice, 0, 2);
        grid.add(tf_productQuantity, 0, 3);

        dialog.getDialogPane().setContent(grid);
        // przyciski
        ButtonType btn_ok = new ButtonType("Edytuj", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btn_ok);

        Optional<Product> productOpt = dialog.showAndWait();
        if (productOpt.isPresent()) {
            if (!tf_productPrice.getText().matches("[0-9]+\\.{0,1}[0-9]{0,2}") ||
                    !tf_productQuantity.getText().matches("[0-9]+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd danych");
                alert.setHeaderText("Błąd danych. Produkt nie został dodany!");
                alert.showAndWait();
            } else {
                product.setName(tf_productName.getText());
                product.setCategory(combo_productCategory.getValue());
                product.setPrice(Double.valueOf(tf_productPrice.getText()));
                product.setQuantity(Integer.valueOf(tf_productQuantity.getText()));
                saveProductsToFile();
                products.clear();
                getProductsFromFile();
                setProductsIntoTable();
            }
        }
    }
}