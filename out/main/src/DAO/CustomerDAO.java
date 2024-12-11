package DAO;

import com.example.dpapp.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import model.Customer;
import model.User;

import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDateTime;

public class CustomerDAO {
    /**
     *
     * @param connection
     * @return Query that grabs customers
     * @throws SQLException
     */
    public static ObservableList<Customer> allCustomers(Connection connection) throws SQLException {
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID,customers.Created_By ,customers.Last_Updated_By,first_level_divisions.Division, first_level_divisions.Country_ID, countries.Country FROM customers JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID JOIN countries ON countries.Country_ID = first_level_divisions.Country_ID ORDER BY customers.Customer_ID ";

        PreparedStatement s = JDBC.connection.prepareStatement(sql);
        ResultSet r = s.executeQuery();

        ObservableList<Customer> customersList = FXCollections.observableArrayList();

        while (r.next()) {
            int customerID = r.getInt("Customer_ID");
            String customerName = r.getString("Customer_Name");
            String address = r.getString("Address");
            String postalCode = r.getString("Postal_Code");
            String phone = r.getString("Phone");
            String createdBy =r.getString("Created_By");
            String lastUpdatedBy = r.getString("Last_Updated_By");
            int cDivisionId = r.getInt("Division_ID");
            String cDivisionName = r.getString("Division");
            int cCountryId = r.getInt("Country_ID");
            String cCountryName = r.getString("Country");

            Customer customer = new Customer(customerName,address, postalCode, phone, createdBy ,lastUpdatedBy,cDivisionId, cDivisionName, cCountryId,cCountryName,customerID );
            customersList.add(customer);
        }
        return customersList;
    }

    public static void deleteCus(int customerId){
        try{String sql = "DELETE FROM customers WHERE Customer_ID = ?";
           PreparedStatement s = JDBC.connection.prepareStatement(sql);
           s.setInt(1,customerId);
           s.execute();
        }catch (SQLException e){e.printStackTrace();}
    }

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> customersList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Create_Date, customers.Last_Update, customers.Created_By, customers.Last_Updated_By, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division, first_level_divisions.Country_ID, countries.Country FROM customers JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID JOIN countries ON countries.Country_ID = first_level_divisions.Country_ID ORDER BY customers.Customer_ID ";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();

            while (r.next()) {
                int customerID = r.getInt("Customer_ID");
                String customerName = r.getString("Customer_Name");
                String address = r.getString("Address");
                String postalCode = r.getString("Postal_Code");
                String phone = r.getString("Phone");
                String createdBy =  r.getString("Created_By");
                String lastUpdatedBy = r.getString("Last_Updated_By");
                int cDivisionId = r.getInt("Division_ID");
                String cDivisionName = r.getString("Division");
                int cCountryId = r.getInt("Country_ID");
                String cCountryName = r.getString("Country");

                Customer customer = new Customer(customerName,address, postalCode, phone, createdBy ,lastUpdatedBy,cDivisionId, cDivisionName, cCountryId,cCountryName,customerID );
                customersList.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customersList;
    }

    /**
     *
     * @return Query that gets all customers
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllCus() throws SQLException {
        ObservableList<Customer> cusList = FXCollections.observableArrayList();
        try {
            //String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division from customers INNER JOIN  first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID";
            String sql = "SELECT * FROM customers ";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();

            while (r.next()) {
                int customerID = r.getInt("Customer_ID");
                String customerName = r.getString("Customer_Name");
                String address = r.getString("Address");
                String postalCode = r.getString("Postal_Code");
                String phone = r.getString("Phone");
                String createDate = r.getString("Create_Date");
                String createdBy = r.getString("Created_By");
                LocalDateTime lastUpdate = r.getTimestamp("Last_Update").toLocalDateTime();
                int cDivisionId = r.getInt("Division_ID");
                Customer customer = new Customer(customerID, customerName, address, postalCode, phone,createDate, createdBy, lastUpdate);
                cusList.add(customer);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cusList;

    }

    /**
     *
     * @return Query for customers  getting country id and division that have the associated country id
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllCus1() throws SQLException {
        try {

            String sql = "SELECT customers.Customer_ID, customers.Customer_Name,customers.Address,customers.Postal_Code,customers.Phone,customers.Division_ID, countries.Country,first_level_divisions.Division\n" +
                    "From customers \n" +
                    "INNER JOIN first_level_divisions\n" +
                    "ON customers.Division_ID = first_level_divisions.Division_ID\n" +
                    "INNER JOIN countries\n" +
                    "ON first_level_divisions.Country_ID= countries.Country_ID;";


            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();
            ObservableList<Customer> cusList = FXCollections.observableArrayList();


            while (r.next()) {
                int customerID = r.getInt("Customer_ID");
                String customerName = r.getString("Customer_Name");
                String address = r.getString("Address");
                String postalCode = r.getString("Postal_Code");
                String phone = r.getString("Phone");
                //String createDate = r.getString("Create_Date");
                //String createdBy = r.getString("Created_By");
                //LocalDateTime lastUpdate = r.getTimestamp("Last_Update").toLocalDateTime();
                int cusDivisionId = r.getInt("Division_ID");
                String cusCountryName = r.getString("Country");
                String cusDivisionName = r.getString("Division");
                Customer customer = new Customer(customerID, customerName, address, postalCode, phone,cusDivisionId,cusCountryName,cusDivisionName);
                cusList.add(customer);

            }
            return cusList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




    }

    /**
     *
     * @return Query of number of customers inside each division
     * @throws SQLException
     */
    public static ObservableList<Customer> getCountryReport() throws SQLException {
        ObservableList<Customer> cusReport = FXCollections.observableArrayList();

        try {

            String sql = "SELECT first_level_divisions.Division AS cusDivisionName,\n" +
                    "COUNT(customers.Customer_ID) AS numCus\n" +
                    "FROM customers\n" +
                    "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID\n" +
                    "GROUP BY first_level_divisions.Division_ID";


            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();



            while (r.next()) {

                String cusDivisionName = r.getString("cusDivisionName");
                int numCus = r.getInt("numCus");

                Customer customer = new Customer(cusDivisionName,numCus);
                cusReport.add(customer);

            }
            return cusReport;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


/**
 * Variant on add customer
 */
    }
    public static void addCus(String customerName, String address, String postalCode, String phone, LocalDateTime createDate,LocalDateTime lastUpdate, int cusDivisionId ){
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Last_Update, Division_ID) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            s.setString(1,customerName);
            s.setString(2,address);
            s.setString(3,postalCode);
            s.setString(4,phone);
            s.setTimestamp(5,Timestamp.valueOf(createDate));
            s.setTimestamp(6,Timestamp.valueOf(lastUpdate));
            s.setInt(7, cusDivisionId);
            s.executeUpdate();

        }catch (SQLException e){e.printStackTrace();}
    }

    /**
     *
     * @param customerId cus ID
     * @param customerName cus Name
     * @param address cus address
     * @param postalCode cus postalCode
     * @param phone cus phone
     * @param lastUpdatedBy user it was last updated by
     * @param lastUpdate last time it was updated
     * @param cusDivisionId cus Division name
     * @param cusCountryId cus country ID
     */
    public static void modifyCus(int customerId, String customerName, String address, String postalCode, String phone, String lastUpdatedBy, Timestamp lastUpdate, int cusDivisionId, int cusCountryId){
try{
    String sql = "UPDATE customers SET Customer_Name = ? , Address = ?, Postal_Code = ?, Phone = ?, Last_Updated_By = ?, Last_Update = ?, Division_ID =? WHERE Customer_ID = ?";
    PreparedStatement s = JDBC.connection.prepareStatement(sql);
    s.setString(1,customerName);
    s.setString(2,address);
    s.setString(3,postalCode);
    s.setString(4, phone);
    s.setString(5,lastUpdatedBy);
    s.setTimestamp(6,lastUpdate);
    s.setInt(7,cusDivisionId);
    //s.setInt(8, cusCountryId);
    s.setInt(8,customerId);

    s.executeUpdate();
}catch (SQLException e){e.printStackTrace();}

    }

    /**
     *
     * @param customerId customer ID
     * @return customer ID anc customer name
     * @throws SQLException
     */
    public static Customer sendCustomer(int customerId) throws SQLException{
        String sql ="SELECT * FROM customers WHERE Customer_ID = ?";
        PreparedStatement s = JDBC.connection.prepareStatement(sql);
        s.setInt(1,customerId);
        s.execute();
        ResultSet r = s.executeQuery();

        while(r.next()){
            int fCustomerId = r.getInt("Customer_ID");
            String customerName = r.getString("Customer_Name");

            Customer cus = new Customer(fCustomerId, customerName);
            return cus;

        }return null;
    }

}
