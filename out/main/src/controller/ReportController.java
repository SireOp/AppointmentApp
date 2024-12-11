package controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.FirstLvDivision;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    //ObservableList<Appointment> contactReport = FXCollections.observableArrayList();
    @FXML
    private TableView<Appointment> contactReportTableView;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> contactNameColumn;
    @FXML
    private TableColumn<Customer, Integer> appointmentIdColumn;
    @FXML
    private TableColumn<Customer, String> titleColumn;
    @FXML
    private TableColumn<Customer, String> typeColumn;
    @FXML
    private TableColumn<Customer, String> descriptionColumn;
    @FXML
    private TableColumn<Customer, String> startDateColumn;
    @FXML
    private TableColumn<Customer, String> endDateColumn;
    @FXML
    private TableColumn<Customer, Integer> contactIdColumn;

    @FXML
    private TableView<Appointment> totalTableView;

    @FXML
    private TableColumn<Appointment, String> appTypColumn;
    @FXML
    private TableColumn<Appointment, Month> monthColumn;
    @FXML
    private TableColumn<Appointment, Integer> totalAppColumn;

    @FXML
    private TableView<Customer> cusReportTableView;

    @FXML
    private TableColumn<Customer, String> divisionNameColumn;

    @FXML
    private TableColumn<Customer, Integer> cusCountColumn;


    /**
     * contactReportTableView Sorts appointments based on contact
     * totalTableView Counts the types of appointments that appear and sorts them by month,
     * cusReportTableView sorts the customers by Division and numbers how many are from each division are all populated here
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            contactReportTableView.setItems(AppointmentDAO.getAllContactReport());
            contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("apDescription"));
            startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            totalTableView.setItems(AppointmentDAO.getAppCount());
            monthColumn.setCellValueFactory(new PropertyValueFactory<>("Month"));
            appTypColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
            totalAppColumn.setCellValueFactory(new PropertyValueFactory<>("TotalApps"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            cusReportTableView.setItems(CustomerDAO.getCountryReport());
            divisionNameColumn.setCellValueFactory(new PropertyValueFactory<>("cusDivisionName"));
            cusCountColumn.setCellValueFactory(new PropertyValueFactory<>("numCus"));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }


    /**
     * Sends the user back to the appointments page
     * @param event On press sends user to main page
     */
    public void back(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/view/appointments.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = (Parent) loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

}
