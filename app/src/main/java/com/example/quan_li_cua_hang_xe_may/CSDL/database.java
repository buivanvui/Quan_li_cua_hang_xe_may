package com.example.quan_li_cua_hang_xe_may.CSDL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.quan_li_cua_hang_xe_may.Sql.NhanVien;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class database extends SQLiteOpenHelper {
    private Context context;

    public database(Context context) {
        super(context, "Login.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tạo bảng nhân viên
        db.execSQL("CREATE TABLE DANGNHAP(" +
                "user TEXT PRIMARY KEY," +
                "pass TEXT NOT NULL )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DANGNHAP");
        onCreate(db);
    }

    public Boolean insert(String user, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user", user);
        values.put("pass", pass);
        long result = db.insert("DANGNHAP", null, values);
        if (result == -1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from DANGNHAP where user=?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from DANGNHAP where user=? and pass=?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

}
