package DAO;

import com.example.dpapp.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;

public class AppointmentDAO {

    /**
     *
     * @return Query that gets all the appointments and joins them with the contacts where the appointment IDs are the same
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID ORDER BY appointments.Appointment_ID";
            //String sql = "SELECT * FROM appointments";
            PreparedStatement s = JDBC.getConnection().prepareStatement(sql);
            ResultSet r = s.executeQuery();
            while (r.next()) {
                int appointmentId = r.getInt("Appointment_ID");
                String title = r.getString("Title");
                String apDescription = r.getString("Description");
                String location = r.getString("Location");
                String type = r.getString("Type");
                LocalDateTime startTime = r.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = r.getTimestamp("End").toLocalDateTime();
                int contactId = r.getInt("Contact_ID");
                String contactName = r.getString("Contact_Name");

                int customerId = r.getInt("Customer_ID");
                int appUserId = r.getInt("User_ID");
                Appointment t = new Appointment(appointmentId, title, apDescription,contactId,type,startTime,endTime, customerId, appUserId, contactName, location);
                allAppointments.add(t);


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allAppointments;
    }

    /**
     *
     * @return SQL query that is placed into  contactReport
     * It gets the requested values and Inner joins them with the contact table where the contactIds are the same
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllContactReport() throws SQLException {
        ObservableList<Appointment> contactReport = FXCollections.observableArrayList();
        try {
            String sql = "Select appointments.Appointment_ID,appointments.Title,appointments.Type,appointments.Description,appointments.Start,appointments.End,appointments.Customer_ID, contacts.Contact_ID,contacts.Contact_Name\n" +
                    "from appointments\n" +
                    "inner JOIN contacts\n" +
                    "ON appointments.Contact_ID = contacts.Contact_ID;\n";
            //String sql = "SELECT * FROM appointments";
            PreparedStatement s = JDBC.getConnection().prepareStatement(sql);
            ResultSet r = s.executeQuery();
            while (r.next()) {
                int appointmentId = r.getInt("Appointment_ID");
                String title = r.getString("Title");
                String type = r.getString("Type");
                String apDescription = r.getString("Description");
                //String location = r.getString("Location");
                LocalDateTime startTime = r.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = r.getTimestamp("End").toLocalDateTime();
                int contactId = r.getInt("Contact_ID");
                String contactName = r.getString("Contact_Name");
                int customerId = r.getInt("Customer_ID");
                //int appUserId = r.getInt("User_ID");
                Appointment t = new Appointment(appointmentId, title, type, apDescription, startTime, endTime, customerId, contactId, contactName);
                contactReport.add(t);


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contactReport;
    }

    /**
     * Takes the month from Start and the Type and counts how many of each app type there is in  each month
     * @return the SQL result
     * @throws SQLException
     */
        public static ObservableList<Appointment> getAppCount () throws SQLException {
            ObservableList<Appointment> appCount = FXCollections.observableArrayList();


            try {

                String sql = "SELECT month(Start) AS Month, Type AS Type, COUNT(*) AS totalApps " +
                        "FROM appointments " +
                        "GROUP BY month(Start), Type " +
                        "ORDER BY month(Start), Type";


                //String sql = "SELECT * FROM appointments";
                PreparedStatement s = JDBC.getConnection().prepareStatement(sql);
                ResultSet r = s.executeQuery();
                while (r.next()) {
                    //int appointmentId = r.getInt("Appointment_ID");
                    //String title = r.getString("Title");
                    String type = r.getString("Type");
                    //String apDescription = r.getString("Description");
                    //String location = r.getString("Location");
                    Month month = Month.of(r.getInt("Month"));
                    //LocalDateTime endTime = r.getTimestamp("End").toLocalDateTime();
                    //int contactId = r.getInt("Contact_ID");
                    //String contactName = r.getString("Contact_Name");
                    //int customerId = r.getInt("Customer_ID");
                    int totalApps = r.getInt("totalApps");
                    Appointment t = new Appointment(type,month,totalApps);
                    appCount.add(t);


                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return appCount;
        }

    /**
     *
     * @param appointmentId app ID
     * @param title app Title
     * @param apDescription app Description
     * @param location app location
     * @param type app type
     * @param startTime app startTime
     * @param endTime app endTime
     * @param customerId customer ID
     * @param appUserId UserId
     * @param contactId contact
     */
    public static void modifyAppointment(int appointmentId, String title, String apDescription, String location, String type, LocalDateTime startTime, LocalDateTime endTime, int customerId, int appUserId, int contactId) {

        try {

            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement modifyAppointment = JDBC.connection.prepareStatement(sql);

            modifyAppointment.setString(1, title);
            modifyAppointment.setString(2, apDescription);
            modifyAppointment.setString(3, location);
            modifyAppointment.setString(4, type);
            modifyAppointment.setTimestamp(5, Timestamp.valueOf(startTime));
            modifyAppointment.setTimestamp(6, Timestamp.valueOf(endTime));
            modifyAppointment.setInt(7, customerId);
            modifyAppointment.setInt(8, appUserId);
            modifyAppointment.setInt(9, contactId);
            modifyAppointment.setInt(10, appointmentId);

            modifyAppointment.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param title title for an appointment object
     * @param apDescription Appointment apDescription for an appointment object
     * @param location location for an appointment object
     * @param type type for an appointment object
     * @param startTime Appointment location for an appointment object
     * @param endTime Endtime for an appointment
     * @param customerId Customer ID tied to this appointment
     * @param appUserId User ID for logins
     * @param contactId  contactId for an appointment object
     * @throws SQLException
     */
    public static void addAppointment(String title, String apDescription, String location, String type, LocalDateTime startTime, LocalDateTime endTime, int customerId, int appUserId, int contactId) throws SQLException {
        try {
        String sql = "INSERT INTO appointments (Title, Description, Location,Type, Start, End, Customer_ID, User_ID,Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement addAppointment = JDBC.connection.prepareStatement(sql);


            addAppointment.setString(1, title);
            addAppointment.setString(2, apDescription);
            addAppointment.setString(3, location);
            addAppointment.setString(4, type);
            addAppointment.setTimestamp(5, Timestamp.valueOf(startTime));
            addAppointment.setTimestamp(6, Timestamp.valueOf(endTime));
            addAppointment.setInt(7, customerId);
            addAppointment.setInt(8, appUserId);
            addAppointment.setInt(9, contactId);
            addAppointment.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets the appointments that have the same Month as start and orders them by appointment id
     * @return
     */
    public static ObservableList<Appointment> getByMonth() {
        ObservableList<Appointment> byMonth = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments INNER JOIN contacts ON appointments.Contact_ID =  contacts.Contact_ID WHERE MONTH(START) = MONTH(NOW()) ORDER BY appointments.Appointment_ID";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();
            while (r.next()) {
                int appointmentId = r.getInt("Appointment_ID");
                String title = r.getString("Title");
                String apDescription = r.getString("Description");
                int contactId = r.getInt("Contact_ID");
                String contactName = r.getString("Contact_Name");
                String type = r.getString("Type");
                LocalDateTime startTime = r.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = r.getTimestamp("End").toLocalDateTime();
                int customerId = r.getInt("Customer_ID");
                int appUserId = r.getInt("User_ID");
                String location = r.getString("Location");
                Appointment week = new Appointment(appointmentId,title,apDescription,contactId, type,startTime, endTime, customerId, appUserId,contactName ,location);
                byMonth.add(week);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return byMonth;
    }

    /**
     * SQL to grab appointments where the START's YearWEEK are the same as the current YearWEEK and sorts them by appointmentID
     * @return
     */
    public static ObservableList<Appointment> getByWeek() {
        ObservableList<Appointment> byWeek = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE YEARWEEK(START) = YEARWEEK(NOW()) ORDER BY appointments.Appointment_ID";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();
            while (r.next()) {
                int appointmentId = r.getInt("Appointment_ID");
                String title = r.getString("Title");
                String apDescription = r.getString("Description");
                int contactId = r.getInt("Contact_ID");
                String type = r.getString("Type");
                LocalDateTime startTime = r.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = r.getTimestamp("End").toLocalDateTime();
                int customerId = r.getInt("Customer_ID");
                int appUserId = r.getInt("User_ID");
                String location = r.getString("Location");
                String contactName = r.getString("Contact_Name");
                Appointment week = new Appointment(appointmentId, title, apDescription, contactId,type, startTime, endTime, customerId, appUserId, location,contactName);
                byWeek.add(week);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return byWeek;
    }

    /** SQL to delete an appointment that has the same appointment id as the given id
     *
     * @param appointmentId
     */
    public static void deleteAppointment(int appointmentId){
        try{
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement deleteApp = JDBC.connection.prepareStatement(sql);
            deleteApp.setInt(1,appointmentId);
            deleteApp.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * checks if an appointment with the given app ID exist
     * @param appointmentId
     * @return
     */
    public static boolean appExists(int appointmentId){
        ObservableList<Appointment> existingApp = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement checkId = JDBC.connection.prepareStatement(sql);
            //ResultSet r = checkId.executeQuery();
            checkId.setInt(1,appointmentId);
            ResultSet r = checkId.executeQuery();

            boolean exists = r.next();

            return exists;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * SQL that pulls every appointment that has a matching userID
     * @param userId
     * @return
     */
    public static ObservableList<Appointment> userAppointment(int userId) {
        ObservableList<Appointment> AllUserAppointment = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Appointments WHERE User_ID ='" + userId + "'";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();
            while (r.next()) {

                int appointmentId = r.getInt("Appointment_ID");
                String title = r.getString("Title");
                String apDescription = r.getString("Description");
                int contactId = r.getInt("Contact_ID");
                //String contactName = r.getString("Contact_Name");
                String type = r.getString("Type");
                LocalDateTime startTime = r.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = r.getTimestamp("End").toLocalDateTime();
                int customerId = r.getInt("Customer_ID");
                int appUserId = r.getInt("User_ID");
                String location = r.getString("Location");
                Appointment result = new Appointment(appointmentId, title, apDescription, contactId, type, startTime, endTime, customerId, appUserId, location);
                AllUserAppointment.add(result);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return AllUserAppointment;
    }

    public static ObservableList<Appointment> userAppointment1(int userId) {
        ObservableList<Appointment> AllUserAppointment = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Appointments WHERE User_ID ='" + userId + "'";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();
            while (r.next()) {

                int appointmentId = r.getInt("Appointment_ID");
                String title = r.getString("Title");
                String apDescription = r.getString("Description");
                int contactId = r.getInt("Contact_ID");
                String contactName = r.getString("Contact_Name");
                String type = r.getString("Type");
                LocalDateTime startTime = r.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = r.getTimestamp("End").toLocalDateTime();
                int customerId = r.getInt("Customer_ID");
                int appUserId = r.getInt("User_ID");
                String location = r.getString("Location");
                Appointment result = new Appointment(appointmentId, title, apDescription, contactId,type, startTime, endTime, customerId, appUserId, location,contactName);
                AllUserAppointment.add(result);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return AllUserAppointment;
    }

    public static  ObservableList <Appointment> contactAppointment(int contactId1){
        ObservableList<Appointment> AllContactAppointment = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM appointments JOIN contact ON appointments.Contact_ID = contacts.Contact_ID WHERE appointments.Contact_ID = '" + contactId1 + "'";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();
            while (r.next()){
                int appointmentId = r.getInt("Appointment_ID");
                String title = r.getString("Title");
                String apDescription = r.getString("Description");
                int contactId = r.getInt("Contact_ID");
                String contactName = r.getString("Contact_Name");
                String type = r.getString("Type");
                LocalDateTime startTime = r.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = r.getTimestamp("End").toLocalDateTime();
                int customerId = r.getInt("Customer_ID");
                int appUserId = r.getInt("User_ID");
                String location = r.getString("Location");
                Appointment result = new Appointment(appointmentId, title, apDescription, contactId,type, startTime, endTime, customerId, appUserId, location,contactName);
                AllContactAppointment.add(result);

            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return AllContactAppointment;
    }






}

