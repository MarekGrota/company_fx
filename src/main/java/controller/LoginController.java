/**
 * Sample Skeleton for 'loginView.fxml' Controller Class
 */

package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utility.UserData;

public class LoginController {

    @FXML // fx:id="tfLogin"
    private TextField tfLogin; // Value injected by FXMLLoader

    @FXML // fx:id="pfPassword"
    private PasswordField pfPassword; // Value injected by FXMLLoader

    private void login () {
        boolean isLogged = UserData.users.stream()
                .anyMatch(user ->
                        user.getEmail().equals(tfLogin.getText()) &&
                                user.getPassword().equals(pfPassword.getText()));
        if (isLogged) {
            System.out.println("Zalogowano");
            tfLogin.setStyle(null);
            pfPassword.setStyle(null);
            //tfLogin.clear();
            pfPassword.clear();
            tfLogin.setStyle("-fx-border-color: green; -fx-border-width: 2px");
            pfPassword.setStyle("-fx-border-color: green; -fx-border-width: 2px");
        } else {
            System.out.println("niezalogowano");
            tfLogin.clear();
            pfPassword.clear();
            tfLogin.setStyle("-fx-border-color: red; -fx-border-width: 2px");
            pfPassword.setStyle("-fx-border-color: red; -fx-border-width: 2px");
        }
    }

    @FXML // klikniecie na button (button)
    void loginAction(ActionEvent event) {
    }

    @FXML //wciśnięcie Enter w dowolnym miejscu na oknie aplikacji (vbox)
    void keyLoginAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            login();
        if (event.getCode() == KeyCode.ESCAPE)
            Platform.exit(); // zamknięcie okna aplikacji (wywołanie metody close())
    }
}
