package controller.windows;

import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.Main;
import model.Author;
import model.Conference;
import model.File;
import model.Sections;
import repository.AuthorsRepository;
import services.AuthorService;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Dragos on 5/8/2017.
 */
public class AuthorControl
{
    private AuthorsRepository repo;
    private final Main loginManager;
    @FXML private ComboBox<Conference> confCombo;
    @FXML private ComboBox<Sections> sesCombo;
    @FXML private ComboBox<Author> authorCombo;
    @FXML private Button confirmButton;
    @FXML private Button addButton;
    @FXML private Button uploadButton;
    @FXML private Label abstractLabel;
    @FXML private Label proposalLabel;
    @FXML private TextArea absText;
    @FXML private TableView<File> fileTable;
    @FXML private TextField propText;
    @FXML private TextField keyText;
    @FXML private TextField topText;
    @FXML private TextField linkText;
    @FXML private TableColumn<File, String> titlu;
    @FXML private TableColumn<File, String> filedoc;
    @FXML private TableColumn<File, String> tabAccept;
    @FXML private ListView<Author> listAuthor;
    @FXML private Button fileButton;
    @FXML private Button partikipButton;

    private int idforfile;
    private ObservableList<Author> aut;
    private AuthorService service;
    private ObservableList files;
    private List<File> lista = new ArrayList<File>();
    private ObservableList<Conference> conferences;
    private List<Author> autr = new ArrayList<Author>();

    public AuthorControl(final Main loginManager)
    {
        this.loginManager = loginManager;

    }
    @SuppressWarnings("unchecked")
    public void initialize(int idforfile)
    {
        this.idforfile = idforfile;
        this.repo = new AuthorsRepository(idforfile);
        this.service = new AuthorService(this.repo);
        lista = service.getAllFiles();
        this.files = FXCollections.observableArrayList(lista);

        files.addListener(new ListChangeListener()
        {
            @Override
            public void onChanged(Change change)
            {

            }
        });
        this.conferences = FXCollections.observableArrayList(service.getAllConf());

        confCombo.setItems(conferences);

        confCombo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1)
            {
                ObservableList combox2 = FXCollections.observableArrayList((List) service.findByConfId(confCombo.getValue().getIdConference()));
                sesCombo.setItems(combox2);
                List<String> lst = service.returnDeadline(confCombo.getValue().getIdConference());
                abstractLabel.setText(lst.get(0));
                proposalLabel.setText(lst.get(1));

            }
        });

        fileTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
        {
            if (newSelection != null)
            {
                keyText.setText(((File) newSelection).getKeywords().toString());
                topText.setText(((File) newSelection).getTopic());
                absText.setText(((File) newSelection).getAbstractData());
                linkText.setText(((File) newSelection).getFiledoc());
                propText.setText(((File) newSelection).getTitlu());
                ObservableList<Author> autori1 = FXCollections.observableArrayList(service.getAfterFileId(((File) newSelection).getIdF()));
                listAuthor.setItems(autori1);
                autr.clear();
                autr.addAll(autori1);
                ObservableList<Author> autori2 = FXCollections.observableArrayList(service.getAfterAuthotNOTid(autr));
                authorCombo.setItems(autori2);


            }
        });

        fileButton.setOnAction(e ->
        {
            try {
                String website = fileTable.getSelectionModel().getSelectedItem().getFiledoc();
                Desktop.getDesktop().browse(new URI(website));
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            } catch (URISyntaxException e1)
            {
                e1.printStackTrace();
            }
            catch(NullPointerException el)
            {
                showErrorMessage("Select a file!");
            }
        });

        partikipButton.setOnAction(e ->
        {
            int idses = sesCombo.getValue().getIdSection();
            int ok = service.registerToConference(idses,idforfile);
            if (ok == 1)
                showMessage(Alert.AlertType.CONFIRMATION, "Succes", "The registration was done with success");
            else
                showErrorMessage("Error registering to event");
        });

        aut = FXCollections.observableArrayList(service.getAllAuthors());
        authorCombo.setItems(aut);
        fileTable.setItems(files);
        autr.addAll(service.getAllAuthors());
    }

    @FXML
    public void setLogoutAction()
    {
        loginManager.logOut();
    }


    @FXML
    public void setUpload()
    {
        try {
            if (autr.isEmpty()) {
                showErrorMessage("Introduce at least one author");
            } else {
                String prop = propText.getText();
                String key = keyText.getText();
                String top = topText.getText();
                String abs = absText.getText();
                String link = linkText.getText();
                String deadline = proposalLabel.getText();
                int idses = sesCombo.getValue().getIdSection();
                if (prop.isEmpty() || key.isEmpty() || top.isEmpty() || abs.isEmpty() || link.isEmpty())
                    showErrorMessage("Please fill all the fields");
                else {
                    int ok = service.uploadFile(prop, key, top, link, abs, autr, idses, deadline);
                    if (ok == 1) {
                        lista = service.getAllFiles();
                        autr.clear();
                        files.clear();
                        files = FXCollections.observableArrayList(lista);
                        fileTable.refresh();
                        showMessage(Alert.AlertType.CONFIRMATION, "Succes", "The file was added with success");
                    } else if (ok == 2) {
                        showErrorMessage("The date is invalid");
                        setOnClear();
                    } else if (ok == 3) {
                        showErrorMessage("You cannot upload files anymore");
                        setOnClear();
                    }
                }
            }
        }
        catch(NullPointerException el)
        {
            showErrorMessage("Select a file!");
        }


    }


    @FXML
    public void setUpdate()
    {
        try {
            if (autr.isEmpty()) {
                showErrorMessage("Introduce at least one author");
            } else {
                String prop = propText.getText();
                String key = keyText.getText();
                String top = topText.getText();
                String abs = absText.getText();
                String link = linkText.getText();
                String deadline = abstractLabel.getText();
                int idses = sesCombo.getValue().getIdSection();
                int idf = fileTable.getSelectionModel().getSelectedItem().getIdF();
                if (prop.isEmpty() || key.isEmpty() || top.isEmpty() || abs.isEmpty() || link.isEmpty())
                    showErrorMessage("Please fill all the fields");
                else {
                    int ok = service.updateFile(prop, key, top, link, abs, autr, deadline, idses, idf);
                    if (ok == 1) {
                        lista = service.getAllFiles();
                        autr.clear();
                        authorCombo.setItems(aut);
                        files.clear();
                        files = FXCollections.observableArrayList(service.getAfterFileId(idforfile));
                        fileTable.refresh();
                        showMessage(Alert.AlertType.CONFIRMATION, "Succes", "The file was updated with success");
                    } else if (ok == 2) {
                        showErrorMessage("The date is invalid");
                        setOnClear();
                    } else if (ok == 3) {
                        showErrorMessage("You cannot update files anymore");
                        setOnClear();
                    }
                }
            }
        }
        catch(NullPointerException el)
        {
            showErrorMessage("Select a file!");
        }

    }

    @FXML
    public void setConfirm()
    {
        autr.add(authorCombo.getValue());
        Author del = authorCombo.getValue();
        authorCombo.getItems().remove(del);
    }

    @FXML
    public void setOnClear()
    {
        authorCombo.setItems(aut);
        keyText.clear();
        topText.clear();
        absText.clear();
        linkText.clear();
        propText.clear();
        autr.clear();
        listAuthor.getItems().clear();
    }

    static void showMessage(Alert.AlertType type, String header, String text)
    {
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    static void showErrorMessage(String text)
    {
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }
}
