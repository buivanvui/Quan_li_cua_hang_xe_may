package com.example.quan_li_cua_hang_xe_may.Sql;

public class NhanVien {
    private int ID;
    private String hoten, ngaysinh, sodienthoai, soCMND;
    private String gioitinh, quequan, chucvu;

    public int getId() {
        return ID;
    }

    public void setId(int ID) {
        this.ID = ID;
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


    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

    public NhanVien(int ID, String hoten, String ngaysinh, String sodienthoai, String soCMND, String gioitinh, String quequan, String chucvu) {
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.sodienthoai = sodienthoai;
        this.soCMND = soCMND;
        this.ID = ID;
        this.gioitinh = gioitinh;
        this.quequan = quequan;
        this.chucvu = chucvu;
    }

}

