package DAO;

import com.example.dpapp.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ContactDAO {
    /**
     *
     * @return Query that gets everything in the contacts table
     * @throws SQLException
     */
    public static ObservableList<Contact> getAllContacts()throws SQLException{
        ObservableList <Contact> contactList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM contacts";
            PreparedStatement s = JDBC.getConnection().prepareStatement(sql);
            ResultSet r = s.executeQuery();

            while(r.next()){
                int contactId = r.getInt("Contact_ID");
                String contactName = r.getString("Contact_Name");
                String contactEmail = r.getString("Email");
                Contact c = new Contact(contactId, contactName, contactEmail);
                contactList.add(c);

            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }return contactList;
    }

    public static  Map<String, Integer> contactIdName = new HashMap<>();

    public static String fContactID(String contactId) throws SQLException{
        String sql = "SELECT *  FROM contacts WHERE Contact_Name = ?";
        PreparedStatement s = JDBC.getConnection().prepareStatement(sql);

        s.setString(1, contactId);
        ResultSet r = s.executeQuery();

        while(r.next()){
            contactId = r.getString("Contact_ID");

        }
        return contactId;
    }

    /**
     *
     * @param contactId
     * @return Contact IDs that match the inputed value
     */
    public static Contact sendContact(int contactId){
        try {
            String sql = "SELECT *  FROM contacts WHERE Contact_ID = ?";
            PreparedStatement s = JDBC.getConnection().prepareStatement(sql);

            s.setInt(1, contactId);
            s.execute();

            ResultSet r = s.getResultSet();


             r.next();
            int fContactId = r.getInt("Contact_ID");
            String contactName = r.getString("Contact_Name");
            String contactEmail = r.getString("Email");
            Contact c = new Contact(fContactId, contactName, contactEmail);
            return c;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}


