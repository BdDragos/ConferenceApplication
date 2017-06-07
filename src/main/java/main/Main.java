package main;

import controller.AdminController;
import controller.DefaultUserController;
import controller.ParticipantsController;
import controller.windows.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import database.Database;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.Attendant;
import model.Participants;
import org.hibernate.SessionFactory;
import repository.*;
import services.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Dragos on 4/4/2017.
 */

public class Main extends Application
{
    private FXMLLoader loader;
    private FXMLLoader loader2;
    private FXMLLoader loader3;
    private FXMLLoader loader4;
    private FXMLLoader loader5;
    private FXMLLoader loader6;

    private Stage primaryStage;
    private AnchorPane rootLayout1;
    private AnchorPane rootLayout2;
    private AnchorPane rootLayout3;
    private AnchorPane rootLayout4;
    private AnchorPane rootLayout5;
    private AnchorPane rootLayout6;

    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    private Scene scene4;
    private Scene scene5;
    private Scene scene6;


    private LoginControl controlLogin;
    private ComiteeControl controlComitee;
    private ReviewerControl controlReviewer;
    private AttendantControl controlAttendant;
    private AuthorControl controlAuthor;
    private AdminWindowController controlAdmin;

    // Repositories
    private CMRepository CMLRepository;
    private AttendantRepository ATLRepository;
    private AuthorsRepository AULRepository;
    private ReviewerRepository RVWRepo;
    private AdminRepository adminRepository;
    private ParticipantsRepository participantsRepository;

    // Controllers
    private AdminController adminController;
    private DefaultUserController defaultUserController;
    private ParticipantsController participantsController;


    // Services
    private AdminService adminService;
    private DefaultUserService defaultUserService;
    private ParticipantsService participantsService;


    private static void execute(String sql)
    {

    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage primaryStage)
    {
        Database dtb = new Database();
        SessionFactory factory = dtb.getConnection();

        // Repositories
        SectionRepository secRepo = new SectionRepository();
        FileRepository fileRepo = new FileRepository();
        ConfRepository cr = new ConfRepository();
        CMRepository CMLRepository = new CMRepository();
        AttendantRepository ATLRepository = new AttendantRepository();
        AuthorsRepository AULRepository = new AuthorsRepository(0);
        adminRepository = new AdminRepository(factory);
        ReviewerRepository RVWRepo = new ReviewerRepository();
        DefaultUserRepository defaultUserRepository = new DefaultUserRepository(factory);
        participantsRepository = new ParticipantsRepository(factory);


        // Controllers
        adminController = new AdminController(adminRepository);
        defaultUserController = new DefaultUserController(defaultUserRepository);
        participantsController = new ParticipantsController(participantsRepository);

        // Services
        adminService = new AdminService(adminController);
        defaultUserService = new DefaultUserService(defaultUserController);
        participantsService = new ParticipantsService(participantsController);

        this.primaryStage = primaryStage;
        loader = new FXMLLoader();
        loader2 = new FXMLLoader();
        loader3 = new FXMLLoader();
        loader4 = new FXMLLoader();
        loader5 = new FXMLLoader();
        loader6 = new FXMLLoader();
        ConfService cs = new ConfService(cr);
        SectionService ss = new SectionService(secRepo);

        try
        {
            String pathToFxml = "src/main/resources/LoginWindow.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader.setLocation(fxmlUrl);

            controlLogin = new LoginControl(CMLRepository,ATLRepository,AULRepository,RVWRepo,adminService, defaultUserService);
            loader.setController(controlLogin);
            rootLayout1 = loader.load();
            scene1 = new Scene(rootLayout1);
        }

        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        try
        {
            String pathToFxml = "src/main/resources/ReviewerWindow.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader2.setLocation(fxmlUrl);


            //controlReviewer = new ReviewerControl(this,fileRepo);
            controlReviewer = new ReviewerControl(this,fileRepo, RVWRepo);
            loader2.setController(controlReviewer);
            rootLayout2 = loader2.load();
            controlReviewer.initData();
            scene2 = new Scene(rootLayout2);
        }

        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        try
        {
            String pathToFxml = "src/main/resources/ComiteeWindow.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader3.setLocation(fxmlUrl);


            controlComitee = new ComiteeControl(this,cs,ss,fileRepo);
            loader3.setController(controlComitee);
            rootLayout3 = loader3.load();
            scene3 = new Scene(rootLayout3);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        try
        {
            String pathToFxml = "src/main/resources/AuthorWindow.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader4.setLocation(fxmlUrl);


            controlAuthor = new AuthorControl(this);
            loader4.setController(controlAuthor);
            rootLayout4 = loader4.load();
            scene4 = new Scene(rootLayout4);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        try
        {
            String pathToFxml = "src/main/resources/AdminWindow.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader5.setLocation(fxmlUrl);

            controlAdmin = new AdminWindowController(this, CMLRepository, ATLRepository, AULRepository, RVWRepo, adminService, defaultUserService, participantsService);
            loader5.setController(controlAdmin);
            rootLayout5 = loader5.load();
            scene5 = new Scene(rootLayout5);
        }

        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        try
        {
            String pathToFxml = "src/main/resources/AttendantWindow.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader6.setLocation(fxmlUrl);


            controlAttendant = new AttendantControl(this);
            loader6.setController(controlAttendant);
            rootLayout6 = loader6.load();
            //controlAttendant.initialize();
            scene6 = new Scene(rootLayout6);
        }

        catch (IOException ex)
        {
            ex.printStackTrace();
        }


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        LoginView();
    }

    public void authenticated(int check,int idforfile)
    {
        if (check == 2)
            ReviewerView();
        if (check == 3)
            ComitteeView();
        if (check == 4)
            AuthorView(idforfile);
        if (check == 5)
            AdminView();
        if (check == 6)
            AttendantView();
    }

    public void logOut()
    {
        LoginView();
    }

    public void LoginView()
    {
        primaryStage.setScene(scene1);
        primaryStage.show();
        controlLogin.initManager(this);
    }

    public void ReviewerView()
    {
        primaryStage.setScene(scene2);
        primaryStage.show();
    }

    public void ComitteeView()
    {
        primaryStage.setScene(scene3);
        primaryStage.show();
    }

    public void AuthorView(int idforfile)
    {
        primaryStage.setScene(scene4);
        primaryStage.show();
        controlAuthor.initialize(idforfile);
    }

    public void AdminView()
    {
        primaryStage.setScene(scene5);
        primaryStage.show();
        controlAdmin.initialize();
    }
    public void AttendantView()
    {
        primaryStage.setScene(scene6);
        primaryStage.show();
        controlAttendant.initialize();
    }
}
