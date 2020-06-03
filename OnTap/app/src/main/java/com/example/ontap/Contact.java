package com.example.ontap;

public class Contact {
    private int id;
    private String name;
    private String phone;
    private boolean check;

    public Contact() {
    }

    public Contact(int id, String name, String phone, boolean check) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.check = check;
    }

    public Contact(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
