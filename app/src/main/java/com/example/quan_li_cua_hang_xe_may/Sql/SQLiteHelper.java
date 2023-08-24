package com.example.quan_li_cua_hang_xe_may.Sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String tenxe, String loaixe, String gia, String soluong, String mota, byte[] image) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO XE VALUES (NULL,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, tenxe);
        statement.bindString(2, loaixe);
        statement.bindString(3, gia);
        statement.bindString(4, soluong);
        statement.bindString(5, mota);
        statement.bindBlob(6, image);

        statement.executeInsert();
    }

    public void updateData(String tenxe, String loaixe, String gia, String soluong, String mota, byte[] image, int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE XE SET tenxe=?,loaixe=?,gia=?,soluong=?,mota=?,image=? WHERE Id=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, tenxe);
        statement.bindString(2, loaixe);
        statement.bindString(3, gia);
        statement.bindString(4, soluong);
        statement.bindString(5, mota);
        statement.bindBlob(6, image);
        statement.bindDouble(7, (double) id);
        statement.execute();
        database.close();
    }

    public void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM XE WHERE Id=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);
        statement.execute();
        database.close();
    }

    public Cursor getData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public void insertKH(String name, String birthday, String number, String CMND, String adress) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO KhachHang VALUES (NULL,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, name);
        statement.bindString(2, birthday);
        statement.bindString(3, number);
        statement.bindString(4, CMND);
        statement.bindString(5, adress);

        statement.executeInsert();
    }

    public void updateKH(String name, String birthday, String number, String CMND, String adress, int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE KhachHang SET name=?,birthday=?,number=?,CMND=?,adress=? WHERE id=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, name);
        statement.bindString(2, birthday);
        statement.bindString(3, number);
        statement.bindString(4, CMND);
        statement.bindString(5, adress);
        statement.bindDouble(6, (double) id);
        statement.execute();
        database.close();
    }

    public void deleteKH(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM KhachHang WHERE id=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);
        statement.execute();
        database.close();
    }

    public void insertBill(String tensp, String ngaymua, String khachmua, String giamua, String soluongmua, String thanhtien) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO HoaDon VALUES (NULL,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, tensp);
        statement.bindString(2, ngaymua);
        statement.bindString(3, khachmua);
        statement.bindString(4, giamua);
        statement.bindString(5, soluongmua);
        statement.bindString(6, thanhtien);

        statement.executeInsert();
    }

    public void deleteBill(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM HoaDon WHERE ID=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);
        statement.execute();
        database.close();
    }

    public void insertNV(String hoten, String ngaysinh, String sodienthoai, String soCMND, String gioitinh, String quequan, String chucvu) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO NhanVien VALUES (NULL,?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, hoten);
        statement.bindString(2, ngaysinh);
        statement.bindString(3, sodienthoai);
        statement.bindString(4, soCMND);
        statement.bindString(5, gioitinh);
        statement.bindString(6, quequan);
        statement.bindString(7, chucvu);

        statement.executeInsert();
    }

    public void updateNV(String hoten, String ngaysinh, String sodienthoai, String soCMND, String gioitinh, String quequan, String chucvu, int ID) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE NhanVien SET hoten=?,ngaysinh=?,sodienthoai=?,soCMND=?,gioitinh=?,quequan=?,chucvu=? WHERE ID=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, hoten);
        statement.bindString(2, ngaysinh);
        statement.bindString(3, sodienthoai);
        statement.bindString(4, soCMND);
        statement.bindString(5, gioitinh);
        statement.bindString(6, quequan);
        statement.bindString(7, chucvu);
        statement.bindDouble(8, (double) ID);
        statement.execute();
        database.close();
    }

    public void deleteNV(int ID) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM NhanVien WHERE ID=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) ID);
        statement.execute();
        database.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}