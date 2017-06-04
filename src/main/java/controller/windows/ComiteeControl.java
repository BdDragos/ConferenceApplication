package controller.windows;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import model.Conference;
import model.File;
import model.Sections;
import repository.ConfRepository;
import repository.FileRepository;
import repository.SectionRepository;
import services.AuthorService;
import services.ConfService;
import services.ReviewerService;
import services.SectionService;
import sun.swing.SwingUtilities2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragos on 5/8/2017.
 */


public class ComiteeControl
{
    final Main loginManager;

    public ComiteeControl(final Main loginManager, ConfService cs, SectionService ss, FileRepository fr)
    {
        this.ctrlConf = cs;
        this.ctrlSection = ss;
        this.repoFile =fr;

        this.loginManager = loginManager;
    }

    @FXML
    public void setLogoutAction()
    {
        loginManager.logOut();
    }

    ConfService ctrlConf;
    SectionService ctrlSection;
    FileRepository repoFile;

    //ConfRepository repoConf;
    //SectionRepository repoSection;

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


    private ObservableList conferences;
    private List<Conference> confList = new ArrayList<Conference>();

    private ObservableList sections;
    private List<Sections> sectionList = new ArrayList<Sections>();


    private ObservableList files;
    private List<File> fileList = new ArrayList<File>();

    public void upFileTable(){
        fileList = repoFile.getAllBorderline();
        this.files = FXCollections.observableArrayList(fileList);
        revTable.setItems(files);

    }
    @FXML
    public void initialize()
    {
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

        conferences.addListener(new ListChangeListener()
        {
            @Override
            public void onChanged(Change change)
            {

            }
        });

//        sections.addListener(new ListChangeListener()
//        {
//            @Override
//            public void onChanged(Change change)
//            {
//
//            }
//        });

        files.addListener(new ListChangeListener()
        {
            @Override
            public void onChanged(Change change)
            {

            }
        });


        confTable.setItems(conferences);
       // sectionTable.setItems(sections);


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
        upFileTable();
    }

    @FXML
    public void rejectFile() {
        ((File)revTable.getSelectionModel().getSelectedItem()).setLevel("Reject");
        upFileTable();
    }
}
