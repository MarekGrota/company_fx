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

    @FXML // fx:id="pfLogin"
    private TextField pfLogin; // Value injected by FXMLLoader

    @FXML // fx:id="pfPassword"
    private PasswordField pfPassword; // Value injected by FXMLLoader

    @FXML
    void loginAction(ActionEvent event) {
        boolean isLogged = UserData.users.stream()
                .anyMatch(user ->
                        user.getEmail().equals(pfLogin.getText()) &&
                        user.getPassword().equals(pfPassword.getText()));
        if(isLogged){
            System.out.println("Zalogowano");
            pfLogin.setStyle(null);
            pfPassword.setStyle(null);
            pfLogin.setStyle("-fx-border-color: green; -fx-border-width: 3px");
            pfPassword.setStyle("-fx-border-color: green; -fx-border-width: 3px");
        } else {
            System.out.println("niezalogowano");
            pfLogin.clear();
            pfPassword.clear();
            pfLogin.setStyle("-fx-border-color: red; -fx-border-width: 3px");
            pfPassword.setStyle("-fx-border-color: red; -fx-border-width: 3px");
        }
    }

}
