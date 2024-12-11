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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    @FXML
    private TextField customerNameTxt;
    @FXML
    private TextField customerIDTxt;
    @FXML
    private TextField addressTxt;
    @FXML
    private TextField postalCodeTxt;
    @FXML
    private TextField phoneTxt;
    @FXML
    private Button addBtn2;
    @FXML
    private Button updateCustomerBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<Country> countryCombo;
    @FXML
    private ComboBox<FirstLvDivision> firstLVDivisionCombo;
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> postalCodeColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, String> countryColumn;
    @FXML
    private TableColumn<Customer, String> firstLvDivisionColumn;
    @FXML
    private TableColumn<Customer, Integer> divisionIdColumn;


    ObservableList<Customer>CusList = FXCollections.observableArrayList();

    /**
     * Fills the combo boxes as well as the tableView
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Customer> customersList = CustomerDAO.getAllCus1();
            if (customersList != null && !customersList.isEmpty()) {
                customerTableView.setItems(customersList);
                customerTableView.refresh();
                customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
                customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
                addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
                postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
                phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
                divisionIdColumn.setCellValueFactory(new PropertyValueFactory<>("cusDivisionId"));
                firstLvDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("cusCountryName"));

                countryColumn.setCellValueFactory(new PropertyValueFactory<>("cusDivisionName"));

            } else {
                customerTableView.setPlaceholder(new Label("Nothing is scheduled for this week"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<Country> allCountries = CountryDAO.getAllCountries();
        ObservableList<String> countryList = FXCollections.observableArrayList();
        ObservableList<FirstLvDivision> allDivisions = FirstLvDivisionDAO.getAllDivIds();

/**Lambda on line 110
 * Lambda to fill country combo box, and division combo box
 */
        allCountries.forEach(country -> countryList.add(country.getCountryName()));
        countryCombo.setItems(allCountries);
        firstLVDivisionCombo.setItems(allDivisions);

    }


    /**
     * @param actionEvent Fills the Division combobox based on of the country Id the user selects
     *                    1 for Us, 2 for the UK, and 3 Canada
     *                    You have to Select a country from the dropdown then press,
     *                    "Set Country"
     */
    public void getDiv(ActionEvent actionEvent) {

        ObservableList<FirstLvDivision> allUSDivisions = FirstLvDivisionDAO.getUsDivIds();

        ObservableList<FirstLvDivision> allUkDivisions = FirstLvDivisionDAO.getUkDivIds();
        ObservableList<FirstLvDivision> allCaDivisions = FirstLvDivisionDAO.getCaDivIds();

        //allUSDivisions
        int countId = countryCombo.getValue().getCountryId();
        if (countId == 1) {
            firstLVDivisionCombo.setItems(allUSDivisions);
        }
        if (countId == 2) {
            firstLVDivisionCombo.setItems(allUkDivisions);

        }
        if (countId == 3) {
            firstLVDivisionCombo.setItems(allCaDivisions);

        }
    }

    /**
     *
     * @param event On press takes values from the text-fields and sends it to the
     *              Customer list
     *
     * @throws SQLException
     */
    public void addCustomer(ActionEvent event) throws SQLException {
        LocalTime currentT = LocalTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mm a");
        String ft = currentT.format(df);

        try {
            Connection conn = JDBC.getConnection();


            String cusName = customerNameTxt.getText();
            String address = addressTxt.getText();
            String postalCode = postalCodeTxt.getText();
            String phone = phoneTxt.getText();
            FirstLvDivision  divisionCombo = firstLVDivisionCombo.getValue();
            int divisionId = divisionCombo.getDivisionId();
            LocalDateTime createDate = LocalDateTime.now();
            LocalDateTime lastUpdated = LocalDateTime.now();
            Country country = countryCombo.getValue();
            if (cusName.isEmpty() || cusName.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("Please enter a customer name"));
                alert.setTitle("Enter a customer name");
                alert.showAndWait();
                return;
            }else if(address.isEmpty() || address.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("Please enter an address"));
                alert.setTitle("Enter a customer name");
                alert.showAndWait();
                return;}
            else if(postalCode.isEmpty() || postalCode.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("Please enter a postal code"));
                alert.setTitle("Enter a customer name");
                alert.showAndWait();
                return;}
            else if(phone.isEmpty() || phone.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("Please enter a phone number"));
                alert.setTitle("Enter a customer name");
                alert.showAndWait();
                return;}
            else if(divisionCombo == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("Please select a division"));
                alert.setTitle("Enter a customer name");
                alert.showAndWait();
                return;}
            else if(country == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ("Please select a country"));
                alert.setTitle("Enter a customer name");
                alert.showAndWait();
                return;}
            else {CustomerDAO.addCus(cusName,address,phone,postalCode,createDate,lastUpdated,divisionId);
                //customerTableView.refresh();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, (cusName+" was added at "+ ft ));
                alert.setTitle("Create Time");
                alert.showAndWait();
                CusList = CustomerDAO.getAllCustomers();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        customerTableView.setItems(CusList);

    }

    /**
     *
     * @param event On Press will delete the selected customer and all apps tied to that customer
     *              On press it will loop through every app and when it finds
     *              one that has the same customerID as the selected  customer value
     *              It adds them to a list then removes all those appointments that have the same
     *              customer id until it gets to zero, once at zero the customer will be deleted
     * @throws SQLException
     */
    public void deleteBtn(ActionEvent event) throws SQLException {
        int appNum = 0;
        int selectedCus = customerTableView.getSelectionModel().getSelectedItem().getCustomerId();
        Customer cus = customerTableView.getSelectionModel().getSelectedItem();



        if (cus ==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing selected");
            alert.setContentText("You must select a customer to remove");
            alert.showAndWait();
            return;
        }
        try {
            ObservableList<Appointment> appList = AppointmentDAO.getAllAppointments();

            for (Appointment app : appList) {
                int appCusId = app.getCustomerId();
                if (appCusId == selectedCus) {
                    appNum++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(appNum > 0){
            ObservableList<Appointment> appList = AppointmentDAO.getAllAppointments();

            Alert confirm = new Alert(Alert.AlertType.WARNING);
            confirm.setTitle("Delete Customer and " +appNum+ "appointments" );
            confirm.setContentText("Please confirm that you want to remove the selected customer, this will remove all " + appNum+  " of their appointments ?");
            confirm.getButtonTypes().clear();
            confirm.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK){

                    for(Appointment app : appList){
                        if(app.getCustomerId() == selectedCus)
                            AppointmentDAO.deleteAppointment(app.getAppointmentId());
                        }
                        CustomerDAO.deleteCus(customerTableView.getSelectionModel().getSelectedItem().getCustomerId());
                        Alert delete = new Alert(Alert.AlertType.CONFIRMATION);
                        delete.setTitle("Delete Customer");
                        delete.setContentText("Customer ID# " + customerTableView.getSelectionModel().getSelectedItem().getCustomerId() + " for " + customerTableView.getSelectionModel().getSelectedItem().getCustomerName() + " was removed."+ appNum+" Appointments deleted");
                        delete.getButtonTypes().clear();
                        delete.getButtonTypes().addAll(ButtonType.OK);
                        delete.showAndWait();





                //customerTableView.setItems(CusList);
                //customerTableView.refresh();

            } else if (confirm.getResult() == ButtonType.CANCEL){


                confirm.close();

            }

        }
        //customerTableView.setItems(CusList);

        if(appNum == 0){

            Alert confirm = new Alert(Alert.AlertType.WARNING);
            confirm.setTitle("Delete Customer");
            confirm.setContentText("Please confirm that you want to remove the selected customer?");
            confirm.getButtonTypes().clear();
            confirm.getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK){
                CustomerDAO.deleteCus(customerTableView.getSelectionModel().getSelectedItem().getCustomerId());
                Alert delete = new Alert(Alert.AlertType.CONFIRMATION);
                delete.setTitle("Delete Customer");
                delete.setContentText("Customer ID# " + customerTableView.getSelectionModel().getSelectedItem().getCustomerId() + " for " + customerTableView.getSelectionModel().getSelectedItem().getCustomerName() + " was removed.");
                delete.getButtonTypes().clear();
                delete.getButtonTypes().addAll(ButtonType.OK);
                delete.showAndWait();


            }

        }
        try {
            CusList = CustomerDAO.getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        customerTableView.setItems(CusList);
        customerTableView.refresh();
    }

    /**
     *
     * @param event On press sends the user to the customer modify page
     *              The user will get an alert if they don't have a customer selected if they do
     *              The values in the fields will be sent over to the customer modify page
     * @throws IOException
     * @throws SQLException
     */
    public void updateCustomer(ActionEvent event) throws IOException, SQLException{
        if(customerTableView.getSelectionModel().getSelectedItem() != null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/view/customerModify.fxml"));
            try {
                loader.load();
                CustomerModifyController ModCus = loader.getController();
                ModCus.sendCus(customerTableView.getSelectionModel().getSelectedItem());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene =  loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }   else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing selected");
            alert.setContentText("You must select a customer to update");
            alert.showAndWait();
            return;

        }

        }


    /**
     *
     * @param event Once cancel is pressed sends alerts the user stating that any updates that haven't been saved will be lost
     *              Then takes them back to the main appointment page if they press ok or stays on the current form if they press cancel
     */
    public void cancel(ActionEvent event) {

        Alert confirm = new Alert(Alert.AlertType.WARNING);
        confirm.setTitle("Changes Saved?");
        confirm.setContentText("Please confirm that you have Saved all new customers");
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
}
