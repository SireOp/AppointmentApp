package model;

/**
 * Constructors and setter and getters for Contact
 */
public class Contact {

    private int contactId;
    private String contactName;
    private String contactEmail;

    /**
     *
     * @param contactId Contact ID
     * @param contactName Contact name
     * @param contactEmail Contact email
     */
    public  Contact(int contactId, String contactName, String contactEmail){
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return contactEmail;
    }

    public void setEmail(String email) {
        this.contactEmail = email;
    }

    public String toString(){
        return (contactName);
    }
}
