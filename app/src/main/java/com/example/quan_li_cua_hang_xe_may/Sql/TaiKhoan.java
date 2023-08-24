package com.example.quan_li_cua_hang_xe_may.Sql;

public class TaiKhoan {
    private String taikhoan, matkhau;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public TaiKhoan(int id, String taikhoan, String matkhau) {
        this.id = id;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
    }
}
