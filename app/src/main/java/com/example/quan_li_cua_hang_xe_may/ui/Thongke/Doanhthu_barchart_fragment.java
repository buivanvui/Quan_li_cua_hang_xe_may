package com.example.quan_li_cua_hang_xe_may.ui.Thongke;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.quan_li_cua_hang_xe_may.R;
import com.example.quan_li_cua_hang_xe_may.Sql.HoaDon;
import com.example.quan_li_cua_hang_xe_may.ui.home.HomeFragment;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Doanhthu_barchart_fragment extends Fragment {
    ImageView img_timeBD, img_timeKT, img_lich;
    TextView txt_timeBD, txt_timeKT, tv_NgayBD, tv_NgayKT;
    PieChart pieChart;
    ArrayList<HoaDon> ds = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.bieudodoanhthu_tab_fragment, container, false);
        img_lich = root.findViewById(R.id.image_Lich);
        txt_timeBD = root.findViewById(R.id.tv_ngayFrom);
        txt_timeKT = root.findViewById(R.id.tv_ngayTo);
        pieChart = root.findViewById(R.id.piechart);
        img_lich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogLich();
            }
        });

        return root;
    }

    private void LoadPiechart() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < ds.size(); i++) {
            entries.add(new PieEntry(Integer.parseInt(ds.get(i).getSoluong()), ds.get(i).getTensp()));
        }
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }
        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }
        PieDataSet dataSet = new PieDataSet(entries, "Tên sản phẩm");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);
        pieChart.animateY(1400, Easing.EaseInOutQuad);

    }

    public void openDialogLich() {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_nhapngay, null);
        img_timeBD = view.findViewById(R.id.img_NgayBD);
        img_timeKT = view.findViewById(R.id.img_NgayKT);
        img_timeBD.setOnClickListener(v -> Chonngay1());
        img_timeKT.setOnClickListener(v -> Chonngay2());
        tv_NgayBD = view.findViewById(R.id.tv_dialog_NgayBD);
        tv_NgayKT = view.findViewById(R.id.tv_dialog_NgayKT);
        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String NgayBD = tv_NgayBD.getText().toString();
                        String NgayKT = tv_NgayKT.getText().toString();
                        Date dateNgayBD = null, dateNgayKT = null;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            dateNgayBD = sdf.parse(NgayBD);
                            dateNgayKT = sdf.parse(NgayKT);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (dateNgayBD.before(dateNgayKT) == false) {
                            Toast.makeText(getActivity().getApplicationContext(), "Ngày bắt đầu phải nhỏ hơn ngày kết thúc!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        txt_timeBD.setText(NgayBD);
                        txt_timeKT.setText(NgayKT);
                        Chart(txt_timeBD.getText().toString(),txt_timeKT.getText().toString());
                    }
                });

        builder.show();
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setEntryLabelTextSize(15);
        pieChart.setHoleRadius(18f);            // hình tròn bên trong
        pieChart.setTransparentCircleRadius(0f);  // vòng tròn trong suốt ở giữa
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Doanh thu");
        pieChart.setCenterTextSize(18);
        pieChart.getDescription().setEnabled(false);
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void Chonngay1() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            tv_NgayBD.setText(simpleDateFormat.format(calendar.getTime()));
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void Chonngay2() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            tv_NgayKT.setText(simpleDateFormat.format(calendar.getTime()));
        }, nam, thang, ngay);
        datePickerDialog.show();

    }

    private void Chart(String a, String b) {
        Cursor cursor = HomeFragment.sqLiteHelper.getData("SELECT * FROM HoaDon WHERE ngaymua BETWEEN '" + a + "' AND '" + b + "'");
        ds.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tensp = cursor.getString(1);
            String ngaymua = cursor.getString(2);
            String khachmua = cursor.getString(3);
            String giamua = cursor.getString(4);
            String soluongmua = cursor.getString(5);
            String thanhtien = cursor.getString(6);
            ds.add(new HoaDon(id, tensp, ngaymua, khachmua, giamua, soluongmua, thanhtien));
        }
        setupPieChart();
        LoadPiechart();
    }

}
