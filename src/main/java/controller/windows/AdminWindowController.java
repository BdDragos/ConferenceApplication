package controller.windows;

import com.sun.org.apache.bcel.internal.generic.FCMPG;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Main;
import model.*;
import repository.*;
import services.AdminService;
import services.DefaultUserService;
import services.ParticipantsService;
import validators.Validators;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Dragos on 5/8/2017.
 */
public class AdminWindowController
{
    // Logged user
    private Admin admin;

    final Main loginManager;
    @FXML private ComboBox<DefaultUser> defaultUsersComboBox;
    @FXML private RadioButton adminRadioButton;
    @FXML private RadioButton attendantRadioButton;
    @FXML private RadioButton authorRadioButton;
    @FXML private RadioButton cmRadioButton;
    @FXML private RadioButton reviewerRadioButton;
    @FXML private TitledPane nameTitledPane;
    @FXML private TitledPane affiliationTitledPane;
    @FXML private TitledPane emailTitledPane;
    @FXML private TitledPane websiteTitledPane;
    @FXML private TextField nameTextField;
    @FXML private TextField affiliationTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField websiteTextField;
    @FXML private Button activateButton;
    @FXML private Button attendantButton;
    @FXML private Button authorButton;
    @FXML private Button cmButton;
    @FXML private Button reviewerButton;
    @FXML private Button logoutButton;
    @FXML private Button deleteButton;
    @FXML private ListView<Admin> adminsListView;
    @FXML private ListView<Attendant> attendantsListView;
    @FXML private ListView<Author> authorsListView;
    @FXML private ListView<CM> cmsListView;
    @FXML private TabPane usersTabPane;

    private ToggleGroup toggleRadioGroup;


    @FXML private AttendantControl attendantControl;
    @FXML private AuthorControl authorControl;
    @FXML private ComiteeControl comiteeControl;
    @FXML private ReviewerControl reviewerControl;

    private List<DefaultUser> defaultUserList;
    private ObservableList<DefaultUser> defaultUserObservableList;
    private List<Admin> adminList;
    private ObservableList<Admin> adminObservableList;
    private List<Attendant> attendantList;
    private ObservableList<Attendant> attendantObservableList;
    private List<Author> authorList;
    private ObservableList<Author> authorObservableList;
    private List<CM> cmList;
    private ObservableList<CM> cmObservableList;

    // Logins Repositories
    private CMRepository CMLRepository;
    private AttendantRepository ATLRepository;
    private AuthorsRepository AULRepository;
    private ReviewerRepository RVWRepo;
    private AdminService adminService;
    private DefaultUserService defaultUserService;
    private ParticipantsService participantsService;
    private SectionRepository sectionRepository;

    public AdminWindowController(final Main loginManager, CMRepository CMLRepository, AttendantRepository ATLRepository, AuthorsRepository AULRepository, ReviewerRepository RVWRepo, AdminService adminService, DefaultUserService defaultUserService, ParticipantsService participantsService, SectionRepository sectionRepository) {
        this.loginManager = loginManager;
        this.CMLRepository = CMLRepository;
        this.ATLRepository = ATLRepository;
        this.AULRepository = AULRepository;
        this.RVWRepo = RVWRepo;
        this.adminService = adminService;
        this.defaultUserService = defaultUserService;
        this.participantsService = participantsService;
        this.sectionRepository = sectionRepository;

        toggleRadioGroup = new ToggleGroup();
    }

    public void initialize(Admin admin) {
        this.admin = admin;

        adminRadioButton.setToggleGroup(toggleRadioGroup);
        attendantRadioButton.setToggleGroup(toggleRadioGroup);
        authorRadioButton.setToggleGroup(toggleRadioGroup);
        cmRadioButton.setToggleGroup(toggleRadioGroup);
        reviewerRadioButton.setToggleGroup(toggleRadioGroup);

        setTitledPanesVisible(false, false, false, false);

        defaultUserList = defaultUserService.getAll();
        defaultUserObservableList = FXCollections.observableArrayList(defaultUserList);
        defaultUsersComboBox.setItems(defaultUserObservableList);

        adminList = adminService.getAll();
        adminObservableList = FXCollections.observableArrayList(adminList);
        adminsListView.setItems(adminObservableList);

        attendantList = ATLRepository.getAll();
        attendantObservableList = FXCollections.observableArrayList(attendantList);
        attendantsListView.setItems(attendantObservableList);

        authorList= AULRepository.getAll();
        authorObservableList = FXCollections.observableArrayList(authorList);
        authorsListView.setItems(authorObservableList);

        cmList = CMLRepository.getAll();
        cmObservableList = FXCollections.observableArrayList(cmList);
        cmsListView.setItems(cmObservableList);

        toggleRadioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (toggleRadioGroup.getSelectedToggle() != null) {
                    if (authorRadioButton.isSelected())
                        setTitledPanesVisible(true, false, false, false);
                    else if (cmRadioButton.isSelected() || reviewerRadioButton.isSelected())
                        setTitledPanesVisible(true, true, true, true);
                    else
                        setTitledPanesVisible(false, false, false, false);
                }
            }
        });
    }
    @FXML public void logoutButtonOnAction()
    {
        loginManager.logOut();
    }

    @FXML public void activateButtonOnAction() {
        DefaultUser du = defaultUsersComboBox.getValue();
        if (du == null)
            return;
        if (attendantRadioButton.isSelected()) {
            if (ATLRepository.login(du.getUsername(), du.getPassword())) {
                showMessage(Alert.AlertType.ERROR, "Attendant Register", "User already exists in Attendant !");
                return;
            }
            ATLRepository.save(du.getUsername(), du.getPassword());
            attendantsListView.getItems().add(ATLRepository.findOne(du.getUsername()));
        }
        else if (authorRadioButton.isSelected()) {
            if (AULRepository.login(du.getUsername(), du.getPassword())!=0) {
                showMessage(Alert.AlertType.ERROR, "Author Register", "User already exists in authors !");
                return;
            }
            String info1 = nameTextField.getText();
            if (!Validators.ValidateName(info1)) {
                showMessage(Alert.AlertType.ERROR, "Author Register", "Nume invalid.\nIntroduceti numele din nou !");
                return;
            }
            AULRepository.save(du.getUsername(), du.getPassword(), info1);
            authorsListView.getItems().add(AULRepository.findOne(du.getUsername()));
        }
        else if (cmRadioButton.isSelected()) {
            if (AULRepository.login(du.getUsername(), du.getPassword())!=0) {
                showMessage(Alert.AlertType.WARNING, "CM Register", "User already exists in authors (skipped) !\nPending for register in CM...");
            }
            if (CMLRepository.login(du.getUsername(), du.getPassword())) {
                showMessage(Alert.AlertType.ERROR, "CM Register", "User already exists in CM !");
                return;
            }
            String info1 = nameTextField.getText(), info2 = affiliationTextField.getText(), info3 = emailTextField.getText(), info4 = websiteTextField.getText();
            if (!Validators.ValidateName(info1)) {
                showMessage(Alert.AlertType.ERROR, "CM Register", "Nume invalid.\nIntroduceti numele din nou !");
                return;
            }
            if (!Validators.ValidateName(info2)) {
                showMessage(Alert.AlertType.ERROR, "CM Register", "Afiliere invalida.\nIntroduceti afilierea din nou !");
                return;
            }
            if (!Validators.ValidateEmail(info3)) {
                showMessage(Alert.AlertType.ERROR, "CM Register", "Email invalid.\nIntroduceti email-ul din nou !");
                return;
            }
            if (!Validators.ValidateURL(info4)) {
                showMessage(Alert.AlertType.ERROR, "CM Register", "Website invalid.\nIntroduceti website-ul din nou !");
                return;
            }
            AULRepository.save(du.getUsername(), du.getPassword(), info1);
            CMLRepository.save(du.getUsername(), du.getPassword(), info1, info2, info3, info4);
            authorsListView.getItems().add(AULRepository.findOne(du.getUsername()));
            cmsListView.getItems().add((CMLRepository.findOne(du.getUsername())));
        }
        else if (reviewerRadioButton.isSelected()) {
            String info1 = nameTextField.getText(), info2 = affiliationTextField.getText(), info3 = emailTextField.getText(), info4 = websiteTextField.getText();
            if (!Validators.ValidateName(info1)) {
                showMessage(Alert.AlertType.ERROR, "CM Register", "Nume invalid.\nIntroduceti numele din nou !");
                return;
            }
            if (!Validators.ValidateName(info2)) {
                showMessage(Alert.AlertType.ERROR, "CM Register", "Afiliere invalida.\nIntroduceti afilierea din nou !");
                return;
            }
            if (!Validators.ValidateEmail(info3)) {
                showMessage(Alert.AlertType.ERROR, "CM Register", "Email invalid.\nIntroduceti email-ul din nou !");
                return;
            }
            if (!Validators.ValidateURL(info4)) {
                showMessage(Alert.AlertType.ERROR, "CM Register", "Website invalid.\nIntroduceti website-ul din nou !");
                return;
            }
            CMLRepository.save(du.getUsername(), du.getPassword(), info1, info2, info3, info4);
            cmsListView.getItems().add(CMLRepository.findOne(du.getUsername()));
        } else if (adminRadioButton.isSelected()) {
            if (adminService.login(du.getUsername(), du.getPassword())) {
                showMessage(Alert.AlertType.ERROR, "Admin Register", "User already exists in Admin !");
                return;
            }
            adminService.save(du.getUsername(), du.getPassword());
            adminsListView.getItems().add((adminService.findOne(du.getUsername())));
        }
        else {
            showMessage(Alert.AlertType.WARNING, "Warning !", "Register failed...");
            return;
        }

        defaultUserService.delete(du.getUsername());
        defaultUsersComboBox.getItems().removeIf(u -> u.getUsername().equals(du.getUsername()));

        showMessage(Alert.AlertType.CONFIRMATION, "Register OK", "Register OK");
    }
    @FXML public void attendantButtonOnAction() {
        loginManager.activeAdmin = this.admin;
        loginManager.bAdminActive = true;
        loginManager.AttendantView();
    }
    @FXML public void authorButtonOnAction() {
        loginManager.activeAdmin = this.admin;
        loginManager.bAdminActive = true;
        loginManager.AuthorView(0);
    }
    @FXML public void cmButtonOnAction() {
        loginManager.activeAdmin = this.admin;
        loginManager.bAdminActive = true;
        loginManager.ComitteeView();
    }
    @FXML public void reviewerButtonOnAction() {
        loginManager.activeAdmin = this.admin;
        loginManager.bAdminActive = true;
        loginManager.ReviewerView();
    }
    @FXML public void deleteButtonOnAction() {
        int selected = usersTabPane.getSelectionModel().getSelectedIndex();
        if (selected == 0) {//admin
            Admin admin = adminsListView.getSelectionModel().getSelectedItem();
            if (admin == null) {
                showMessage(Alert.AlertType.ERROR, "Delete Admin", "Adminul selectat este invalid !");
                return;
            }
            if (admin.getPassword().equals(this.admin.getUsername())) {
                showMessage(Alert.AlertType.ERROR, "Delete Admin", "Nu-ti poti sterge propriul cont !");
                return;
            }
            adminService.delete(admin.getUsername());
            //adminList.remove(admin);
            //adminObservableList.remove(admin);
            adminsListView.getItems().remove(admin);
        }
        else if (selected == 1) {//attendant
            Attendant attendant = attendantsListView.getSelectionModel().getSelectedItem();
            if (attendant == null) {
                showMessage(Alert.AlertType.ERROR, "Delete Attendant", "Attendant-ul selectat este invalid !");
                return;
            }
            if(participantsService.getAll().stream().filter(p -> p.getIdat() == attendant.getIdat()).collect(Collectors.toList()).size() > 0) {
                showMessage(Alert.AlertType.ERROR, "Delete Attendant", "Eroare stergere ! Attendant-ul se gaseste in lista de participanti.");
                return;
            }
            ATLRepository.delete(attendant.getUsername());
            //attendantList.remove(attendant);
            //attendantObservableList.remove(attendant);
            attendantsListView.getItems().remove(attendant);
        }
        else if (selected == 2) {//author
            Author author = authorsListView.getSelectionModel().getSelectedItem();
            if (author == null) {
                showMessage(Alert.AlertType.ERROR, "Delete Author", "Author-ul selectat este invalid !");
                return;
            }
            if (participantsService.getAll().stream().filter(p->p.getIda() == author.getIda()).collect(Collectors.toList()).size() > 0 ||
                AULRepository.findAuthorInLegaf(author)) {
                showMessage(Alert.AlertType.ERROR, "Delete Author", "Author-ul selectat are alte date atasate programului !\nStergeti acele date apoi incercati din nou !");
                return;
            }

            AULRepository.delete(author.getUsername());
            //authorList.remove(author);
            //authorObservableList.remove(author);
            authorsListView.getItems().remove(author);
        }
        else if (selected == 3) {//cm and reviewer
            CM cm = cmsListView.getSelectionModel().getSelectedItem();
            if (cm == null) {
                showMessage(Alert.AlertType.ERROR, "Delete CM", "CM-ul selectat este invalid !");
                return;
            }
            if (sectionRepository.getAll().stream().filter(s->s.getSesChair() == cm.getId()).collect(Collectors.toList()).size() > 0) {
                showMessage(Alert.AlertType.ERROR, "Delete CM", "CM-ul selectat are alte date atasate programului !\nStergeti acele date apoi incercati din nou !");
                return;
            }
            CMLRepository.delete(cm.getUsername());
            //cmList.remove(cm);
            //cmObservableList.remove(cm);
            cmsListView.getItems().remove(cm);
        }
        else {
            showMessage(Alert.AlertType.WARNING, "Delete user", "Nu ati selectat niciun utilizator !");
            return;
        }
        showMessage(Alert.AlertType.CONFIRMATION, "Delete user", "Utilizatorul a fost sters.");
    }
    private static void showMessage(Alert.AlertType type, String header, String message)
    {
        Alert messages=new Alert(type);
        messages.setHeaderText(header);
        messages.setContentText(message);
        messages.showAndWait();
    }
    private void setTitledPanesVisible(boolean name, boolean affiliation, boolean email, boolean website) {
        nameTitledPane.setVisible(name);
        affiliationTitledPane.setVisible(affiliation);
        emailTitledPane.setVisible(email);
        websiteTitledPane.setVisible(website);
    }
}