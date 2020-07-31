/**
 * Sample Skeleton for 'loginView.fxml' Controller Class
 */

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utility.UserData;

public class LoginController {

    @FXML // fx:id="tfLogin"
    private TextField tfLogin; // Value injected by FXMLLoader

    @FXML // fx:id="pfPassword"
    private PasswordField pfPassword; // Value injected by FXMLLoader

    @FXML
    void loginAction(ActionEvent event) {
        boolean isLogged = UserData.users.stream()
                .anyMatch(user ->
                        user.getEmail().equals(tfLogin.getText()) &&
                        user.getPassword().equals(pfPassword.getText()));
        if(isLogged){
            System.out.println("Zalogowano");
            tfLogin.setStyle(null);
            pfPassword.setStyle(null);
            tfLogin.clear();
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

}
