package com.example.customlistview;

import android.widget.ImageView;

public class Student {
    private String name;
    private int BirthDay;
    private int URLpicture;

    public Student(String name, int birthDay) {
        this.name = name;
        BirthDay = birthDay;
    }

    public Student(String name, int birthDay, int picture) {
        this.name = name;
        BirthDay = birthDay;
        this.URLpicture = picture;
    }

    public int getPicture() {
        return URLpicture;
    }

    public void setPicture(int picture) {
        this.URLpicture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(int birthDay) {
        BirthDay = birthDay;
    }
}
