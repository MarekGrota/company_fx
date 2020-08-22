package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utility.UserData;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField pfPassword;
    // metoda dostarczająca logikę do logowania użytkownika
    private void login() throws IOException {
        boolean isLogged = UserData.users.stream()
                .anyMatch(user ->
                        user.getEmail().equals(tfLogin.getText()) &&
                                user.getPassword().equals(pfPassword.getText()));
        if(isLogged){
            System.out.println("zalogowano");
            tfLogin.setStyle(null);
            pfPassword.setStyle(null);
            // wywołanie nowego okna aplikacji
            Stage primaryStage = new Stage();
            Parent parent = FXMLLoader.load(getClass().getResource("/view/companyView.fxml"));
            primaryStage.setTitle("Aplikacja magazynowa");  // tytuł okna
//            primaryStage.setResizable(false);               // brak skalowania
            primaryStage.initStyle(StageStyle.UNDECORATED); // brak przyciksków w tytule okna
            primaryStage.setScene(new Scene(parent));
            primaryStage.show();
            // zamknięcie okna logowania na obiekcie typu Stage
            Stage loginStage = (Stage) tfLogin.getScene().getWindow();
            loginStage.close();
        } else {
            System.out.println("niezalogowano");
            tfLogin.clear();
            pfPassword.clear();
            tfLogin.setStyle("-fx-border-color: red; -fx-border-width: 2px");
            pfPassword.setStyle("-fx-border-color: red; -fx-border-width: 2px");
        }
    }
    @FXML   // kliknięcie na button (button)
    void loginAction(ActionEvent event) throws IOException {
        login();
    }
    @FXML   // wciśnięce Enter w dowolnym miejscu na oknie aplikacji (vbox)
    void keyLoginAction(KeyEvent event) throws IOException {
        if(event.getCode() == KeyCode.ENTER) {
            login();
        } else if(event.getCode() == KeyCode.ESCAPE){
            Platform.exit();    // zamknięcie okna aplikacji wywołanie metody close()
        }
    }
}