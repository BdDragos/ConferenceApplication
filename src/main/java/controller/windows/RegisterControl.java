package controller.windows;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.DefaultUserService;
import validators.Validators;

/**
 * Created by Dragos on 5/22/2017.
 */
public class RegisterControl
{
    @FXML private TextField userField;
    @FXML private PasswordField passField;
    @FXML private PasswordField repeatpassField;
    private DefaultUserService defaultUserService;

    public RegisterControl(DefaultUserService defaultUserService) {
        this.defaultUserService = defaultUserService;
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

        if (!Validators.ValidateName(userName))
        {
            showErrorMessage("Username invalid .\nIntroduceti alt username !");
        }
        else if (!Validators.ValidatePassword(password))
        {
            showErrorMessage("Parola invalida .\nIntroduceti alta parola !");
        }
        else if (!Validators.ValidatePassword(repeatpass))
        {
            showErrorMessage("Parola invalida .\nIntroduceti parola scrisa mai sus !");
        }
        else if (!password.equals(repeatpass))
            {
                showErrorMessage("Parolele trebuie sa fie identice.");
            }
            else
                if (defaultUserService.findOneByUsername(userName) != null) {
                    showErrorMessage("User-ul deja exista !");
                }
                else {
                    defaultUserService.save(userName, password);
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
