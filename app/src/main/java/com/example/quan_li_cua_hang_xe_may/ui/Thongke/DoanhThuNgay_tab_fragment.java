package com.example.quan_li_cua_hang_xe_may.ui.Thongke;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quan_li_cua_hang_xe_may.Adapter.HoaDonListAdapter;
import com.example.quan_li_cua_hang_xe_may.R;
import com.example.quan_li_cua_hang_xe_may.Sql.HoaDon;
import com.example.quan_li_cua_hang_xe_may.ui.HoaDon.ChitietHoadon;
import com.example.quan_li_cua_hang_xe_may.ui.home.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DoanhThuNgay_tab_fragment extends Fragment {

    ImageView img_time;
    GridView grip_ngay;
    TextView txt_time, txt_doanhthu;
    public static ArrayList<HoaDon> list_day;
    private HoaDonListAdapter adapter_day;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.doanhthungay_tab_fragment, container, false);
        txt_time = root.findViewById(R.id.txt_time);
        txt_doanhthu = root.findViewById(R.id.txt_doanhthungay);

        list_day = new ArrayList<>();

        grip_ngay=root.findViewById(R.id.gripview_ngay);
        adapter_day = new HoaDonListAdapter(getActivity(), R.layout.layout_hoadon, list_day);
        grip_ngay.setAdapter(adapter_day);

        img_time = root.findViewById(R.id.img_time);
        img_time.setOnClickListener(v -> Chonngay());

        grip_ngay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openView(position);
                Toast.makeText(getActivity().getApplicationContext(), "Information", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    private void openView(int position) {
        final HoaDon hoaDon = list_day.get(position);
        int MaHD=hoaDon.getID();
        String tensp=hoaDon.getTensp();
        String ngaymuasp=hoaDon.getNgaymua();
        String giamuasp=hoaDon.getGiamua();
        String tenkhachmua=hoaDon.getKhachmua();
        String soluongmua=hoaDon.getSoluong();
        String thanhtiensp=hoaDon.getThanhtien();

        Intent it=new Intent(getActivity().getApplicationContext(), ChitietHoadon.class);
        it.putExtra("MaHD",MaHD);
        it.putExtra("tensp",tensp);
        it.putExtra("ngaymuasp",ngaymuasp);
        it.putExtra("giamuasp",giamuasp);
        it.putExtra("tenkhachmuasp",tenkhachmua);
        it.putExtra("soluongmuasp",soluongmua);
        it.putExtra("thanhtiensp",thanhtiensp);
        startActivity(it);
    }

    private void Chonngay() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            txt_time.setText("Ngày " + simpleDateFormat.format(calendar.getTime()));
            List_day(simpleDateFormat.format(calendar.getTime()));
            int doanhthu = 0;
            for (int i = 0; i < list_day.size(); i++) {
                final HoaDon hoaDon = list_day.get(i);
                String str =hoaDon.getThanhtien().replaceAll(",","");
                doanhthu += Integer.parseInt(str);
            }
            txt_doanhthu.setText(String.valueOf(doanhthu));
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void List_day(String ngay) {

        Cursor cursor = HomeFragment.sqLiteHelper.getData("SELECT * FROM HoaDon WHERE ngaymua LIKE '%" + ngay + "%'");
        list_day.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tensp = cursor.getString(1);
            String ngaymua = cursor.getString(2);
            String khachmua = cursor.getString(3);
            String giamua = cursor.getString(4);
            String soluongmua = cursor.getString(5);
            String thanhtien = cursor.getString(6);
            list_day.add(new HoaDon(id, tensp, ngaymua, khachmua, giamua, soluongmua, thanhtien));
        }
        adapter_day.notifyDataSetChanged();
    }
}
