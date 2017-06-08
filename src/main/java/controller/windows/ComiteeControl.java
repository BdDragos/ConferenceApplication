package controller.windows;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.Main;
import model.*;
import repository.AttendantRepository;
import repository.AuthorsRepository;
import repository.CMRepository;
import repository.FileRepository;
import services.ConfService;
import services.ParticipantsService;
import services.SectionService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dragos on 5/8/2017.
 */


public class ComiteeControl
{
    final Main loginManager;

    public ComiteeControl(final Main loginManager, ConfService cs, SectionService ss, FileRepository fr, ParticipantsService participantsService,  AttendantRepository attendantRepository, AuthorsRepository authorsRepository, CMRepository cmRepository)
    {
        this.ctrlConf = cs;
        this.ctrlSection = ss;
        this.repoFile =fr;
        this.participantsService = participantsService;
        this.attendantRepository = attendantRepository;
        this.authorsRepository = authorsRepository;
        this.cmRepository = cmRepository;

        this.loginManager = loginManager;
    }

    @FXML
    public void setLogoutAction()
    {
        loginManager.logOut();
    }
    AttendantRepository attendantRepository;
    AuthorsRepository authorsRepository;
    ConfService ctrlConf;
    SectionService ctrlSection;
    FileRepository repoFile;
    ParticipantsService participantsService;
    CMRepository cmRepository;

    @FXML private TableView<Conference> confTable;
    @FXML private TableColumn<Conference, String> confName;
    @FXML private TableColumn<Conference, Integer> confNopart;
    @FXML private TableColumn<Conference, String> confDeadlineProp;
    @FXML private TableColumn<Conference, String> confDeadlineAbs;

    @FXML private TableView<Sections> sectionTable;
    @FXML private TableColumn<Sections, String> sectionName;
    @FXML private TableColumn<Sections, Integer> sectionSeschair;
    @FXML private TableColumn<Sections, String> sectionHour;
    @FXML private TableColumn<Sections, String> sectionDate;


    @FXML private TableView<File> tablePart;
    @FXML private TableColumn<Participants, Integer> tabId;
    @FXML private TableColumn<Participants, String> tabPart;
    @FXML private TableColumn<Participants, String> tabAtt;

    @FXML private TableView<File> revTable;
    @FXML private TableColumn<File, String> fileTitlu;
    @FXML private TableColumn<File, String> fileDoc;



    @FXML private TextField taConfName;
    @FXML private TextField taNoPartConf;
    @FXML private TextField taConfDeadlineProp;
    @FXML private TextField taConfDeadlineAbs;
    @FXML private TextField taSectionName;
    @FXML private TextField taSectionChair;
    @FXML private TextField taSectionHour;
    @FXML private TextField taSectionDate;

    @FXML private Button btConfSave;
    @FXML private Button btConfUpdate;
    @FXML private Button btConfDelete;
    @FXML private Button btSectionSave;
    @FXML private Button btSectionUpdate;
    @FXML private Button btSectionDelete;
    @FXML private Button btAcceptFile;
    @FXML private Button btRejectFile;
    @FXML private Button cmButtonSC;

    @FXML private ComboBox<CM> cmComboBoxSC;


    private ObservableList conferences;
    private List<Conference> confList = new ArrayList<Conference>();

    private ObservableList sections;
    private List<Sections> sectionList = new ArrayList<Sections>();

    private ObservableList participantsOList;
    private List<Participants> participantsList;

    private ObservableList authorOList;
    private List<Author> authorList;

    private ObservableList attendantOList;
    private List<Attendant> attendantList;

    private ObservableList cmOList;
    private List<CM> cmList;


    private ObservableList files;
    private List<File> fileList = new ArrayList<File>();

    public void upFileTable(){
        fileTitlu.setCellValueFactory(new PropertyValueFactory<File, String>("titlu"));
        fileDoc.setCellValueFactory(new PropertyValueFactory<File, String>("filedoc"));
        //fileList = repoFile.getAllBorderline();
        //this.files = FXCollections.observableArrayList(fileList);
    }
    @FXML
    public void initialize()
    {
        cmList = cmRepository.getAll();
        cmOList = FXCollections.observableArrayList(cmList);
        cmComboBoxSC.setItems(cmOList);

        participantsList = participantsService.getAll();
        participantsOList = FXCollections.observableArrayList(participantsList);

        authorList = authorsRepository.getAllAuthor();
        authorOList = FXCollections.observableArrayList(authorList);
        attendantList = attendantRepository.getAll();
        attendantOList = FXCollections.observableArrayList(attendantList);
        //tablePart.setItems(participantsOList);

        sectionTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Sections>() {
            @Override
            public void changed(ObservableValue<? extends Sections> observable, Sections oldValue, Sections newValue) {
                if (newValue != null) {
                    participantsList = participantsService.getAll().stream().filter(p->p.getIds()==newValue.getIdSection()).collect(Collectors.toList());
                    if (participantsList.size() < 1) {
                        tablePart.getItems().removeAll();
                    }
                    else {
                        participantsOList = FXCollections.observableArrayList(participantsList);
                        tablePart.getItems().removeAll();
                        tablePart.setItems(participantsOList);
                    }
                    fileList = repoFile.getAllBorderline().stream().filter(f -> f.getIdses() == newValue.getIdSection()).collect(Collectors.toList());
                    for (File f : fileList)
                        System.out.println(f.toString());
                    if (fileList.size() < 1) {
                        revTable.getItems().removeAll();
                    }
                    else {
                        files = FXCollections.observableArrayList(fileList);
                        revTable.getItems().removeAll();
                        revTable.setItems(files);
                    }
                }
            }
        });

        tabId.setCellValueFactory(new PropertyValueFactory<Participants, Integer>("idpa"));
        tabPart.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Participants, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Participants, String> p) {
                if (p.getValue() != null) {
                    Participants pp = p.getValue();
                    if (pp.getIda() != null) {
                        Author author = authorList.stream().filter(a->a.getIda()==pp.getIda()).collect(Collectors.toList()).get(0);
                        if (author != null)
                            return new SimpleStringProperty(author.getName());
                        else
                            return new SimpleStringProperty("N/A");
                    }
                    else return new SimpleStringProperty("N/A");
                } else {
                    return new SimpleStringProperty("N/A");
                }
            }
        });
        tabAtt.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Participants, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Participants, String> p) {
                if (p.getValue() != null) {
                    Participants pp = p.getValue();
                    if (pp.getIdat() != null) {
                        Attendant attendant = attendantList.stream().filter(a->a.getIdat()==pp.getIdat()).collect(Collectors.toList()).get(0);
                        if (attendant != null)
                            return new SimpleStringProperty(attendant.getUsername());
                        else
                            return new SimpleStringProperty("N/A");
                    }
                    else return new SimpleStringProperty("N/A");
                } else {
                    return new SimpleStringProperty("N/A");
                }
            }
        });

        confName.setCellValueFactory(new PropertyValueFactory<Conference, String>("name"));
        confNopart.setCellValueFactory(new PropertyValueFactory<Conference, Integer>("noParticipants"));
        confDeadlineProp.setCellValueFactory(new PropertyValueFactory<Conference, String>("deadlineProposal"));
        confDeadlineAbs.setCellValueFactory(new PropertyValueFactory<Conference, String>("deadlineAbstract"));

        sectionName.setCellValueFactory(new PropertyValueFactory<Sections, String>("name"));
        sectionSeschair.setCellValueFactory(new PropertyValueFactory<Sections, Integer>("sesChair"));
        sectionHour.setCellValueFactory(new PropertyValueFactory<Sections, String>("hour"));
        sectionDate.setCellValueFactory(new PropertyValueFactory<Sections, String>("date"));

        //this.ctrlConf = new ConfService(this.repoConf);
        //this.ctrlSection = new SectionService(this.repoSection);
        confList = ctrlConf.getAll();

        this.conferences = FXCollections.observableArrayList(confList);
        upFileTable();
        confTable.setItems(conferences);
    }


    @FXML
    private void cmButtonSCOnAction() {
        CM cm = cmComboBoxSC.getSelectionModel().getSelectedItem();
        if (cm == null)
            return;
        Sections sections = sectionTable.getSelectionModel().getSelectedItem();
        if (sections == null)
            return;
        ctrlSection.updateSection(sections.getIdSection(),cm.getId(),sections.getName(), sections.getHour(), sections.getDate());
        sectionTable.getSelectionModel().getSelectedItem().setSesChair(cm.getId());
    }

    @FXML
    public List<Conference> getAllConf() {
        return ctrlConf.getAll();
    }

    @FXML
    public Integer saveConf() {
        return ctrlConf.addConference(Integer.parseInt(taNoPartConf.getText()), taConfName.getText(), taConfDeadlineProp.getText(),taConfDeadlineAbs.getText());
    }

    @FXML
    public void updateConf() {
        int confId = ((Conference)confTable.getSelectionModel().getSelectedItem()).getIdConference();
        ctrlConf.updateConference(confId, Integer.parseInt(taNoPartConf.getText()), taConfName.getText(), taConfDeadlineProp.getText(),taConfDeadlineAbs.getText());
    }

    @FXML
    public void deleteConf() {
        int confId = ((Conference)confTable.getSelectionModel().getSelectedItem()).getIdConference();
        ctrlConf.deleteConference(confId);
    }

    @FXML
    public void getAllSection() {
        ctrlSection.getAll();
    }

    @FXML
    public void fillSection() {
        int confId = ((Conference)confTable.getSelectionModel().getSelectedItem()).getIdConference();
        String name = ((Conference)confTable.getSelectionModel().getSelectedItem()).getName();
        int no = ((Conference)confTable.getSelectionModel().getSelectedItem()).getNoParticipants();
        String dprop = ((Conference)confTable.getSelectionModel().getSelectedItem()).getDeadlineProposal();
        String dabs = ((Conference)confTable.getSelectionModel().getSelectedItem()).getDeadlineAbstract();
        taConfName.setText(name);
        taNoPartConf.setText(Integer.toString(no));
        taConfDeadlineProp.setText(dprop);
        taConfDeadlineAbs.setText(dabs);
        taSectionName.setText("");
        taSectionChair.setText("");
        taSectionDate.setText("");
        taSectionHour.setText("");
        sectionList = ctrlSection.getAllSectionByConfId(confId);
        this.sections = FXCollections.observableArrayList(sectionList);
        sectionTable.setItems(sections);
    }

    @FXML
    public void fillSectionFields(){
        String name = ((Sections)sectionTable.getSelectionModel().getSelectedItem()).getName();
        int sesc = ((Sections)sectionTable.getSelectionModel().getSelectedItem()).getSesChair();
        String hour = ((Sections)sectionTable.getSelectionModel().getSelectedItem()).getHour();
        String date = ((Sections)sectionTable.getSelectionModel().getSelectedItem()).getDate();
        taSectionName.setText(name);
        taSectionChair.setText(Integer.toString(sesc));
        taSectionHour.setText(hour);
        taSectionDate.setText(date);
    }

    @FXML
    public Integer saveSection() {
        return ctrlSection.addSection(((Conference)confTable.getSelectionModel().getSelectedItem()).getIdConference(),Integer.parseInt(taSectionChair.getText()),taSectionName.getText(),taSectionHour.getText(),taSectionDate.getText());
    }

    @FXML
    public void updateSection() {
        ctrlSection.updateSection(((Sections)sectionTable.getSelectionModel().getSelectedItem()).getIdSection(),Integer.parseInt(taSectionChair.getText()),taSectionName.getText(),taSectionHour.getText(),taSectionDate.getText());
    }

    @FXML
    public void deleteSection() {
        ctrlSection.deleteSection(((Sections)sectionTable.getSelectionModel().getSelectedItem()).getIdSection());
    }

    @FXML
    public void acceptFile(){
        ((File)revTable.getSelectionModel().getSelectedItem()).setLevel("Accept");
        //upFileTable();
    }

    @FXML
    public void rejectFile() {
        ((File)revTable.getSelectionModel().getSelectedItem()).setLevel("Reject");
        //upFileTable();
    }
}
