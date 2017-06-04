package controller.windows;

import com.sun.jmx.mbeanserver.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.Main;
import model.File;
import repository.FileRepository;
import repository.ReviewerRepository;
import utils.AcceptLevel;

import java.awt.*;
import java.awt.TextArea;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragos on 5/8/2017.
 */
public class ReviewerControl {
    private ReviewerRepository reviewRepo;
    private FileRepository fileRepo;
    private ReviewerRepository revRepo;

    @FXML
    ComboBox<AcceptLevel> acceptCB = new ComboBox<>();
    @FXML
    private TableView<File> fileTable = new TableView<>();
    @FXML
    private Button reviewButton;
    @FXML
    private Button openFileBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button hideMineBtn;
    @FXML
    private javafx.scene.control.TextArea absArea;
    public ObservableList<File> fileList = FXCollections.observableArrayList();
    final Main loginManager;
    //@FXML
    //private TextArea absArea;

    public ReviewerControl(final Main loginManager,FileRepository fileRepo)
    {
        this.loginManager = loginManager;
        this.fileRepo = fileRepo;
        int temp = 0;
    }
    public ReviewerControl(final Main loginManager,FileRepository fileRepo, ReviewerRepository revRepo)
    {
        this.loginManager = loginManager;
        this.fileRepo = fileRepo;
        this.reviewRepo = revRepo;
        int temp = 0;
    }
    public void initData(){
        fileTable.getItems().clear();
        for(File f : fileRepo.getAll()) {
            fileList.add(f);
        }

        fileTable.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                absArea.setText(fileTable.getSelectionModel().getSelectedItem().getAbstractData());
                //System.out.println(fileTable.getSelectionModel().getSelectedItem());
            }
        });
        fileTable.setItems(fileList);
        acceptCB.getItems().addAll(AcceptLevel.values());

        //delete
        openFileBtn.setOnAction(e -> {
            try {
                String website = fileTable.getSelectionModel().getSelectedItem().getFiledoc();
                Desktop.getDesktop().browse(new URI(website));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
              catch(NullPointerException el){
                showErrorMessage("Selectati ceva!");
            }
        });

        reviewButton.setOnAction(event -> {
            try{
                File oldFile = fileTable.getSelectionModel().getSelectedItem();
                int reviewCount = fileTable.getSelectionModel().getSelectedItem().getReviewCount();
                int id = fileTable.getSelectionModel().getSelectedItem().getIdF();
                List<Integer> ownFileIds = getFileIdsFromLoginId();
                if(reviewCount < 4){
                    if(ownFileIds.contains(fileTable.getSelectionModel().getSelectedItem().getIdF()))
                        showErrorMessage("You can't review your own paper!");
                    else {
                        String currentLevel = fileTable.getSelectionModel().getSelectedItem().getLevel();
                        int currentLevelNo = mapLevelToInt(currentLevel);
                        int selectedLevelNo = mapLevelToInt(acceptCB.getSelectionModel().getSelectedItem().toString());
                        int average;
                        if (reviewCount == 0)
                            average = selectedLevelNo;
                        else
                            average = (currentLevelNo * reviewCount + selectedLevelNo) / (reviewCount + 1);
                        String averageLevel = mapIntToLevel(average);
                        fileRepo.getById(id).setLevel(averageLevel);
                        fileRepo.getById(id).setReviewCount(++reviewCount);
                        File newFile = fileRepo.getById(id);
                        fileTable.getColumns().get(0).setVisible(false);
                        fileTable.getColumns().get(0).setVisible(true);
                        fileRepo.updateFile(oldFile, newFile);
                    }
                }
                else
                    showErrorMessage("Too many reviewers already");
            }catch(Exception ex){
                showErrorMessage(ex.toString());
            }
        });

        logoutBtn.setOnAction(event -> loginManager.logOut());

        hideMineBtn.setOnAction(event -> {
            for(File f : fileRepo.getAll()){
                fileList.remove(f);
            }
            fileTable.setItems(fileList);
            fileTable.getColumns().get(0).setVisible(false);
            fileTable.getColumns().get(0).setVisible(true);
            //System.out.println(fileRepo.getAllPairs().get(0));
            List<Integer> listIds = new ArrayList<>();//salvez id-urile fisierelor de sters

            for(FileRepository.Pair p : fileRepo.getAllPairs())
                if(p.getIdO() == reviewRepo.getIdLogin()){
                    listIds.add(p.getIdD());
                }
            for(File f : fileRepo.getAll()) {
                if(listIds.contains(f.getIdF())){}
                else{
                    fileList.add(f);
                }

            }
            fileTable.setItems(fileList);
            fileTable.getColumns().get(0).setVisible(false);
            fileTable.getColumns().get(0).setVisible(true);
        });

    }

    public List<Integer> getFileIdsFromLoginId(){
        List<Integer> listIds = new ArrayList<>();
        for(FileRepository.Pair p : fileRepo.getAllPairs())
            if(p.getIdO() == reviewRepo.getIdLogin()){
                listIds.add(p.getIdD());
            }
        return listIds;
    }
    private String mapIntToLevel(int average) {
        if(average == 1)
            return "StrongAccept";
        else if(average == 2)
            return "Accept";
        else if(average == 3)
            return "WeakAccept";
        else if(average == 4)
            return "Borderline";
        else if(average == 5)
            return "WeakReject";
        else if(average == 6)
            return "Reject";
        else if(average == 7)
            return "StrongReject";

        return null;
    }

    private int mapLevelToInt(String currentLevel) {
        if(currentLevel.equals("StrongAccept"))
            return 1;
        else if(currentLevel.equals("Accept"))
            return 2;
        else if(currentLevel.equals("WeakAccept"))
            return 3;
        else if(currentLevel.equals("Borderline"))
            return 4;
        else if(currentLevel.equals("WeakReject"))
            return 5;
        else if(currentLevel.equals("Reject"))
            return 6;
        else if(currentLevel.equals("StrongReject"))
            return 7;
        return 0;

    }

    public void showErrorMessage(String s){
        Alert alert = new Alert(Alert.AlertType.ERROR, s);
        alert.showAndWait();
    }


}
