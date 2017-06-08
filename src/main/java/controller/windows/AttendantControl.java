package controller.windows;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.Main;
import model.Conference;
import org.hibernate.SessionFactory;
import repository.AttendantRepository;
import repository.ConfRepository;
import repository.AuthorsRepository;
import model.Sections;
import repository.SectionRepository;

import java.util.List;

/**
 * Created by Dragos on 5/8/2017.
 */
public class AttendantControl
{
    final Main loginManager;
    private ConfRepository confRepo = new ConfRepository();
    private SectionRepository sectionRepository = new SectionRepository();
    private Sections sections = new Sections();
    int idforfile;
    private AuthorsRepository authorsRepository = new AuthorsRepository(idforfile);
    private SessionFactory factory;
    private AttendantRepository attRepo = new AttendantRepository();
    public AttendantControl(final Main loginManager) {
        this.loginManager = loginManager;
        //this.factory = factory;
        //this.confRepo = confRepo;
    }
    @FXML
    public void setLogoutAction()
    {
        loginManager.logOut();
    }

    @FXML
    TextField textFieldSesChair = new TextField();
    @FXML
    TextField textFieldDate = new TextField();
    @FXML
    TextField textFieldHour = new TextField();

    //@FXML
    //private TableView<Session> sectionTableView;

    //@FXML
    //private TableColumn<Session, String> sesiune;

    @FXML
    ComboBox<Sections> sessionComboBox;
    @FXML
    ComboBox<Conference> conferenceComboBox;

    private ObservableList<Conference> conferences;
    public void initialize(){
        int id;
        //int id = conferenceComboBox.getSelectionModel().getSelectedItem().getIdConference();
        //conferenceComboBox.getItems().clear();
        //sessionComboBox.getItems().clear();
        ObservableList<Conference> obs = FXCollections.observableArrayList(confRepo.getAll());
        conferenceComboBox.setItems(obs);
        //conferenceComboBox.getSelectionModel().selectFirst();
        conferenceComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                ObservableList combobox2 = FXCollections.observableArrayList((List) authorsRepository.findByConfId(conferenceComboBox.getValue().getIdConference()));
                sessionComboBox.setItems(combobox2);
            }
        });

        /*sessionComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                //textFieldSesChair.setText(((Sections)newValue).getSesChair());
                textFieldDate.setText(((Sections)newValue).getDate());
                textFieldHour.setText(((Sections)newValue).getHour());
            }
        });*/
    }

    static void showMessage(Alert.AlertType type, String header, String text)
    {
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    @FXML
    public void partikip(){
        if (conferenceComboBox.getSelectionModel().getSelectedItem() == null)
            AuthorControl.showErrorMessage("Please select a conference");
        else if (sessionComboBox.getSelectionModel().getSelectedItem() == null)
            AuthorControl.showErrorMessage("Please select a session");
        else{
            int id = conferenceComboBox.getSelectionModel().getSelectedItem().getIdConference();
            attRepo.partikip(id);
            //conferenceComboBox.getSelectionModel().getSelectedItem().setNoParticipants(participanti);
            showMessage(Alert.AlertType.CONFIRMATION, "Success", "You are a registered participant");
        }
    }
}
