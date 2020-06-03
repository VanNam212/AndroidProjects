package com.example.contentprovider;

public class ContactModel {
    private int Id;
    private String ContactName,PhoneNumber;

    public ContactModel(int id, String contactName, String phoneNumber) {
        Id = id;
        ContactName = contactName;
        PhoneNumber = phoneNumber;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
