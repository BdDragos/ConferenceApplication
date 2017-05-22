package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.Main;
import repository.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Dragos on 4/4/2017.
 */
public class LoginControl implements Initializable {

    @FXML
    private TextField userField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private RadioButton cmRadio, reviewRadio;
    @FXML private RadioButton attendantRadio;
    @FXML private RadioButton authorRadio;
    @FXML private RadioButton adminRadio;
    private ToggleGroup group;

    // Logins Repositories
    private CMRepository CMLRepository;
    private AttendantRepository ATLRepository;
    private AuthorsRepository AULRepository;
    private ReviewerRepository RVWRepo;
    private AdminRepository ADRepo;


    public LoginControl(CMRepository cmloginRep, AttendantRepository atloginrep, AuthorsRepository atuloginrep, ReviewerRepository RVWRepo, AdminRepository ADRepo)
    {
        this.CMLRepository = cmloginRep;
        this.ATLRepository = atloginrep;
        this.AULRepository = atuloginrep;
        this.RVWRepo = RVWRepo;
        this.ADRepo = ADRepo;
    }

    public void initialize(URL location, ResourceBundle resources)
    {
        passwordField.setPromptText("Your password");
        userField.setPromptText("Your username");

        this.group = new ToggleGroup();

        cmRadio.setToggleGroup(group);
        cmRadio.setSelected(true);

        attendantRadio.setToggleGroup(group);
        reviewRadio.setToggleGroup(group);

        authorRadio.setToggleGroup(group);
        adminRadio.setToggleGroup(group);
    }

    public void initManager(final Main loginManager)
    {
        loginButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                    int response = 0;
                    String userName = userField.getText();
                    String password = passwordField.getText();
                    if (userName == null) {
                        showErrorMessage("Dati un username");
                    } else if (password == null) {
                        showErrorMessage("Dati o parola");
                    } else
                        {
                            if (cmRadio.isSelected())
                            {
                                    if (CMLRepository.login(userName, password)) {
                                        showMessage(Alert.AlertType.CONFIRMATION);
                                        response = 3;
                                        }
                            }
                            else if (reviewRadio.isSelected())
                            {
                                    if (RVWRepo.login(userName, password)) {
                                        showMessage(Alert.AlertType.CONFIRMATION);
                                        response = 2;
                                    }

                            }

                            else if (authorRadio.isSelected())
                            {
                                    if (AULRepository.login(userName, password))
                                    {
                                        showMessage(Alert.AlertType.CONFIRMATION);
                                        response = 4;
                                    }
                            }
                            else if (attendantRadio.isSelected())
                            {
                                    if (ATLRepository.login(userName, password)) {
                                        showMessage(Alert.AlertType.CONFIRMATION);
                                        response = 6;
                                    }
                            }
                            else if (adminRadio.isSelected())
                            {
                                    if (ADRepo.login(userName, password)) {
                                        showMessage(Alert.AlertType.CONFIRMATION);
                                        response = 5;
                                    }

                            }
                            if (response == 0) {
                                showErrorMessage("Username sau parola invalida");
                                initManager(loginManager);
                            }
                            else
                            {
                                loginManager.authenticated(response);

                            }
                    }
            }
        });
    }

    public void setOnRegister(String username, String password)
    {

    }

    private static void showMessage(Alert.AlertType type)
    {
        Alert message=new Alert(type);
        message.setHeaderText("Succes");
        message.setContentText("Succes");
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
