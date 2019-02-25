package admin;

import dbUtil.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dob;

    @FXML
    private TableView<StudentData> studentDataTableView;

    @FXML
    private TableColumn<StudentData, String> idColumn;

    @FXML
    private TableColumn<StudentData, String> firstNameColumn;

    @FXML
    private TableColumn<StudentData, String> lastNameColumn;

    @FXML
    private TableColumn<StudentData, String> emailColumn;

    @FXML
    private TableColumn<StudentData, String> dobColumn;

    private DBConnection dbConnection;
    private ObservableList<StudentData> studentData;

    private String sql = "SELECT * FROM students";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dbConnection = new DBConnection();
    }

    @FXML
    private void loadStudentData(ActionEvent event) throws SQLException {
        try {

            // Note this pattern
            Connection connection = DBConnection.getConnection();
            this.studentData = FXCollections.observableArrayList();

            ResultSet rs = connection.createStatement().executeQuery(sql);
            while (rs.next()) {
                this.studentData.add(new StudentData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            ;
        }

        this.idColumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("ID"));
        this.firstNameColumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("firstName"));
        this.lastNameColumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("lastName"));
        this.emailColumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("email"));
        this.dobColumn.setCellValueFactory(new PropertyValueFactory<StudentData, String>("DOB"));

        this.studentDataTableView.setItems(null);
        this.studentDataTableView.setItems(this.studentData);

    }

    @FXML
    private void addStudent(ActionEvent event) throws SQLException {
        String sqlInsert = "INSERT INTO students(id,fname,lname,email,DOB) VALUES (?,?,?,?,?)";
        Connection connection = DBConnection.getConnection();


        try {
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.setString(1, this.id.getText());
            statement.setString(2, this.firstName.getText());
            statement.setString(3, this.lastName.getText());
            statement.setString(4, this.email.getText());
            statement.setString(5, this.dob.getEditor().getText());

            statement.execute();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            connection.close();
        }
    }

    @FXML
    private void clearFields (ActionEvent event){
        this.id.setText("");
        this.firstName.setText("");
        this.lastName.setText("");
        this.email.setText("");
        this.dob.setValue(null);
    }
}
