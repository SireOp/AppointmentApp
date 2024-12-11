package model;

import java.time.LocalDateTime;

public class Customer {
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private String createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String  lastUpdatedBy;
    private int cusDivisionId;
    private int cusCountryId;
    private String cusDivisionName;
    private String cusCountryName;
    private int customerId;
    private int numCus;

    /**
     * Used to access Customer attributes
     */
    public Customer(){}
public Customer(String customerName,String address, String postalCode, String phone, String createdBy,String  lastUpdatedBy,int cusDivisionId, String cusDivisionName, int cusCountryId, String cusCountryName,int customerId){
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.cusDivisionId = cusDivisionId;
        this.cusDivisionName = cusDivisionName;
        this.cusCountryId = cusCountryId;
        this.cusCountryName = cusCountryName;
        this.customerId = customerId;


}

    /**
     *
     * @param customerId Customer ID
     * @param customerName Customer Name
     */
    public Customer(int customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    /**
     * Used for the Division Report table
     * @param cusDivisionName Division names
     * @param numCus count of user from a given divisions
     */
    public Customer(String cusDivisionName, int numCus){
        this.cusDivisionName = cusDivisionName;
        this.numCus = numCus;
    }

    /**
     * Getter for country id
     * @return Country ID
     */
    public int getCusCountryId() {
        return cusCountryId;
    }

    /**
     * Setter for country ID
     * @param cusCountryId
     */
    public void setCusCountryId(int cusCountryId) {
        this.cusCountryId = cusCountryId;
    }

    /**
     * getter for Customer division
     * @return Customer's division name
     */
    public String getCusDivisionName() {
        return cusDivisionName;
    }

    /**
     * setter for cus division name
     * @param cDivisionName cus Divisions name
     */
    public void setCusDivisionName(String cDivisionName) {
        this.cusDivisionName = cDivisionName;
    }

    public String getCusCountryName() {
        return cusCountryName;
    }

    public void setCusCountryName(String cusCountryName) {
        this.cusCountryName = cusCountryName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCusDivisionId() {
        return cusDivisionId;
    }

    public void setCDivisionId(int cusDivisionId) {
        this.cusDivisionId = cusDivisionId;
    }

    /**
     *
     * @return to String so that the customer data is readable
     */
    @Override
    public String toString(){
    return String.valueOf(customerId);
    //"[ " + customerId + "]";
    //+ customerName;
    }

    /**
     *
     * @param customerId Customer ID
     * @param customerName Customer Name
     * @param address Customer address
     * @param postalCode Customer postalcode
     * @param phone Customer phone number
     * @param cusDivisionId Customer Division ID
     * @param cusCountryName Customer country name
     * @param cusDivisionName Customer division name
     */
    public Customer(int customerId, String customerName,String address, String postalCode, String phone,int cusDivisionId,String cusCountryName ,String  cusDivisionName){
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.cusDivisionId = cusDivisionId;
        this.cusDivisionName = cusDivisionName;
        this.cusCountryName = cusCountryName;
        this.cusDivisionName = cusDivisionName;


    }

    /**
     *
     * @param customerId Customer ID
     * @param customerName Customer Name
     * @param address Customer address
     * @param postalCode Customer postalcode
     * @param phone Customer phone number
     * @param createdBy who created this customer
     * @param lastUpdatedBy what user updated this customer last
     * @param cusDivisionId Customer divisions ID
     * @param cusDivisionName Customer Division name
     * @param cusCountryId Customer country ID
     * @param cusCountryName Customer country name
     */
    public Customer(int customerId, String  customerName, String address, String postalCode, String phone,String createdBy,String lastUpdatedBy, int cusDivisionId, String cusDivisionName, int cusCountryId, String cusCountryName){

        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.cusDivisionId = cusDivisionId;
        this.cusDivisionName = cusDivisionName;
        this.cusCountryId = cusCountryId;
        this.cusCountryName = cusCountryName;
        this.phone = phone;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;




    }

    /**
     *
     * @param customerId Customer ID
     * @param customerName Customer Name
     * @param address Customer address
     * @param postalCode Customer postal code
     * @param phone Customer phone
     * @param createDate Customer createDate
     * @param createdBy What user created the customer
     * @param lastUpdate What user updated this customer last
     */
    public Customer(int customerId,String customerName,String address, String postalCode, String phone,String createDate, String createdBy,LocalDateTime lastUpdate){
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;



    }


    public int getNumCus() {
        return numCus;
    }

    public void setNumCus(int numCus) {
        this.numCus = numCus;
    }
}
