package controller;

import DAO.*;
import com.example.dpapp.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.sql.Connection;
import java.time.DayOfWeek;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AppointmentModifyController implements Initializable {
    ObservableList<Appointment> AllAppointments = FXCollections.observableArrayList();
    @FXML
    private ToggleGroup tgApp;
    @FXML
    private TextField appointmentIdTxtField;
    @FXML
    private TextField titleTxtField;
    @FXML
    private TextArea descriptionTxtArea;
    @FXML
    private TextField locationTxtField;
    @FXML
    private TextField typeTxtField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField customerIdTxtField;
    @FXML
    private TextField userIdTxtField;
    @FXML
    private TextField contactTxtField;
    @FXML
    private ComboBox<LocalTime> startTimeCombo;
    @FXML
    private ComboBox<LocalTime> endTimeCombo;
    @FXML
    private ComboBox<Contact> contactCombo;
    @FXML
    private ComboBox<Country> countryCombo;
    @FXML
    private ComboBox<Customer> customerIdCombo;
    @FXML
    private ComboBox<User> userIdCombo;
    @FXML
    private ComboBox<FirstLvDivision> firstLVDivisionComboBox;

    @FXML
    private Label firstLvDivisionLabel;
    @FXML
    private Label appointmentsLabel;
    @FXML
    private Label appointmentIdLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label customerIdLabel;
    @FXML
    private Label userIdLabel;
    @FXML
    private Label contactLabel;

    @FXML
    private RadioButton allRad;
    @FXML
    private RadioButton monthRad;
    @FXML
    private RadioButton weekRad;

    @FXML
    private Button addBtn2;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;

    @FXML
    private ToggleGroup appView;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, Integer> contactColumn;
    @FXML
    private TableColumn<Appointment, String> contactNameColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, Timestamp> startDateColumn;
    @FXML
    private TableColumn<Appointment, Timestamp> endDateColumn;
    @FXML
    private TableColumn<Appointment, Integer> CustomerIdColumn;
    @FXML
    private TableColumn<Appointment, Integer> UserIdColumn;
    @FXML
    private TableColumn<Appointment, String> locationColumn;

    final int numOfDays = 0;
    private Object date;
private Appointment orignalAppoinment;
    ObservableList<Appointment>CusList = FXCollections.observableArrayList();


    /**
     * initialize fills the appointmentTableview once the form loads
     * It also holds list for the combo boxes in the case the user intends to change them, from the values sent over from the appoinment page
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentTableView.setItems(AppointmentDAO.getAllAppointments());
            appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("apDescription"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            CustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            UserIdColumn.setCellValueFactory(new PropertyValueFactory<>("appUserId"));

            ObservableList<User> allUsers = UserDAO.getAllUsers();
            ObservableList<Integer> userId = FXCollections.observableArrayList();
            ObservableList<String> userName = FXCollections.observableArrayList();

            ObservableList<Customer> allCustomers = CustomerDAO.getAllCus();
            ObservableList<Integer> customerIds = FXCollections.observableArrayList();
            ObservableList<String> customerNames = FXCollections.observableArrayList();
            ObservableList<Country> allCountries = CountryDAO.getAllCountries();
            ObservableList<String> countryList = FXCollections.observableArrayList();
            ObservableList<Integer> countryIds = FXCollections.observableArrayList();

            //countryCombo.setItems(allCountries);
            allUsers.forEach(user -> {
                userId.add(user.getUserId());
                userName.add(user.getUserName());
            });
            allCustomers.forEach(customer -> {
                customerIds.add(customer.getCustomerId());
                //customerNames.add(customer.getCustomerName());
            });

            allCountries.forEach(country -> countryList.add(country.getCountryName()));
            ObservableList<FirstLvDivision> allDivisions = FirstLvDivisionDAO.getAllDivIds();
            ObservableList<String> divisionNames = FXCollections.observableArrayList();
            allDivisions.forEach(firstLvDivision -> divisionNames.add(firstLvDivision.getDivisionName()));
            userIdCombo.setItems(allUsers);
            customerIdCombo.setItems(allCustomers);
            ObservableList<FirstLvDivision> divisionList = FXCollections.observableArrayList();
            firstLVDivisionComboBox.setItems(divisionList);

            startTimeCombo.setItems(Appointment.getTime());
            endTimeCombo.setItems(Appointment.getTime());
            ObservableList<Contact> contactList = ContactDAO.getAllContacts();
            ObservableList<Integer> contactIds1 = FXCollections.observableArrayList();
            ObservableList<String> contactNames = FXCollections.observableArrayList();
            ObservableList<LocalTime> apTimes = FXCollections.observableArrayList();
/**
 * Lambda for contactList
 */
            contactList.forEach(contact -> {
                contactNames.add(contact.getContactName());
                contactIds1.add(contact.getContactId());
                //ContactDAO.contactIdName.put(contact.getContactName(), contact.getContactId());
            });
            LocalTime fAppointment = LocalTime.MIN.plusHours(8);
            LocalTime lAppointment = LocalTime.MAX.minusHours(1).minusHours(45);

            if (!fAppointment.equals(0) || !lAppointment.equals(0)) {
                while (fAppointment.isBefore(lAppointment)) {
                    apTimes.add(fAppointment);
                    fAppointment = fAppointment.plusMinutes(15);
                }
            }

/**
 *
 */
            contactCombo.setItems(contactList);
            countryCombo.setItems(allCountries);
            firstLVDivisionComboBox.setItems(allDivisions);
            /** Lambda
             * The StartDate Lambda sets up a listener on startDatePicker to change the endDatePicker the same date
             * The startTimeCombo Lambda adds 30 minutes onto the time selected in the startTimeCombo box
             */
            startDatePicker.valueProperty().addListener((observableValue, oldValueDate, newValueDate) -> endDatePicker.setValue(newValueDate.plusDays(numOfDays)));
            startTimeCombo.valueProperty().addListener((observableValue, oldValueTime, newValueTime) -> endTimeCombo.setValue(newValueTime.plusMinutes(30)));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void appInfo() {
        try {
            JDBC.openConnection();
            Appointment selectedApp = appointmentTableView.getSelectionModel().getSelectedItem();

            String fillContacts = null;
            if (selectedApp != null) {
                ObservableList<Contact> contactList = ContactDAO.getAllContacts();
                ObservableList<String> contactNames = FXCollections.observableArrayList();
                fillContacts = "";

                contactList.forEach(contact -> contactNames.add(contact.getContactName()));
                contactCombo.setItems(contactList);


                for (Contact contact : contactList) {
                    if (selectedApp.getContactId() == contact.getContactId()) {
                        fillContacts = contact.getContactName();
                    }
                }
            }

            appointmentIdTxtField.setText(String.valueOf(selectedApp.getAppointmentId()));
            titleTxtField.setText(selectedApp.getTitle());
            descriptionTxtArea.setText(selectedApp.getApDescription());
            locationTxtField.setText(selectedApp.getLocation());
            typeTxtField.setText(selectedApp.getLocation());
            customerIdTxtField.setText(String.valueOf(selectedApp.getCustomerId()));
            startDatePicker.setValue(selectedApp.getStartTime().toLocalDate());
            endDatePicker.setValue(selectedApp.getEndTime().toLocalDate());
            endTimeCombo.setValue(selectedApp.getEndTime().toLocalTime());
            userIdTxtField.setText(String.valueOf(selectedApp.getAppUserId()));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This receives the selected appointment from the appointment main page, so that it can populate the data fields in the update appointment page
     * @param app
     * @throws SQLException
     */
    public void sendAppointment(Appointment app) throws SQLException{
        this.orignalAppoinment = app;
        startTimeCombo.setItems(Appointment.getTime());
        endTimeCombo.setItems(Appointment.getTime());
        appointmentIdTxtField.setText(Integer.toString(app.getAppointmentId()));
        titleTxtField.setText(app.getTitle());
        descriptionTxtArea.setText(app.getApDescription());
        locationTxtField.setText(app.getLocation());
        typeTxtField.setText(app.getType());
        startDatePicker.setValue(app.getStartTime().toLocalDate());
        startTimeCombo.setValue(app.getStartTime().toLocalTime());
        endDatePicker.setValue(app.getEndTime().toLocalDate());
        endTimeCombo.setValue(app.getEndTime().toLocalTime());
        Contact con = ContactDAO.sendContact(app.getContactId());
        contactCombo.setValue(con);
        Customer cos = CustomerDAO.sendCustomer(app.getCustomerId());
        customerIdCombo.setValue(cos);
        User us = UserDAO.sendUserId(app.getAppUserId());
        userIdCombo.setValue(us);
        //userIdCombo.setValue();
    }


    public void updateCustomer(ActionEvent event) throws IOException, SQLException {

        String title = titleTxtField.getText();
        String apDescription = descriptionTxtArea.getText();
        String type = typeTxtField.getText();
        Contact contact = contactCombo.getValue();
        String location = locationTxtField.getText();

        int nCustomerId = Integer.parseInt(String.valueOf(customerIdCombo.getValue()));
        int appointmentId1 = Integer.parseInt(String.valueOf(appointmentIdTxtField.getText()));
        int appUserId = Integer.parseInt(String.valueOf(userIdCombo.getValue()));

        Contact selectedContactName = contact;
        LocalTime start = startTimeCombo.getValue();
        LocalTime end   =   endTimeCombo.getValue();
        LocalDateTime appStart = LocalDateTime.of(startDatePicker.getValue(), startTimeCombo.getValue());
        LocalDateTime appEnd = LocalDateTime.of(endDatePicker.getValue(), endTimeCombo.getValue());
        LocalDate ePicker = endDatePicker.getValue();
        LocalDate sPicker = startDatePicker.getValue();
        LocalDateTime originalSDate = orignalAppoinment.getStartTime();
        LocalDateTime originalEDate =  orignalAppoinment.getEndTime();
        LocalDateTime originalSTime = orignalAppoinment.getStartTime();
        LocalDateTime originalETime = orignalAppoinment.getEndTime();

        DayOfWeek appSDayCheck = startDatePicker.getValue().getDayOfWeek();
        DayOfWeek appEDayCheck = endDatePicker.getValue().getDayOfWeek();
        int contactId = contactCombo.getValue().getContactId();

        Connection conn = JDBC.getConnection();


        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
    for(Appointment appointment :allAppointments ){
        if(appointmentId1 != orignalAppoinment.getAppointmentId()){
            filteredAppointments.add(appointment);
        }
    }


        ObservableList<Customer> allCustomers = CustomerDAO.allCustomers(conn);
        ObservableList<Integer> holdCustomerId = FXCollections.observableArrayList();
        ObservableList<User> getAllUsers = UserDAO.getAllUsers();
        ObservableList<Integer> holdUserId = FXCollections.observableArrayList();

        boolean match = false;

        //boolean timeCheckStartDate = originalSDate.isEqual(ChronoLocalDateTime.from(sPicker));
        //boolean timeCheckEndDate = originalEDate.isEqual(ChronoLocalDateTime.from(ePicker));
        //boolean timeCheckStartTime = originalSTime.isEqual(ChronoLocalDateTime.from(start));
        //boolean timeCheckEndTime = originalETime.isEqual(ChronoLocalDateTime.from(end));

if(orignalAppoinment.getTitle().equals(title)&&
    orignalAppoinment.getApDescription().equals(apDescription)&&
    orignalAppoinment.getType().equals(type)&&
    orignalAppoinment.getLocation().equals(location)&&
        //timeCheckStartDate == true &&
       // timeCheckEndDate == true &&
        //timeCheckStartTime == true &&
        //timeCheckEndTime == true &&
    //orignalAppoinment.getStartTime().equals((start)) &&
   // orignalAppoinment.getEndTime().equals((end)) &&
        originalSTime.toLocalTime().equals(start) &&
        originalETime.toLocalTime().equals(end)&&
    orignalAppoinment.getAppUserId() == appUserId &&
    orignalAppoinment.getAppointmentId() == appointmentId1 &&
    orignalAppoinment.getCustomerId() == nCustomerId &&
    originalSDate.toLocalDate().isEqual(sPicker) &&
        originalEDate.toLocalDate().isEqual(ePicker) &&
    orignalAppoinment.getContactId() == contactId)
        {

    match = true;
}
//Check for overlaps inside of the else block
    if(match){
        Alert alert = new Alert(Alert.AlertType.WARNING, ("No changes were made are you sure you are done?"));
        alert.setTitle("No Changes made");
        alert.showAndWait();
        return;

    }
    else if (Appointment.OperatingHours(appStart, appEnd)) {
        Alert alert = new Alert(Alert.AlertType.WARNING, ("You must set a Start and End time that are within operating hours  to create this appointment"));
        alert.setTitle("No description was given");
        alert.showAndWait();
        return;

    } else if (Appointment.updateTimeCheck(nCustomerId, appointmentId1,appStart, appEnd)) {
        Alert alert = new Alert(Alert.AlertType.WARNING, ("You must schedule an appointment that does not overlap with another appointment  to create this appointment"));
        alert.setTitle("Scheduling Conflict");
        alert.showAndWait();
        return;


    } else if (Appointment.WorkWeek(appSDayCheck, appEDayCheck)) {
        Alert alert = new Alert(Alert.AlertType.WARNING, ("You must schedule an appointment during the work week"));
        alert.setTitle("Scheduled outside of Work week");
        alert.showAndWait();
        return;



    } else
        AppointmentDAO.modifyAppointment(appointmentId1, title, apDescription, location, type, appStart, appEnd, nCustomerId, appUserId, contactId);
        Alert create = new Alert(Alert.AlertType.CONFIRMATION);
        create.setTitle("Appointment Updated");
        create.setContentText("You're " + type + " on " + appSDayCheck + " " + sPicker + " from " + startTimeCombo.getValue() + "-" + endTimeCombo.getValue() + " has been updated");
        //create.getButtonTypes().addAll(ButtonType.YES);
        create.showAndWait();

try {
    AllAppointments = AppointmentDAO.getAllAppointments();
}catch (SQLException e){e.printStackTrace();}
        System.out.println("OK update appointment");
        appointmentTableView.setItems(AllAppointments);

    }


    /**
     * This was ment send an alert and push the date to the need week day if the user selected a weekend
     * Ended up just sticking to an alert that tells the user to select a date/Time within operating hours
     * @param date date from the selected date
     * @return
     */

    private LocalDate fNextWeekday(LocalDate date) {
        LocalDate previousDay = null;
        previousDay = previousDay.minusDays(1);
        LocalDate nextDay = date.plusDays(1);
        while (previousDay.getDayOfWeek() == DayOfWeek.SATURDAY || previousDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            previousDay = previousDay.minusDays(1);
        }
        while (nextDay.getDayOfWeek() == DayOfWeek.SATURDAY || nextDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            nextDay = nextDay.plusDays(1);
        }
        if (date.minusDays(previousDay.getDayOfWeek().getValue()).isBefore(nextDay.minusDays(date.getDayOfWeek().getValue()))) {
            return previousDay;
        } else {
            return nextDay;
        }

    }

    /**
     *
     * It alerts the user that if they have not saved their update changes that they will be lost
     * IF they press "ok" they will go back to the appointment page if they press cancel they stay on the current page
     * @param event Allows the user to go back to the appointment page
     */

    public void cancelModify(ActionEvent event) {

        Alert confirm = new Alert(Alert.AlertType.WARNING);
        confirm.setTitle("Changes Saved?");
        confirm.setContentText("Please confirm that all changes have been saved");
        confirm.getButtonTypes().clear();
        confirm.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.OK) {
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

    /**
     * Gets the selected appointment from the tableview then warns the user before deleting the appointment
     * @param event Deletes the selected appointment
     */
    public void deleteBtn(ActionEvent event) {

        Appointment selectedApp = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedApp ==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing selected");
            alert.setContentText("You must select a Scheduled appointment to remove");
            alert.showAndWait();
        } else {
            Alert confirm = new Alert(Alert.AlertType.WARNING);
            confirm.setTitle("Delete check");
            confirm.setContentText("Please confirm that you want to delete the selected appointment?");
            confirm.getButtonTypes().clear();
            confirm.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK){
                Alert delete = new Alert(Alert.AlertType.CONFIRMATION);
                delete.setTitle("Delete Appointment");
                delete.setContentText("Appointment ID# " + appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentId() + " for " + appointmentTableView.getSelectionModel().getSelectedItem().getType() + " was removed.");
                delete.getButtonTypes().clear();
                delete.getButtonTypes().addAll(ButtonType.OK);
                delete.showAndWait();

                AppointmentDAO.deleteAppointment(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentId());
                try {
                    AllAppointments = AppointmentDAO.getAllAppointments();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                appointmentTableView.setItems(AllAppointments);
                appointmentTableView.refresh();
            } else if (confirm.getResult() == ButtonType.CANCEL){
                confirm.close();
            }

        }


    }

    /**
     *
     * @param event Loads appointments grouped by month in the appointment tableview
     */
    public void monthApps(ActionEvent event) {
        appointmentTableView.setItems(AppointmentDAO.getByMonth());

    }

    /**
     *
     * @param event Loads appointments grouped by week in the appointment tableview
     */
    public void weekApps(ActionEvent event) {


        appointmentTableView.setItems(AppointmentDAO.getByWeek());
        appointmentTableView.setPlaceholder(new Label("Nothing is scheduled for this week"));
    }


    public void allApps(ActionEvent event) {

        try {
            appointmentTableView.setItems(AppointmentDAO.getAllAppointments());
            appointmentTableView.setPlaceholder(new Label("No scheduled appointments this month"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param event When the "Set Country" button is pressed as long as there is a selected country
     *              it will pull all the first Lv divisions that have that country ID
     *              There is array list for each of the three Countries
     */
    public void getDiv(ActionEvent event) {
        ObservableList<FirstLvDivision> allUSDivisions = FirstLvDivisionDAO.getUsDivIds();

        ObservableList<FirstLvDivision> allUkDivisions = FirstLvDivisionDAO.getUkDivIds();
        ObservableList<FirstLvDivision> allCaDivisions = FirstLvDivisionDAO.getCaDivIds();

        int countId = countryCombo.getValue().getCountryId();
        if(countId == 1 ){
            firstLVDivisionComboBox.setItems(allUSDivisions);
        }
        if(countId == 2 ){
            firstLVDivisionComboBox.setItems(allUkDivisions);

        }
        if(countId == 3 ){
            firstLVDivisionComboBox.setItems(allCaDivisions);

        }

    }
}
