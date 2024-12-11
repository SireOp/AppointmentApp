package DAO;

import com.example.dpapp.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.FirstLvDivision;
import controller.AppointmentController;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Query pulls all the divisions
 */
public class FirstLvDivisionDAO {
    public static ObservableList<FirstLvDivision> getAllDivIds(){
        ObservableList<FirstLvDivision> allDivisions = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * From first_level_divisions";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();

            //LocalDateTime startTime = r.getTimestamp("Start").toLocalDateTime();
            //Timestamp lastUpdate = r.getTimestamp("Last_Update").toLocalDateTime();


            while(r.next()){
                int divisionId = r.getInt("Division_ID");
                String divisionName = r.getString("Division");
                int countryId = r.getInt("Country_ID");
                Timestamp Create_Date =r.getTimestamp("Create_Date");
                LocalDateTime createDate = Create_Date.toLocalDateTime();
                String  createdBy = r.getString("Created_By");
                Timestamp Last_Update =r.getTimestamp("Last_Update");
                LocalDateTime lastUpdate = Last_Update.toLocalDateTime();
                String lastUpdatedBy = r.getString("Last_Updated_By");
                FirstLvDivision f = new FirstLvDivision(divisionId, divisionName,countryId,createDate,createdBy,lastUpdate,lastUpdatedBy);
                allDivisions.add(f);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        } return allDivisions;
    }

    /**
     * Query that returns all US divisions
     * @return All divisions that have the country ID of 1
     */
    public static ObservableList<FirstLvDivision> getUsDivIds(){
        ObservableList<FirstLvDivision> allUSDivisions = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * From first_level_divisions WHERE Country_ID = 1";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();

            //LocalDateTime startTime = r.getTimestamp("Start").toLocalDateTime();
            //Timestamp lastUpdate = r.getTimestamp("Last_Update").toLocalDateTime();


            while(r.next()){
                int divisionId = r.getInt("Division_ID");
                String divisionName = r.getString("Division");
                int countryId = r.getInt("Country_ID");
                Timestamp Create_Date =r.getTimestamp("Create_Date");
                LocalDateTime createDate = Create_Date.toLocalDateTime();
                String  createdBy = r.getString("Created_By");
                Timestamp Last_Update =r.getTimestamp("Last_Update");
                LocalDateTime lastUpdate = Last_Update.toLocalDateTime();
                String lastUpdatedBy = r.getString("Last_Updated_By");
                FirstLvDivision f = new FirstLvDivision(divisionId, divisionName,countryId,createDate,createdBy,lastUpdate,lastUpdatedBy);
                allUSDivisions.add(f);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        } return allUSDivisions;
    }

    /**
     * Query returns all divisions with the country id of 2
     * @return all divisions that are in UK/ country ID 2
     */
    public static ObservableList<FirstLvDivision> getUkDivIds(){
        ObservableList<FirstLvDivision> allUkDivisions = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * From first_level_divisions WHERE Country_ID = 2";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();

            //LocalDateTime startTime = r.getTimestamp("Start").toLocalDateTime();
            //Timestamp lastUpdate = r.getTimestamp("Last_Update").toLocalDateTime();


            while(r.next()){
                int divisionId = r.getInt("Division_ID");
                String divisionName = r.getString("Division");
                int countryId = r.getInt("Country_ID");
                Timestamp Create_Date =r.getTimestamp("Create_Date");
                LocalDateTime createDate = Create_Date.toLocalDateTime();
                String  createdBy = r.getString("Created_By");
                Timestamp Last_Update =r.getTimestamp("Last_Update");
                LocalDateTime lastUpdate = Last_Update.toLocalDateTime();
                String lastUpdatedBy = r.getString("Last_Updated_By");
                FirstLvDivision f = new FirstLvDivision(divisionId, divisionName,countryId,createDate,createdBy,lastUpdate,lastUpdatedBy);
                allUkDivisions.add(f);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        } return allUkDivisions;
    }

    /**
     * Query returns all divisions with the country id of 3
     * @return all divisions that are in Canada/ country ID 3
     */
    public static ObservableList<FirstLvDivision> getCaDivIds(){
        ObservableList<FirstLvDivision> allCaDivisions = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * From first_level_divisions WHERE Country_ID = 3";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();

            //LocalDateTime startTime = r.getTimestamp("Start").toLocalDateTime();
            //Timestamp lastUpdate = r.getTimestamp("Last_Update").toLocalDateTime();


            while(r.next()){
                int divisionId = r.getInt("Division_ID");
                String divisionName = r.getString("Division");
                int countryId = r.getInt("Country_ID");
                Timestamp Create_Date =r.getTimestamp("Create_Date");
                LocalDateTime createDate = Create_Date.toLocalDateTime();
                String  createdBy = r.getString("Created_By");
                Timestamp Last_Update =r.getTimestamp("Last_Update");
                LocalDateTime lastUpdate = Last_Update.toLocalDateTime();
                String lastUpdatedBy = r.getString("Last_Updated_By");
                FirstLvDivision f = new FirstLvDivision(divisionId, divisionName,countryId,createDate,createdBy,lastUpdate,lastUpdatedBy);
                allCaDivisions.add(f);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        } return allCaDivisions;
    }

    /**
     * Query that gets the division that have the same division ID
     * @param divisionId Division ID
     * @return
     */
    public static FirstLvDivision getDivisionLv(int divisionId){
        try{
            String sql = "SELECT Division_ID, Division FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            s.setInt(1,divisionId);
            s.execute();
            ResultSet r = s.getResultSet();
            r.next();
            int fDivisionId = r.getInt("Division_ID");
            String divisionName = r.getString("Division");
            FirstLvDivision t = new FirstLvDivision(fDivisionId, divisionName);
            return t;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets divisions based on country ID
     * @param countryId country ID
     * @return All the divisions that has the same country ID as the given country
     * @throws SQLException
     */
    public static ObservableList<FirstLvDivision> comboDivision(int countryId) throws SQLException {
        ObservableList<FirstLvDivision> divisionSelection = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?"+ countryId;
        PreparedStatement s = JDBC.connection.prepareStatement(sql);
        s.setInt(1,countryId);
        s.execute();
        ResultSet r = s.getResultSet();

        while (r.next()){
            int divisionId = r.getInt("Division_ID");
            String divisionName = r.getString("Division");
            countryId = r.getInt("Country_ID");
            Timestamp Create_Date =r.getTimestamp("Create_Date");
            LocalDateTime createDate = Create_Date.toLocalDateTime();
            String  createdBy = r.getString("Create_By");
            Timestamp Last_Update =r.getTimestamp("Last_Update");
            LocalDateTime lastUpdate = Last_Update.toLocalDateTime();
            String lastUpdatedBy = r.getString("Last_Updated_By");
            FirstLvDivision f = new FirstLvDivision(divisionId, divisionName,countryId,createDate,createdBy,lastUpdate,lastUpdatedBy);
            divisionSelection.add(f);

        } return divisionSelection;
    }

    /**
     * Query incomplete
     * @return
     */
    public static ObservableList<Country> fDivisionCount(){
        ObservableList<Country> fDivisions = FXCollections.observableArrayList();
        try {
            String sql = "SELECT first_level_divisions.Division COUNT(customers.Customer_ID) AS Count FROM countries INNER JOIN first_level_divisions ON countries.Country_ID = first_level_divisions.Country_ID INNER JOIN customers.Division_ID = first_level_divisions.Division_ID group by countries.Country";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();
            while(r.next()){
                String fDivision = r.getString("Division");
                int fDivisionCount = r.getInt("Count");
                Country t = new Country(fDivision, fDivisionCount);
                fDivisions.add(t);

            }
        }   catch (SQLException e){
            e.printStackTrace();
        }
        return fDivisions;
    }

    /**
     * Query that brings up all the countries
     */
    public  void  getCountries(){
        try{
            String sql = "SELECT Country FROM countries";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();

            ObservableList<String> countryList = FXCollections.observableArrayList();
            while (r.next()){
                countryList.add(r.getString("Country"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    /**
     * Another version of getting divisions based on divisions and countries sharing the same country ID
     */
    public static void getDivisions(){
        try{
            //get the country from the first combo box
            String sql = "SELECT Division FROM first_level_divisions" +
                                    "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID" +
                                    "WHERE countries.Country = ?";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();
            ObservableList<String> divisionList = FXCollections.observableArrayList();
            while(r.next()){
                divisionList.add(r.getString("Division"));
            }

        }catch (SQLException e){e.printStackTrace();}
    }
    AppointmentController appController = new AppointmentController();

    /**
     *   all the First LV divisions that have the same id as the selected Country
     * @param countryName Selected country
     */
    public void FillDivisions (String countryName){
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Division FROM first_level_divisions" + "INNER JOIN countries ON first_level_divisions.Country_ID" + "WHERE Country = ?";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            s.setString(1,countryName);
            ResultSet r = s.executeQuery();
            while(r.next()){
                divisionList.add(r.getString("Division"));
            }
        }catch (SQLException e){e.printStackTrace();}
    }

}
