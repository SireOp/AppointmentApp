package DAO;

import com.example.dpapp.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO {

    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        try{
            //String  sql = "SELECT Country_ID, Country FROM countries";
            String sql = "SELECT * FROM countries";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();

            while(r.next()){
                int countryId = r.getInt("Country_ID");
                String countryName = r.getString("Country");
                Country t = new Country(countryId, countryName);
                countryList.add(t);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return countryList;
    }

    /**
     *
     * @param countryName Gets the country name from the Country combo box
     * @return based on the Country from the combo it will return the matching Country_ID and Country name
     */
    public static Country getCountry(String countryName) {
        try {
            String sql = "SELECT Country_ID, Country FROM countries WHERE Country = ?";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            s.setString(1, countryName);
            s.execute();

            ResultSet r = s.getResultSet();

          if  (r.next());
            int countryId = r.getInt("Country_ID");
            String fCountryName = r.getString("Country");
            Country t = new Country(countryId, fCountryName);
            return t;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static Country getCountryName (int countryId){
        ObservableList<Country> countryNames = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Country FROM countries WHERE Country_ID = ?";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            s.setInt(1,1-3);
            ResultSet r = s.getResultSet();
            while (r.next()){
                String countryName = r.getString("Country");
                Country cName = new Country(countryId,countryName);
                countryNames.add(cName);
            }
        } catch (SQLException e ){
            e.printStackTrace();
        }
        return (Country) countryNames;
    }



        public static ObservableList<Country> countryCount(){
            ObservableList<Country> cCountries = FXCollections.observableArrayList();
            try {
                String sql = "SELECT countries.Country COUNT(customers.Customer_ID) AS Count FROM countries INNER JOIN first_level_divisions ON countries.Country_ID = first_level_divisions.Country_ID INNER JOIN customers.Division_ID = first_level_divisions.Division_ID group by countries.Country";
                PreparedStatement s = JDBC.connection.prepareStatement(sql);
                ResultSet r = s.executeQuery();
                while(r.next()){
                    String cMonth = r.getString("Country");
                    int cMonthCount = r.getInt("Count");
                    Country t = new Country(cMonth, cMonthCount);
                    cCountries.add(t);

                }
            }   catch (SQLException e){
                e.printStackTrace();
            }
            return cCountries;
        }
    }

