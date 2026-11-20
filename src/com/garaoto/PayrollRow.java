package com.garaoto;

public class PayrollRow {
    public int id, soCong;
    public String maNv, hoTen;
    public double luongCung, donGiaCong, thuong, phat, tamUng;

    public double tienCong() { return soCong * donGiaCong; }
    public double tongLuong() { return luongCung + tienCong() + thuong - phat - tamUng; }
}
