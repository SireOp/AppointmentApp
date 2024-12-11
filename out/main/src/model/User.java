package model;

import java.time.LocalDateTime;

/**
 * Setters and getters for User
 */
public class User {

    private int userId;
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUsedby;
    public  User(int userId, String userName,String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUsedby){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUsedby = lastUsedby;



    }

    /**
     *
     * @param userName User name
     */
    public User(String userName){
        this.userName = userName;
    }

    /**
     *
     * @param userId user ID
     * @param userName username
     * @param userPass user password
     */
    public User(int userId, String userName, String userPass){
        this.userId = userId;
        this.userName = userName;
        this.password = userPass;
    }

    /**
     *
     * @param userId User ID
     * @param userName User name
     */
    public User(int userId, String userName) {
    this.userId = userId;
    this.userName = userName;
    }

    /**
     * Getter for user id
     * @return user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * setter for User ID
     * @param userId user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for username
     * @return Username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for Username
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for crate Date
     * @return
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Setter for Create date
     * @param createDate
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter for create by
     * @return what user created
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for create by
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter for last update
     * @return last update
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Setter for last update
     * @param lastUpdate
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter for last used by
     * @return who used last
     */
    public String getLastUsedby() {
        return lastUsedby;
    }

    /**
     * Setter for last used by
     * @param lastUsedby
     */
    public void setLastUsedby(String lastUsedby) {
        this.lastUsedby = lastUsedby;
    }

    /**
     * Makes the return value readable
     * @return String of user ID
     */
    @Override
    public String toString(){
        return String.valueOf(userId);
        //Integer.toString(userId) + "." + " " + userName;
    }
}

