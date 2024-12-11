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
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AppointmentController implements Initializable {

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
    private ComboBox<String> contactCombo;
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


/**
 *Lambda on line 177,181 & 186,
 * Lambda for users, customers, and countries to fill their combo boxes
 */

    /**
     * Populates the comboboxes and the tableview
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


            userIdCombo.setItems(allUsers);
            customerIdCombo.setItems(allCustomers);


            startTimeCombo.setItems(Appointment.getTime());
            endTimeCombo.setItems(Appointment.getTime());
            ObservableList<Contact> contactList = ContactDAO.getAllContacts();
            ObservableList<String> contactNames = FXCollections.observableArrayList();
            ObservableList<LocalTime> apTimes = FXCollections.observableArrayList();
/** Lambda on 202
 * Lambda  for contact Names and contactIds to be added to contactList
 */
            contactList.forEach(contact -> {
                contactNames.add(contact.getContactName());
                ContactDAO.contactIdName.put(contact.getContactName(), contact.getContactId());
            });
            LocalTime fAppointment = LocalTime.MIN.plusHours(8);
            LocalTime lAppointment = LocalTime.MAX.minusHours(1).minusHours(45);

            if (!fAppointment.equals(0) || !lAppointment.equals(0)) {
                while (fAppointment.isBefore(lAppointment)) {
                    apTimes.add(fAppointment);
                    fAppointment = fAppointment.plusMinutes(15);
                }
            }


            contactCombo.setItems(contactNames);
            countryCombo.setItems(allCountries);

            firstLVDivisionComboBox.setItems(allDivisions);
            startDatePicker.valueProperty().addListener((observableValue, oldValueDate, newValueDate) -> endDatePicker.setValue(newValueDate.plusDays(numOfDays)));
            startTimeCombo.valueProperty().addListener((observableValue, oldValueTime, newValueTime) -> endTimeCombo.setValue(newValueTime.plusMinutes(30)));

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    /**
     * Lambda on line 249 to fill contact combo box
     */
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
                contactCombo.setItems(contactNames);


                for (Contact contact : contactList) {
                    if (selectedApp.getContactId() == contact.getContactId()) {
                        fillContacts = contact.getContactName();
                    }
                }
            }

            //auto fill based off of selected app
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
            contactCombo.setValue(fillContacts);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param actionEvent on press, it will populate the combo boxes for First Lv divisions
     */
    public void getDiv(ActionEvent actionEvent) {

        ObservableList<FirstLvDivision> allUSDivisions = FirstLvDivisionDAO.getUsDivIds();

        ObservableList<FirstLvDivision> allUkDivisions = FirstLvDivisionDAO.getUkDivIds();
        ObservableList<FirstLvDivision> allCaDivisions = FirstLvDivisionDAO.getCaDivIds();

        //allUSDivisions
        int countId = countryCombo.getValue().getCountryId();
        if (countId == 1) {
            firstLVDivisionComboBox.setItems(allUSDivisions);
        }
        if (countId == 2) {
            firstLVDivisionComboBox.setItems(allUkDivisions);

        }
        if (countId == 3) {
            firstLVDivisionComboBox.setItems(allCaDivisions);

        }

       int selectedCountry = -1;


    }

    /**
     *
     * @param event on press adds the user's data for each combo bo, text field, text area to the arrayList for customers
     *              As long as all the condition are meet it will add the customer data
     * @throws IOException
     * @throws SQLException
     */
    public void addCustomer(ActionEvent event) throws IOException, SQLException {

        try {
            Connection conn = JDBC.getConnection();


            String title = titleTxtField.getText();
            String apDescription = descriptionTxtArea.getText();
            String type = typeTxtField.getText();
            String contact = contactCombo.getValue();
            String location = locationTxtField.getText();
            String selectedContactName = contact;
            if (!title.isEmpty() && !apDescription.isEmpty() && !location.isEmpty() && !type.isEmpty() && startDatePicker.getValue() != null && endDatePicker.getValue() != null) {


                ObservableList<Customer> allCustomers = CustomerDAO.allCustomers(conn);
                ObservableList<Integer> holdCustomerId = FXCollections.observableArrayList();
                ObservableList<User> getAllUsers = UserDAO.getAllUsers();
                ObservableList<Integer> holdUserId = FXCollections.observableArrayList();

                allCustomers.stream().map(Customer::getCustomerId).forEach(holdCustomerId::add);
                getAllUsers.stream().map(User::getUserId).forEach(holdUserId::add);


            }

            if (contact == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("You must select a Contact for this appointment"));
                alert.setTitle("No contact selected");
                alert.showAndWait();
                return;
            }
            //Pushes day of the week to the next week day
            //int contactId = contact.getContactId();
            startDatePicker.valueProperty().addListener((observableValue, oldValue, newValue) -> {
                if (newValue != null && (newValue.getDayOfWeek() == DayOfWeek.SATURDAY || newValue.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                    LocalDate nextWeekday = fNextWeekday(newValue);
                    startDatePicker.setValue(nextWeekday);
                }
            });

            DateTimeFormatter mh = DateTimeFormatter.ofPattern("HH:mm");

            LocalDate sPicker = startDatePicker.getValue();

            if (sPicker == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("You must select a start date for this appointment and/or select a weekday"));
                alert.setTitle("No start date selected");
                alert.showAndWait();
                return;
            }

            if (startTimeCombo.getValue().isBefore(LocalTime.of(8, 0)) || startTimeCombo.getValue().isAfter(LocalTime.of(22, 0)) || endTimeCombo.getValue().isBefore(LocalTime.of(8, 0)) || endTimeCombo.getValue().isAfter(LocalTime.of(22, 0))) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("You must select a start time for this appointment that is during business hours"));
                alert.setTitle("Outside of operating hours");
                alert.showAndWait();
                return;

            }

            int nCustomerId = Integer.parseInt(String.valueOf(customerIdCombo.getValue()));
            int appointmentId1;




            LocalTime start = startTimeCombo.getValue();
            if (start == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("You must select a start time for this appointment"));
                alert.setTitle("No start time selected");
                alert.showAndWait();
                return;
            }
            LocalDateTime appStart = LocalDateTime.of(startDatePicker.getValue(), startTimeCombo.getValue());

            LocalDate ePicker = endDatePicker.getValue();
            if (ePicker == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("You must select a end date for this appointment"));
                alert.setTitle("No end date selected");
                alert.showAndWait();
                return;
            }
            LocalTime end = endTimeCombo.getValue();
            if (end == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("You must select a end time for this appointment"));
                alert.setTitle("No end time selected");
                alert.showAndWait();
                return;
            }
            LocalDateTime appEnd = LocalDateTime.of(endDatePicker.getValue(), endTimeCombo.getValue());


            String country = String.valueOf(countryCombo.getValue());
            if (country == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("You must select a country to create this appointment"));
                alert.setTitle("No country selected");
                alert.showAndWait();
                return;
            }
            int appUserId = Integer.parseInt(String.valueOf(userIdCombo.getValue()));

            int contactId = ContactDAO.contactIdName.getOrDefault(selectedContactName, 0);

            DayOfWeek appSDayCheck = startDatePicker.getValue().getDayOfWeek();
            DayOfWeek appEDayCheck = endDatePicker.getValue().getDayOfWeek();
            if (title.isBlank() || title.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("You must write a title to create this appointment"));
                alert.setTitle("No title");
                alert.showAndWait();
                return;
            } else if (apDescription.isBlank() || apDescription.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("You must write a description to create this appointment"));
                alert.setTitle("No description was given");
                alert.showAndWait();
                return;

            } else if (location.isBlank() || location.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("You must give a location to create this appointment"));
                alert.setTitle("No location was given");
                alert.showAndWait();
                return;

            } else if (Appointment.OperatingHours(appStart, appEnd)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("You must set a Start and End time that are within operating hours  to create this appointment"));
                alert.setTitle("No description was given");
                alert.showAndWait();
                return;

            } else if (Appointment.timeCheck(nCustomerId, appStart, appEnd)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("You must schedule an appointment that does not overlap with another appointment  to create this appointment"));
                alert.setTitle("Scheduling Conflict");
                alert.showAndWait();
                return;


            } else if (Appointment.WorkWeek(appSDayCheck, appEDayCheck)) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("You must schedule an appointment during the work week"));
                alert.setTitle("Scheduled outside of Work week");
                alert.showAndWait();
                return;

            } else {
                AppointmentDAO.addAppointment(title, apDescription, location, type, appStart, appEnd, nCustomerId, appUserId, contactId);
                Alert create = new Alert(Alert.AlertType.CONFIRMATION);
                create.setTitle("Appointment Created");
                create.setContentText("You're " + type + " on " + appSDayCheck + " " + sPicker + " from " + startTimeCombo.getValue() + "-" + endTimeCombo.getValue() + " has been scheduled");
                //create.getButtonTypes().addAll(ButtonType.YES);
                create.showAndWait();
                AllAppointments = AppointmentDAO.getAllAppointments();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        appointmentTableView.setItems(AllAppointments);
        appointmentTableView.refresh();
    }

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
     * @param event Once the update button is pressed if an item is selected it will send that values from the comboboexs
     *              , text fields over to the appointment modify page/app update page.
     */
    public void updateCustomer(ActionEvent event) {

        if (appointmentTableView.getSelectionModel() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/view/appointmentModify.fxml"));
            try {
                loader.load();
                AppointmentModifyController ModController = loader.getController();
                ModController.sendAppointment(appointmentTableView.getSelectionModel().getSelectedItem());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = (Parent) loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing selected");
            alert.setContentText("You must select a Scheduled appointment to update");
            alert.showAndWait();
            return;

        }
    }

    /**
     *
     * @param event On press will delete the selected appointment, or throw an alert if nothing is selected
     */
    public void deleteBtn(ActionEvent event) {
        Appointment selectedApp = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedApp == null) {
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
            if (confirm.getResult() == ButtonType.OK) {
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
            } else if (confirm.getResult() == ButtonType.CANCEL) {
                confirm.close();
            }

        }

    }

    /**
     *
     * @param event when toggled it will show only the appointments for the current month
     */
    public void monthApps(ActionEvent event) {

        appointmentTableView.setItems(AppointmentDAO.getByMonth());
    }

    /**
     *
     * @param event when toggled it will show only the appointments for the current week
     */
    public void weekApps(ActionEvent event) {

        appointmentTableView.setItems(AppointmentDAO.getByWeek());
        appointmentTableView.setPlaceholder(new Label("Nothing is scheduled for this week"));
    }

    /**
     *
     * @param event default will show all appointments or a label stating that there are no appointments this month
     */
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
     * @param event Once the "Add customer" button is pressed it will send the user to customer page
     *              Here the user can use the text fields and combo boxes to add a new customer
     * @throws IOException
     */
    public void customerPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/customer.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     *
     * @param event On press sends the user to the Reports' page where tableviews display the requested data
     */
    public void reportPage(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/view/report.fxml"));
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
