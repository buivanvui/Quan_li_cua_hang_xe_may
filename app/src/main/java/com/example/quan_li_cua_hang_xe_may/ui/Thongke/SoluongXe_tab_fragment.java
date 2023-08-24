package com.example.quan_li_cua_hang_xe_may.ui.Thongke;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quan_li_cua_hang_xe_may.MainActivity;
import com.example.quan_li_cua_hang_xe_may.R;
import com.example.quan_li_cua_hang_xe_may.Sql.Xe;
import com.example.quan_li_cua_hang_xe_may.signup_tab_fragment;
import com.example.quan_li_cua_hang_xe_may.ui.home.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SoluongXe_tab_fragment extends Fragment {
    TextView txt_tongxe, txt_xega, txt_xeso, txt_xephankhoi, txt_xedien;
    public static ArrayList<Xe> listxega, listxeso, listxephankhoi, listxedien;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.soluongxe_tab_fragment, container, false);

        txt_tongxe = root.findViewById(R.id.txt_tongsoxe);
        txt_xega = root.findViewById(R.id.txt_SlXega);
        txt_xeso = root.findViewById(R.id.txt_SlXeso);
        txt_xephankhoi = root.findViewById(R.id.txt_SlXephankhoi);
        txt_xedien = root.findViewById(R.id.txt_SlXedien);
        listxega = new ArrayList<>();
        listxeso = new ArrayList<>();
        listxedien = new ArrayList<>();
        listxephankhoi = new ArrayList<>();

        int tong = 0, xega = 0, xeso = 0, xedien = 0, xephankhoi = 0;
        for (int i = 0; i < HomeFragment.list.size(); i++) {
            final Xe xe = HomeFragment.list.get(i);
            tong += Integer.parseInt(xe.getSoluong());
        }
        Listxega();
        Listxeso();
        Listxedien();
        Listxephankhoi();
        for (int i = 0; i < listxega.size(); i++) {
            final Xe xe = listxega.get(i);
            xega += Integer.parseInt(xe.getSoluong());
        }
        for (int i = 0; i < listxeso.size(); i++) {
            final Xe xe = listxeso.get(i);
            xeso += Integer.parseInt(xe.getSoluong());
        }
        for (int i = 0; i < listxedien.size(); i++) {
            final Xe xe = listxedien.get(i);
            xedien += Integer.parseInt(xe.getSoluong());
        }
        for (int i = 0; i < listxephankhoi.size(); i++) {
            final Xe xe = listxephankhoi.get(i);
            xephankhoi += Integer.parseInt(xe.getSoluong());
        }
        txt_tongxe.setText(String.valueOf(tong));
        txt_xega.setText(String.valueOf(xega));
        txt_xeso.setText(String.valueOf(xeso));
        txt_xedien.setText(String.valueOf(xedien));
        txt_xephankhoi.setText(String.valueOf(xephankhoi));

        return root;
    }

    private void Listxega() {

        Cursor cursor = HomeFragment.sqLiteHelper.getData("SELECT * FROM XE WHERE loaixe ='xe ga'");
        listxega.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenxe = cursor.getString(1);
            String loaixe = cursor.getString(2);
            String gia = cursor.getString(3);
            String soluong = cursor.getString(4);
            String mota = cursor.getString(5);
            byte[] image = cursor.getBlob(6);
            listxega.add(new Xe(id, tenxe, loaixe, gia, soluong, mota, image));
        }
    }

    private void Listxeso() {

        Cursor cursor = HomeFragment.sqLiteHelper.getData("SELECT * FROM XE WHERE loaixe ='xe so'");
        listxeso.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenxe = cursor.getString(1);
            String loaixe = cursor.getString(2);
            String gia = cursor.getString(3);
            String soluong = cursor.getString(4);
            String mota = cursor.getString(5);
            byte[] image = cursor.getBlob(6);
            listxeso.add(new Xe(id, tenxe, loaixe, gia, soluong, mota, image));
        }
    }

    private void Listxedien() {

        Cursor cursor = HomeFragment.sqLiteHelper.getData("SELECT * FROM XE WHERE loaixe ='xe dien'");
        listxedien.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenxe = cursor.getString(1);
            String loaixe = cursor.getString(2);
            String gia = cursor.getString(3);
            String soluong = cursor.getString(4);
            String mota = cursor.getString(5);
            byte[] image = cursor.getBlob(6);
            listxedien.add(new Xe(id, tenxe, loaixe, gia, soluong, mota, image));
        }
    }

    private void Listxephankhoi() {

        Cursor cursor = HomeFragment.sqLiteHelper.getData("SELECT * FROM XE WHERE loaixe ='xe phan khoi'");
        listxephankhoi.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenxe = cursor.getString(1);
            String loaixe = cursor.getString(2);
            String gia = cursor.getString(3);
            String soluong = cursor.getString(4);
            String mota = cursor.getString(5);
            byte[] image = cursor.getBlob(6);
            listxephankhoi.add(new Xe(id, tenxe, loaixe, gia, soluong, mota, image));
        }
    }

}
