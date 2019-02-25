package login;

import admin.AdminController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import student.StudentController;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    //NOTE: The format of the widget name must match ID name
    //NOTE: Always watch your import, avoid java.AWT

    @FXML
    private Label dbStatus;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private ComboBox<Option> comboBox;
    @FXML
    private Button loginBtn;
    @FXML
    private Label loginStatus;

    private LoginModel loginModel = new LoginModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.loginModel.isDBConnected()) {
            this.dbStatus.setText("Connected To Database");
        } else {
            this.dbStatus.setText("Not Connected To Database");
        }

        this.comboBox.setItems(FXCollections.observableArrayList(Option.values()));
    }


    @FXML
    public void Login(ActionEvent event) {
        try {
            if (this.loginModel.isLoggedIn(
                    this.username.getText().toString(),
                    this.password.getText().toString(),
                    ((Option) this.comboBox.getValue()).toString()
            )) {
                Stage stage = (Stage) this.loginBtn.getScene().getWindow();
                stage.close();
                switch (((Option) this.comboBox.getValue()).toString()) {
                    case "Admin":
                        adminLogin();
                        break;
                    case "Student":
                        studentLogin();
                        break;
                    default:
                        adminLogin();
                        break;
                }
            } else {
                this.loginStatus.setText("Invalid username or password");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getComboBox(String s){
        if (s == null){
            return "Admin";
        }
        else {
            return s;
        }
    }

    public void studentLogin() {

        try {
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) loader.load(getClass().getResource("/student/student.fxml").openStream());

            StudentController studentController = (StudentController) loader.getController();
            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Student Dashboard");
            userStage.setResizable(false);
            userStage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void adminLogin() {
        try {
            Stage adminStage = new Stage();
            FXMLLoader adminLoader = new FXMLLoader();
            Pane adminRoot = (Pane) adminLoader.load(getClass().getResource("/admin/admin.fxml").openStream());
            AdminController studentController = (AdminController) adminLoader.getController();

            Scene adminScene = new Scene(adminRoot);
            adminStage.setScene(adminScene);
            adminStage.setTitle("Admin Dashboard");
            adminStage.setResizable(false);
            adminStage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
