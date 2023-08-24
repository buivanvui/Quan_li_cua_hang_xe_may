package com.example.quan_li_cua_hang_xe_may.ui.Thongke;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quan_li_cua_hang_xe_may.Adapter.HoaDonListAdapter;
import com.example.quan_li_cua_hang_xe_may.R;
import com.example.quan_li_cua_hang_xe_may.Sql.HoaDon;
import com.example.quan_li_cua_hang_xe_may.ui.HoaDon.ChitietHoadon;
import com.example.quan_li_cua_hang_xe_may.ui.HoaDon.Fragment_HoaDon;
import com.example.quan_li_cua_hang_xe_may.ui.home.HomeFragment;

import java.util.ArrayList;

public class DoanhThuThang_tab_fragment extends Fragment {

    Spinner spinMonth;
    GridView grip_thang;
    TextView txt_doanhthu_month;
    public static ArrayList<HoaDon> list_month;
    HoaDonListAdapter adapter_month;
    static final String[] Months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.doanhthuthang_tab_fragment, container, false);

        txt_doanhthu_month = root.findViewById(R.id.txt_doanhthuthang);

        list_month = new ArrayList<>();

        grip_thang=root.findViewById(R.id.gripview_thang);
        adapter_month = new HoaDonListAdapter(getActivity(), R.layout.layout_hoadon, list_month);
        grip_thang.setAdapter(adapter_month);

        ArrayAdapter<String> adapterMonths = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Months);
        adapterMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMonth = root.findViewById(R.id.spinner_month);
        spinMonth.setAdapter(adapterMonths);
        spinMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectItem = parent.getItemAtPosition(position).toString();
                List_month("/" + selectItem + "/");
                int doanhthu_month = 0;
                for (int i = 0; i < list_month.size(); i++) {
                    final HoaDon hoaDon = list_month.get(i);
                    String str =hoaDon.getThanhtien().replaceAll(",","");
                    doanhthu_month += Integer.parseInt(str);
                }
                txt_doanhthu_month.setText(String.valueOf(doanhthu_month));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        grip_thang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openView(position);
                Toast.makeText(getActivity().getApplicationContext(), "Information", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
    private void openView(int position) {
        final HoaDon hoaDon = list_month.get(position);
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
    private void List_month(String month) {

        Cursor cursor = HomeFragment.sqLiteHelper.getData("SELECT * FROM HoaDon WHERE ngaymua LIKE '%" + month + "%'");
        list_month.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tensp = cursor.getString(1);
            String ngaymua = cursor.getString(2);
            String khachmua = cursor.getString(3);
            String giamua = cursor.getString(4);
            String soluongmua = cursor.getString(5);
            String thanhtien = cursor.getString(6);
            list_month.add(new HoaDon(id, tensp, ngaymua, khachmua, giamua, soluongmua, thanhtien));
        }
        adapter_month.notifyDataSetChanged();
    }

}

