package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import repository.DefaultUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragos on 5/22/2017.
 */
public class RegisterControl
{
    @FXML private TextField userField;
    @FXML private PasswordField passField;
    @FXML private PasswordField repeatpassField;
    private DefaultUserRepository DURepo;

    public RegisterControl(DefaultUserRepository DURepo) {
        this.DURepo = DURepo;
    }
    public void initialize()
    {
    }

    @FXML
    public void setOnConfirm()
    {

        String userName = userField.getText();
        String password = passField.getText();
        String repeatpass = repeatpassField.getText();

        if (userName == null)
        {
            showErrorMessage("Dati un username");
        }
        else if (password == null)
        {
            showErrorMessage("Dati o parola");
        }
        else if (repeatpass == null)
        {
            showErrorMessage("Repetati parola");
        }
        else if (password.compareTo(repeatpass) != 0)
            {
                showErrorMessage("Parolele trebuie sa fie identice.");
            }
            else
                if (DURepo.findOneByUsername(userName) != null) {
                    showErrorMessage("User-ul deja exista !");
                }
                else {
                    DURepo.add(userName, password);
                    showMessage(Alert.AlertType.CONFIRMATION, "Register !", "Ati fost inregistrat.\nAsteptati ca adminul sa va activeze contul.");
                }
    }

    private static void showMessage(Alert.AlertType type, String header, String msg)
    {
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(msg);
        message.showAndWait();
    }

    private static void showErrorMessage(String text)
    {
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }
}
