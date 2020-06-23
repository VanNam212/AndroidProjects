package com.example.dbman_song;

public class Song {
    //Fields
    private String Ten_BaiHat;
    private String Ten_CaSi;
    private String ThoiLuong;
    private int ID;

    //Contructor
    public Song()
    {

    }
    public Song(String ten_BaiHat, String ten_CaSi, String thoiLuong, int ID) {
        Ten_BaiHat = ten_BaiHat;
        Ten_CaSi = ten_CaSi;
        ThoiLuong = thoiLuong;
        this.ID = ID;
    }

    //Getter, setter
    public String getTen_BaiHat() {
        return Ten_BaiHat;
    }

    public void setTen_BaiHat(String ten_BaiHat) {
        Ten_BaiHat = ten_BaiHat;
    }

    public String getTen_CaSi() {
        return Ten_CaSi;
    }

    public void setTen_CaSi(String ten_CaSi) {
        Ten_CaSi = ten_CaSi;
    }

    public String getThoiLuong() {
        return ThoiLuong;
    }

    public void setThoiLuong(String thoiLuong) {
        ThoiLuong = thoiLuong;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
