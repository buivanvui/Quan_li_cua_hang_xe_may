package com.example.quan_li_cua_hang_xe_may.ui.HoaDon;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quan_li_cua_hang_xe_may.R;


public class ChitietHoadon extends AppCompatActivity {
    int MaHD;
    String tensp,ngaymuasp,giamuasp,tenkhachmuasp,soluongmuasp,thanhtiensp;
    private TextView ten, ngaymua, tenkhach, giamua, soluong, thanhtien, id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbill);

        Intent it=getIntent();
        if (it != null)
        {
                MaHD= it.getIntExtra("MaHD",0);
                tensp=it.getStringExtra("tensp");
                ngaymuasp=it.getStringExtra("ngaymuasp");
                giamuasp=it.getStringExtra("giamuasp");
                tenkhachmuasp=it.getStringExtra("tenkhachmuasp");
                soluongmuasp=it.getStringExtra("soluongmuasp");
                thanhtiensp=it.getStringExtra("thanhtiensp");
        }
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi Tiết Hóa Đơn");
        id = findViewById(R.id.text_id);
        ten = findViewById(R.id.text_ten);
        ngaymua = findViewById(R.id.text_ngayban);
        tenkhach = findViewById(R.id.text_khachmua);
        giamua = findViewById(R.id.text_giamua);
        soluong = findViewById(R.id.text_soluong);
        thanhtien = findViewById(R.id.text_thanhtien);
        id.setText(String.valueOf(MaHD));
        ten.setText(tensp);
        ngaymua.setText(ngaymuasp);
        giamua.setText(giamuasp);
        tenkhach.setText(tenkhachmuasp);
        soluong.setText(soluongmuasp);
        thanhtien.setText(thanhtiensp);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
