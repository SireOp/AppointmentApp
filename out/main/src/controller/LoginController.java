package controller;

import java.io.FileWriter;

import DAO.AppointmentDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import static DAO.UserDAO.*;


public class LoginController implements Initializable {
    @FXML
    private Button loginBtn;

    @FXML
    private Label userNameLabel, passwordLabel, locationLabel;

    @FXML
    private TextField userNameTxtField;

    @FXML
    private PasswordField passwordTxtField;
    boolean logCheck = false;

    public LoginController() {

    }

    ResourceBundle rb = ResourceBundle.getBundle("ult/Nat");

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime now15 = LocalDateTime.now().plusMinutes(15);
    ZonedDateTime LocalDToZone = now.atZone(ZoneId.systemDefault());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Current Working Directory: " + currentDirectory);
        String location = ZoneId.systemDefault().toString();




        locationLabel.setText(location);
        userNameLabel.setText(rb.getString("UserName"));
        passwordLabel.setText(rb.getString("Password"));
        loginBtn.setText(rb.getString("Login"));




    }

    /**
     *
     * @param event Runs the checks for login that checks the inputted username and pass against those in the database
     *              Checks for nulls
     *
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    public void onActionLogin(ActionEvent event) throws SQLException, IOException {


        String UserName = userNameTxtField.getText();
        String Pass = passwordTxtField.getText();

        try {




            if (UserName.isEmpty() || UserName.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("PleaseEnterUserName"), new ButtonType[0]);
                alert.setTitle(rb.getString("UserError"));
                alert.showAndWait();
                logActivity();
                logCheck = false;

            } else if (!checkPass(Pass) && !checkUser(UserName)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("PassAndUserName"), new ButtonType[0]);
                alert.setTitle(rb.getString("PassUserError"));
                alert.showAndWait();
                logActivity();
                logCheck = false;

            } else if (Pass.isEmpty() || Pass.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("PleaseEnterValidPassword"), new ButtonType[0]);
                alert.setTitle(rb.getString("PasswordError"));
                alert.showAndWait();

            } else if (!checkUser(UserName)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("PleaseEnterUserName"), new ButtonType[0]);
                alert.setTitle(rb.getString("UserError"));
                alert.showAndWait();
                logActivity();
                logCheck = false;

            } else if (!checkPass(Pass)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, rb.getString("PleaseEnterValidPassword"), new ButtonType[0]);
                alert.setTitle(rb.getString("PasswordError"));
                alert.showAndWait();
                logActivity();
                logCheck = false;

            }
            else if (userCred(UserName, Pass)) {
                int userId = getId(UserName);
                ObservableList<Appointment> uAppointment = AppointmentDAO.userAppointment(userId);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, rb.getString("LoginGood"), new ButtonType[0]);
                alert.setTitle(rb.getString("LoginGood"));
                alert.showAndWait();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/view/appointments.fxml"));
                loader.load();
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = (Parent) loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
                logCheck = true;

                logActivity();


                boolean appCheck = false;
                for (Appointment appointment : uAppointment) {
                    LocalDateTime startTime = appointment.getStartTime();
                    long  timeDiff = Math.abs(ChronoUnit.MINUTES.between(startTime, now));
                   int alertApId = appointment.getAppointmentId();

                    if ((startTime.isAfter(now) || startTime.isEqual(now15)) && (startTime.isBefore(now15) || startTime.isEqual(now))) {
                        Alert delete = new Alert(Alert.AlertType.WARNING);
                        delete.setTitle("Scheduling Conflict" + " Appointment ID"+"["+ appointment.getAppointmentId()+"]" + " is in" +timeDiff+" minutes" );
                        String contextText = rb.getString("SchedulingConflict15");
                        //MessageFormat formatter = new MessageFormat("");
                        //formatter.setLocale(ti);
                        String formattedMessage = MessageFormat.format(contextText,alertApId,timeDiff);
                        delete.setContentText(formattedMessage);

                        delete.getButtonTypes().clear();
                        delete.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                        delete.showAndWait();
                        System.out.println("["+ appointment.getAppointmentId()+"]" + " is in" +timeDiff );
                        appCheck = true;


                    }
                }
                if (!appCheck) {
                    Alert confirmation = new Alert(Alert.AlertType.INFORMATION, rb.getString("LoginGood15"), new ButtonType[0]);
                    confirmation.setTitle(rb.getString("LoginGood"));
                    confirmation.showAndWait();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }


    interface Logs {

         String getFile();
    }


    /** Lambda
     * Sets login_Activity.txt to write to the Root directory and
     */
    Logs logActivity = () -> {
    return  "C:\\Users\\SirOp\\IdeaProjects\\DpApp/login_activity.txt";};

    /**
     * Writes the login_activity file if login is successful or not.
     * DateTimeFormatter and string will write to the file as "admin has logged on at Sun,05-05-2024 05:20:04 PM"
     * with username  if login is good or failed then the day followed by Month, date, year and time
     * @throws IOException
     */
    public void logActivity() throws IOException {
        try {

            FileWriter fileWriter = new FileWriter(logActivity.getFile(), true);
            DateTimeFormatter dF = DateTimeFormatter.ofPattern("E,MM-dd-yyyy hh:mm:ss a");
            if (logCheck) {
                fileWriter.write(userNameTxtField.getText() + " has logged on at " + dF.format(now));
            } else if (!logCheck){
                fileWriter.write(userNameTxtField.getText() + " failed to login at " + dF.format(now));
            }
            fileWriter.write("\n");
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

