package model;

import DAO.AppointmentDAO;
import controller.AppointmentController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Initial attributes of appointment set to private so that each instance of appointment can exist and not just the mose recent instance
 */
public class Appointment {

    private int appointmentId;
    private String title;
    private String apDescription;
    private int contactId;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int customerId;
    private int appUserId;
    private String location;
    private String contactName;
    private Month month;
    private int  totalApps;


    /**
     *
     * @param appointmentId App ID
     * @param title App title
     * @param apDescription App Description
     * @param contactId App contact ID
     * @param type App type
     * @param startTime App start time
     * @param endTime App end time
     * @param customerId App customer ID
     * @param appUserId App user ID
     * @param location App location
     */
    public Appointment(int appointmentId, String title, String apDescription, int contactId, String type, LocalDateTime startTime, LocalDateTime endTime, int customerId, int appUserId, String location) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.apDescription = apDescription;
        this.contactId = contactId;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
        this.appUserId = appUserId;
        this.location = location;


        //this.contactName = contactName;


    }


    /**
     *
     * @param type Holds the Type of appointment for the reports page
     * @param month holds the Month for the reports page
     * @param totalApps Holds the count for total types of appointments  for a corresponding month
     */
    public Appointment(String type,Month month,int totalApps) {
        this.type = type;
        this.month = month;
        this.totalApps = totalApps;

    }

    /**
     *
     * @param appointmentId Appointment Id for an appointment object
     * @param title Appointment title for an appointment object
     * @param apDescription Appointment apDescription for an appointment object
     * @param contactId Appointment contactId for an appointment object
     * @param type Appointment type for an appointment object
     * @param startTime Appointment startTime for an appointment object
     * @param endTime Appointment endTime for an appointment object
     * @param customerId Appointment customerId for an appointment object
     * @param appUserId Appointment appUserId for an appointment object
     * @param contactName Appointment contactName for an appointment object
     * @param location Appointment location for an appointment object
     */
    public Appointment(int appointmentId, String title, String apDescription, int contactId, String type, LocalDateTime startTime, LocalDateTime endTime, int customerId, int appUserId, String contactName ,String location) {

        this.appointmentId = appointmentId;
        this.title = title;
        this.apDescription = apDescription;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.contactId = contactId;
        this.customerId = customerId;
        this.appUserId = appUserId;
        this.contactName = contactName;




    }

    /**
     *
     * @param appointmentId App ID
     * @param title App title
     * @param type App type
     * @param apDescription App description
     * @param startTime App start time
     * @param endTime App end time
     * @param customerId App customer ID
     * @param contactId App contact ID
     * @param contactName App contact Name
     */
    public Appointment(int appointmentId, String title, String type, String apDescription, LocalDateTime startTime, LocalDateTime endTime, int customerId, int contactId, String contactName) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.type = type;
        this.apDescription = apDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    public Appointment(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getApDescription() {
        return apDescription;
    }

    public void setApDescription(String apDescription) {
        this.apDescription = apDescription;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @param customerId takes the customer id and checks based on the filtered list if it is not the same as the app customer ID if this is
     *If the customerIds are not the same skips the rest of the code in the loop and goes to the next id until there is a match
     * @param appointmentId Gets the appointmentId of the app to be updated, that appointmentId is then filtered from the list
     * @param startTime Used to hold the appointments start time
     * @param endTime Used to hold the appointments end time
     * @return
     */
    public static boolean updateTimeCheck(int customerId,int appointmentId, LocalDateTime startTime, LocalDateTime endTime) {
    try {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

        LocalDateTime appStart;
        LocalDateTime appEnd;
        int appIdUpdate;
        for (Appointment app : allAppointments) {
            if (appointmentId != app.getAppointmentId()) {
                filteredAppointments.add(app);
            }
        }

        for (Appointment app : filteredAppointments) {
            appStart = app.getStartTime();
            appEnd = app.getEndTime();


            if (customerId != app.getCustomerId()) {
                continue;
            } else if (appStart.isEqual(startTime) || appEnd.isEqual(endTime)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Scheduling Conflict");
                alert.setContentText("Appointments cannot be scheduled during a concurrent appointment, Please select a different appointment window ");
                alert.showAndWait();
                return true;

            } else if (startTime.isAfter(appStart) && startTime.isBefore(appEnd)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Scheduling Conflict");
                alert.setContentText("Appointments cannot be scheduled during a concurrent appointment, Please select a different start time for this appointment");
                alert.showAndWait();
                return true;
            } else if (endTime.isAfter(appStart) && endTime.isBefore(appEnd)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Scheduling Conflict");
                alert.setContentText("Appointments cannot conclude during a concurrent appointment, Please select a different end time for this appointment");
                alert.showAndWait();
                return true;
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;

}


    /**
     * Iterates through each app until it finds a matching customer ID then once it does it will run checks to insure there are no overlapping appointments
     * @param customerId app customer ID
     * @param startTime app start time
     * @param endTime app end time
     * @return
     */
    public static boolean timeCheck(int customerId, LocalDateTime startTime, LocalDateTime endTime) {

        try {
            ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();

            LocalDateTime appStart;
            LocalDateTime appEnd;
            int appID;
            for (Appointment app : allAppointments) {
                appID = app.getAppointmentId();
                appStart = app.getStartTime();
                appEnd = app.getEndTime();


                if (customerId != app.getCustomerId()) {
                    continue;
                } else if (appStart.isEqual(startTime) || appEnd.isEqual(endTime)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Scheduling Conflict");
                    alert.setContentText("Appointments cannot be scheduled during a concurrent appointment, Please select a different appointment window ");
                    alert.showAndWait();
                    return true;

                } else if (startTime.isAfter(appStart) && startTime.isBefore(appEnd)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Scheduling Conflict");
                    alert.setContentText("Appointments cannot be scheduled during a concurrent appointment, Please select a different start time for this appointment");
                    alert.showAndWait();
                    return true;
                } else if (endTime.isAfter(appStart) && endTime.isBefore(appEnd)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Scheduling Conflict");
                    alert.setContentText("Appointments cannot conclude during a concurrent appointment, Please select a different end time for this appointment");
                    alert.showAndWait();
                    return true;
                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }return false;

    }


    /**
     * Sets the start time for operating hours
     * @return the start time for the business
     */
    public static LocalTime LStart(){
        LocalTime businessOpen = LocalTime.of(8,0);
        ZoneId eZone = ZoneId.of("America/New_York");
        ZoneId localTimeZ = ZoneId.systemDefault();

        LocalDateTime bhEast = LocalDateTime.of(LocalDate.now(),businessOpen);
        LocalDateTime bLocal = bhEast.atZone(eZone).withZoneSameInstant(localTimeZ).toLocalDateTime();
        LocalTime bStartLocal = bLocal.toLocalTime();

        return bStartLocal;


    }

    /**
     * Sets the end time for operating hours
     * @return the end time for the business
     */
    public static LocalTime LEnd(){
        LocalTime businessClose = LocalTime.of(22,0);
        ZoneId eZone = ZoneId.of("America/New_York");
        ZoneId localTimeZ = ZoneId.systemDefault();

        LocalDateTime bhEast = LocalDateTime.of(LocalDate.now(),businessClose);
        LocalDateTime bLocal = bhEast.atZone(eZone).withZoneSameInstant(localTimeZ).toLocalDateTime();
        LocalTime bEndLocal = bLocal.toLocalTime();

        return bEndLocal;


    }

    /**
     * Used to set time for the start and end time
     * @return start with 30 minutes added on to it
     */
    public static ObservableList<LocalTime> getTime(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mm a");

        ObservableList<LocalTime> appTimeList = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(8,00);
        LocalTime end = LocalTime.MIDNIGHT.minusHours(1);

        while(start.isBefore(end.plusSeconds(2))){

            appTimeList.add(start);
            start = start.plusMinutes(30);
        }
        return appTimeList;
    }


    /**
     *
     * @param startTime Holds the LocalDateTime for the start of the work day
     * @param endTime Holds the LocalDateTime for the start of the work day
     * @return if true then an alert will pop telling the user to rescheduled the appointment within business hours
     * if false it will allow them to create the appointment.
     */
    public static boolean OperatingHours(LocalDateTime startTime, LocalDateTime endTime){
        ZoneId localTime = ZoneId.systemDefault();
        ZoneId EST = ZoneId.of("America/New_York");



        LocalDateTime appSCheck = startTime.atZone(localTime).withZoneSameInstant(EST).toLocalDateTime();
        LocalDateTime appECheck = endTime.atZone(localTime).withZoneSameInstant(EST).toLocalDateTime();
        LocalDateTime OpHoursS = appSCheck.withHour(8).withMinute(0);
        LocalDateTime OpHoursE= appECheck.withHour(22).withMinute(0);



        if(appSCheck.isBefore(OpHoursS) || appECheck.isAfter(OpHoursE)){
            LocalTime LStart = Appointment.LStart();
            LocalTime LEnd = Appointment.LEnd();
            Alert alert = new Alert(Alert.AlertType.ERROR, String.format("You must schedule appointments inside of business hours" + "Select  between the hours between " + LStart.format(DateTimeFormatter.ofPattern((DateTimeFormatter.ofPattern("hh:mm")) + " - " + LEnd.format(DateTimeFormatter.ofPattern("hh:mm")) + "Local time")), new ButtonType[0]));
            alert.setTitle("Scheduled outside of Operating hours");
            alert.showAndWait();
            return true;
        }
        else{
            return false;
        }



    }

    /**
     *
     * @param appSDayCheck used to check app is set during the work week
     * @param appEDayCheck used to check app is set during the work week
     * @return error if true false passes
     */
    public static boolean WorkWeek(DayOfWeek appSDayCheck, DayOfWeek appEDayCheck){





        int appSDayCheckInt = appSDayCheck.getValue();
        int appEDayCheckInt = appEDayCheck.getValue();


        int startOfWorkWeek = DayOfWeek.MONDAY.getValue();
        int endOfWorkWeek = DayOfWeek.FRIDAY.getValue();

        if(appSDayCheckInt < startOfWorkWeek || appSDayCheckInt > endOfWorkWeek || appEDayCheckInt < startOfWorkWeek || appEDayCheckInt > endOfWorkWeek){
            Alert alert = new Alert(Alert.AlertType.ERROR, String.format("You must schedule appointments during the work week" + "Select a day between Monday  " + appSDayCheck + " is outside of the work week" + appEDayCheck, new ButtonType[0]));
            alert.setTitle("Scheduled outside of Operating hours");
            alert.showAndWait();
            return true;
        }

        else{
            return false;
        }

    }


    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public int getTotalApps() {
        return totalApps;
    }

    public void setTotalApps(int totalApps) {
        this.totalApps = totalApps;
    }
}

