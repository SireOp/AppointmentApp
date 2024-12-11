package DAO;

import com.example.dpapp.JDBC;
import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public  class UserDAO  extends User{

    public UserDAO(int appUserId, String userName, String userPass) {
        super(appUserId, userName, userPass);
    }

    /**
     * Query for all users
     * @return All users from users table
     * @throws SQLException
     */
    public static ObservableList<User> getAllUsers() throws  SQLException{
        ObservableList<User> userList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM users";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            ResultSet r = s.executeQuery();

            while (r.next()){
                int appUserId = r.getInt("User_ID");
                String userName = r.getString("User_Name");
                String userPass = r.getString("Password");


                User user = new User(appUserId, userName,userPass);
                userList.add(user);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return userList;
    }

    /**
     * Pulls the users that have the same username and password inputted by the user used as a check that the user credentials are correct
     * @param User_Name username
     * @param Password password
     * @return
     */
    public static boolean userCred(String User_Name, String Password){

        try(PreparedStatement getLog = JDBC.connection.prepareStatement("SELECT * FROM Users WHERE User_Name = ? AND Password = ?")){
            getLog.setString(1,User_Name);
            getLog.setString(2,Password);
            ResultSet r = getLog.executeQuery();
            if (r.next()){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Query gets all columns from the table that match the given username
     * @param User_Name user name
     * @return
     */
    public static boolean checkUser(String User_Name){

        try(PreparedStatement getLog = JDBC.connection.prepareStatement("SELECT * FROM Users WHERE  BINARY User_Name = ?")){
            getLog.setString(1,User_Name);
            ResultSet r = getLog.executeQuery();
            if (r.next()){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Select all columns from users where the password matches inputted password
     * @param Password
     * @return
     */
    public static boolean checkPass(String Password){

        try(PreparedStatement getLog = JDBC.connection.prepareStatement("SELECT * FROM Users WHERE  BINARY Password = ?")){
            getLog.setString(1,Password);
            ResultSet r = getLog.executeQuery();
            if (r.next()){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     *
     * @param UserName user name
     * @return The username and userID that match the given username
     * @throws SQLException
     */
    public static int getId(String UserName) throws SQLException{
        int userId = 0;
        String sql = "SELECT User_ID, User_Name FROM users WHERE User_Name = '" + UserName +"'";
        PreparedStatement s = JDBC.connection.prepareStatement(sql);
        ResultSet r = s.executeQuery();

        while (r.next()){
            userId = r.getInt("User_ID");
            UserName = r.getString("User_Name");
        }

        return userId;
    }

    /**
     *
     * @param appUserId is userId
     * @return  t from off User_ID
     */
    public static User returnId (int appUserId){
        try{
            String sql = "SELECT User_ID, User_Name FROM users WHERE User_ID = ?";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            s.setInt(1,appUserId);
            s.execute();
            ResultSet r = s.getResultSet();

           r.next();
           int  findUserId = r.getInt("User_ID");
           String userName = r.getString("User_Name");
           String userPass = r.getString("Password");
           User t = new User(findUserId, userName, userPass);
           return t;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    /**
     * Query for user ID and Username that match an inputted user ID
     * @param userId user id
     * @return
     */
    public static User sendUserId(int userId){
        try{
            String sql = "SELECT User_ID, User_Name FROM users WHERE User_ID = ?";
            PreparedStatement s = JDBC.connection.prepareStatement(sql);
            s.setInt(1,userId);
            s.execute();
            ResultSet r = s.getResultSet();

            r.next();
            int fUserId = r.getInt("User_ID");
            String userName = r.getString("User_Name");
            User us = new User(fUserId, userName);
            return us;
        } catch (SQLException e) {
             {
                throw new RuntimeException(e);
            }
        }

    }

    }

