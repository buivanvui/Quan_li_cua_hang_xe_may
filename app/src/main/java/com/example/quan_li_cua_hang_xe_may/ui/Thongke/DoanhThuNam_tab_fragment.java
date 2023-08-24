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
import java.util.Calendar;

public class DoanhThuNam_tab_fragment extends Fragment {

    Spinner spinYear;
    GridView grip_nam;
    TextView txt_doanhthu_year;
    public static ArrayList<HoaDon> list_year;
    HoaDonListAdapter adapter_year;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.doanhthunam_tab_fragment, container, false);

        txt_doanhthu_year = root.findViewById(R.id.txt_doanhthunam);

        list_year = new ArrayList<>();

        grip_nam=root.findViewById(R.id.gripview_nam);
        adapter_year= new HoaDonListAdapter(getActivity(), R.layout.layout_hoadon, list_year);
        grip_nam.setAdapter(adapter_year);


        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2010; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapteryears = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, years);
        spinYear = root.findViewById(R.id.spinner_year);
        spinYear.setAdapter(adapteryears);

        spinYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectItem = parent.getItemAtPosition(position).toString();
                List_year("/" + selectItem);
                int doanhthu_year = 0;
                for (int i = 0; i < list_year.size(); i++) {
                    final HoaDon hoaDon = list_year.get(i);
                    String str =hoaDon.getThanhtien().replaceAll(",","");
                    doanhthu_year += Integer.parseInt(str);
                }
                txt_doanhthu_year.setText(String.valueOf(doanhthu_year));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        grip_nam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openView(position);
                Toast.makeText(getActivity().getApplicationContext(), "Information", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
    private void openView(int position) {
        final HoaDon hoaDon = list_year.get(position);
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
    private void List_year(String year) {

        Cursor cursor = HomeFragment.sqLiteHelper.getData("SELECT * FROM HoaDon WHERE ngaymua LIKE '%" + year + "%'");
        list_year.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tensp = cursor.getString(1);
            String ngaymua = cursor.getString(2);
            String khachmua = cursor.getString(3);
            String giamua = cursor.getString(4);
            String soluongmua = cursor.getString(5);
            String thanhtien = cursor.getString(6);
            list_year.add(new HoaDon(id, tensp, ngaymua, khachmua, giamua, soluongmua, thanhtien));
        }
        adapter_year.notifyDataSetChanged();
    }
}

