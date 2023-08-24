package com.example.quan_li_cua_hang_xe_may.Sql;

public class KhachHang {
    private String hoten, ngaysinh, diachi, sodienthoai, soCMND;
    private int ID;

    public KhachHang(int ID, String hoten, String ngaysinh, String sodienthoai, String soCMND, String diachi) {
        this.ID = ID;
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.sodienthoai = sodienthoai;
        this.soCMND = soCMND;
        this.diachi = diachi;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}