package com.example.devil.senslinksms;


public class PhoneContacts implements Comparable<PhoneContacts> {

    String contactName;
    String contactNumber;
    boolean isChecked;


    PhoneContacts(){};

    public PhoneContacts(String contactName, String contactNumber, boolean isChecked) {
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.isChecked = isChecked;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }


    public boolean isCheck() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
       this.isChecked = checked;
    }


    @Override
    public int compareTo(PhoneContacts another) {
        return this.getContactName().compareTo(another.getContactName());
    }
}
