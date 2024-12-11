package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FirstLvDivision {



private int divisionId;
private String divisionName;
private int countryId;

    /**
     *
     * @return divisionId
     */
    public int getDivisionId() {
        return divisionId;
    }


    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     *
     * @return divisionName
     */
    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     *
     * @return countryId
     */
    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Makes the values readable
     * @return to string of the division ID and name
     */
    @Override
    public String toString(){
        return Integer.toString(divisionId) + "." + " " + divisionName;
    }

    /**
     *
     * @param divisionId Division ID
     * @param divisionName Division name
     * @param countryId Country ID
     * @param createDate Create date
     * @param createdBy Who created the divisions
     * @param lastUpdate When was the last division updated
     * @param lastUpdatedBy Who updated the divisions last
     */
    public FirstLvDivision(int divisionId, String divisionName, int countryId, LocalDateTime createDate,String createdBy,LocalDateTime lastUpdate,String lastUpdatedBy){

    this.divisionId = divisionId;
    this.divisionName = divisionName;
    this.countryId = countryId;



}

    /**
     *
     * @param divisionId Division id
     * @param divisionName Division name
     */
    public FirstLvDivision(int divisionId, String  divisionName){
        this.divisionId = divisionId;
        this.divisionName = divisionName;

}

}
