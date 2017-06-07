package controller.windows;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Main;
import repository.*;
import services.AdminService;
import services.DefaultUserService;
import validators.Validators;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Dragos on 4/4/2017.
 */
public class LoginControl implements Initializable {

    @FXML private TextField userField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private RadioButton cmRadio, reviewRadio;
    @FXML private RadioButton attendantRadio;
    @FXML private RadioButton authorRadio;
    @FXML private RadioButton adminRadio;
    private ToggleGroup group;
    private Stage secondStage;

    // Logins Repositories
    private CMRepository CMLRepository;
    private AttendantRepository ATLRepository;
    private AuthorsRepository AULRepository;
    private ReviewerRepository RVWRepo;
    private AdminService adminService;
    private DefaultUserService defaultUserService;



    public LoginControl(CMRepository cmloginRep, AttendantRepository atloginrep, AuthorsRepository atuloginrep, ReviewerRepository RVWRepo, AdminService adminService, DefaultUserService defaultUserService)
    {
        this.CMLRepository = cmloginRep;
        this.ATLRepository = atloginrep;
        this.AULRepository = atuloginrep;
        this.RVWRepo = RVWRepo;
        this.adminService = adminService;
        this.defaultUserService = defaultUserService;
        this.secondStage = new Stage();
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
                    int idforfile = 0;
                    int response = 0;
                    String userName = userField.getText();
                    String password = passwordField.getText();
                    if (!Validators.ValidateName(userName)) {
                        showErrorMessage("Username invalid.\nIntroduceti alt username !");
                    } else if (!Validators.ValidatePassword(password)) {
                        showErrorMessage("Parola invalida.\nIntroduceti alta parola !");
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
                                    if (RVWRepo.login(userName, password))
                                    {
                                        showMessage(Alert.AlertType.CONFIRMATION);
                                        response = 2;
                                    }

                            }

                            else if (authorRadio.isSelected())
                            {
                                    idforfile = AULRepository.login(userName, password);
                                    if (idforfile !=0)
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
                                    if (adminService.login(userName, password)) {
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
                                loginManager.authenticated(response,idforfile);

                            }
                    }
            }
        });
    }

    @FXML
    public void setOnRegister()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            String pathToFxml = "src/main/resources/RegisterWindow.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader.setLocation(fxmlUrl);

            RegisterControl registerCont = new RegisterControl(defaultUserService);
            loader.setController(registerCont);
            AnchorPane rootLayout1;
            Scene scene1;
            rootLayout1 = loader.load();
            scene1 = new Scene(rootLayout1);
            secondStage.setScene(scene1);
            secondStage.show();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
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
