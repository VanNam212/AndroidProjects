package com.example.thisinh;

public class ThiSinh implements Comparable<ThiSinh> {
    private String SoBaoDanh;
    private String HoTen;
    private double Toan;
    private double Ly;
    private double Hoa;

    public ThiSinh() {
    }

    public ThiSinh(String soBaoDanh, String hoTen, double toan, double ly, double hoa) {
        SoBaoDanh = soBaoDanh;
        HoTen = hoTen;
        Toan = toan;
        Ly = ly;
        Hoa = hoa;
    }

    public String getSoBaoDanh() {
        return SoBaoDanh;
    }

    public void setSoBaoDanh(String soBaoDanh) {
        SoBaoDanh = soBaoDanh;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public double getToan() {
        return Toan;
    }

    public void setToan(double toan) {
        Toan = toan;
    }

    public double getLy() {
        return Ly;
    }

    public void setLy(double ly) {
        Ly = ly;
    }

    public double getHoa() {
        return Hoa;
    }

    public void setHoa(double hoa) {
        Hoa = hoa;
    }

    //tính tổng điểm
    public double tong_diem() {
        return getToan() + getLy() + getHoa();
    }

    //get tên
    public String getTen() {
        String[] arr = getHoTen().split(" ");
        return arr[arr.length - 1];
    }

    @Override
    public int compareTo(ThiSinh o) {
        // So sánh 2 String.
        int value = this.getTen().compareTo(o.getTen());

        // Nếu lastName của 2 đối tượng là không bằng nhau.
        if (value != 0) {
            return value;
        }
        return value;
    }
}
