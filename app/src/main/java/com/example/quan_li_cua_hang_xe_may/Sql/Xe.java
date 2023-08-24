package com.example.quan_li_cua_hang_xe_may.Sql;

import java.io.Serializable;

public class Xe implements Serializable {
    private int id;
    private String tenxe;
    private String loaixe;
    private String gia;
    private String soluong;
    private String mota;
    private byte[] hinh;

    public Xe(int id, String tenxe, String loaixe, String gia, String soluong, String mota, byte[] hinh) {
        this.id = id;
        this.tenxe = tenxe;
        this.loaixe = loaixe;
        this.gia = gia;
        this.soluong = soluong;
        this.mota = mota;
        this.hinh = hinh;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenxe() {
        return tenxe;
    }

    public void setTenxe(String tenxe) {
        this.tenxe = tenxe;
    }

    public String getLoaixe() {
        return loaixe;
    }

    public void setLoaixe(String loaixe) {
        this.loaixe = loaixe;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }
}
