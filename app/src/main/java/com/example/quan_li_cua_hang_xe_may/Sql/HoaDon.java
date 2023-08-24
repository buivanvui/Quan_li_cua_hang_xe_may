package com.example.quan_li_cua_hang_xe_may.Sql;

public class HoaDon {
    private String tensp, ngaymua, khachmua, giamua, soluong, thanhtien;
    private int ID;

    public HoaDon(int ID, String tensp, String ngaymua, String khachmua, String giamua, String soluong, String thanhtien) {
        this.ID = ID;
        this.tensp = tensp;
        this.ngaymua = ngaymua;
        this.khachmua = khachmua;
        this.giamua = giamua;
        this.soluong = soluong;
        this.thanhtien = thanhtien;

    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getNgaymua() {
        return ngaymua;
    }

    public void setNgaymua(String ngaymua) {
        this.ngaymua = ngaymua;
    }

    public String getKhachmua() {
        return khachmua;
    }

    public void setKhachmua(String khachmua) {
        this.khachmua = khachmua;
    }

    public String getGiamua() {
        return giamua;
    }

    public void setGiamua(String giamua) {
        this.giamua = giamua;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(String thanhtien) {
        this.thanhtien = thanhtien;
    }
}
