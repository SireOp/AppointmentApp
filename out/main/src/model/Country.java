package model;

public class Country {
    private int countryId;
    private String countryName;
    private int cMonthCount;
    private String cMonth;

    public Country(String cMonth, int cMonthCount){
        this.cMonth = cMonth;
        this.cMonthCount = cMonthCount;
    }

    public Country(int countryId, String countryName){
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getcMonthCount() {
        return cMonthCount;
    }

    public void setcMonthCount(int cMonthCount) {
        this.cMonthCount = cMonthCount;
    }

    public String getcMonth() {
        return cMonth;
    }

    public void setcMonth(String cMonth) {
        this.cMonth = cMonth;
    }

    @Override
    public String toString(){
        //return Integer.toString(countryId) + "." + " " + countryName;
        return countryName;
    }
}

