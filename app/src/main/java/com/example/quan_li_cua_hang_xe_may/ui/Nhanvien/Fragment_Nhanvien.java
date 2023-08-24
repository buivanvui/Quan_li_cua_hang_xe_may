package com.example.quan_li_cua_hang_xe_may.ui.Nhanvien;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.quan_li_cua_hang_xe_may.Adapter.NhanvienListAdapter;
import com.example.quan_li_cua_hang_xe_may.R;
import com.example.quan_li_cua_hang_xe_may.Sql.KhachHang;
import com.example.quan_li_cua_hang_xe_may.Sql.NhanVien;
import com.example.quan_li_cua_hang_xe_may.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Fragment_Nhanvien extends Fragment {

    private GridView gridView1;
    private ArrayList<NhanVien> list3;
    public static NhanvienListAdapter adapter2;
    FloatingActionButton fab_add;
    private EditText hoten, ngaysinh, sodienthoai, soCMND, quequan;
    private RadioButton rb_nam, rb_nu, rb_nhanvien, rb_quanly;
    private RadioGroup rg_gioitinh, rg_chucvu;
    String gioitinh = "", chucvu = "";
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nhanvien, container, false);

        setHasOptionsMenu(true);

        HomeFragment.sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS NhanVien (ID INTEGER PRIMARY KEY AUTOINCREMENT,hoten VARCHAR,ngaysinh VARCHAR,sodienthoai VARCHAR,soCMND VARCHAR,gioitinh VARCHAR,quequan VARCHAR,chucvu VARCHAR)");

        gridView1 = view.findViewById(R.id.gridview_nhanvien);
        list3 = new ArrayList<>();
        adapter2 = new NhanvienListAdapter(getActivity(), R.layout.layout_nhanvien, list3);
        gridView1.setAdapter(adapter2);
        updateNVList();
        fab_add = view.findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDialogAdd();
            }
        });

        gridView1.setOnItemLongClickListener((adapterView, view1, i, l) -> {

            CharSequence[] items = {"Update", "Delete"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle("Choose an action");
            dialog.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int item) {
                    if (item == 0) {
                        Cursor c = HomeFragment.sqLiteHelper.getData("SELECT ID FROM NhanVien");
                        ArrayList<Integer> arrID = new ArrayList<Integer>();
                        while (c.moveToNext()) {
                            arrID.add(c.getInt(0));
                        }
                        openDialogEdit(arrID.get(i));
                        Toast.makeText(getActivity().getApplicationContext(), "Update", Toast.LENGTH_SHORT).show();

                    } else {
                        //delete
                        Cursor c = HomeFragment.sqLiteHelper.getData("SELECT ID FROM NhanVien");
                        ArrayList<Integer> arrID = new ArrayList<Integer>();
                        while (c.moveToNext()) {
                            arrID.add(c.getInt(0));
                        }
                        showDialogDelete(arrID.get(i));
                        Toast.makeText(getActivity().getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.show();
            return true;
        });
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDialogView(position);
                Toast.makeText(getActivity().getApplicationContext(), "Information", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void openDialogView(int position) {
        androidx.appcompat.app.AlertDialog.Builder builders = new androidx.appcompat.app.AlertDialog.Builder(this.getActivity()); //lấy thông tin tất cả xe

        final NhanVien nhanVien = list3.get(position);
        StringBuffer buffer = new StringBuffer();
        buffer.append("ID: " + nhanVien.getId() + "\n\n");
        buffer.append("Họ và tên: " + nhanVien.getHoten() + "\n\n");
        buffer.append("Ngày sinh: " + nhanVien.getNgaysinh() + "\n\n");
        buffer.append("Số điện thoại: " + nhanVien.getSodienthoai() + "\n\n");
        buffer.append("Số căn cước công dân: " + nhanVien.getSoCMND() + "\n\n");
        buffer.append("Giới tính: " + nhanVien.getGioitinh() + "\n\n");
        buffer.append("Địa chỉ: " + nhanVien.getQuequan() + "\n\n");
        buffer.append("Chức vụ: " + nhanVien.getChucvu() + "\n");

        builders.setCancelable(true);
        builders.setTitle("INFORMATION");
        builders.setMessage(buffer.toString());
        builders.show();

    }

    private void OpenDialogAdd() {
        androidx.appcompat.app.AlertDialog.Builder builderss = new androidx.appcompat.app.AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_nhanvien, null);

        hoten = view.findViewById(R.id.edt_hoten);
        ngaysinh = view.findViewById(R.id.edt_ngaysinh);
        sodienthoai = view.findViewById(R.id.edt_sdt);
        soCMND = view.findViewById(R.id.edt_soCmnd);
        rb_nam = view.findViewById(R.id.rb_nam);
        rb_nu = view.findViewById(R.id.rb_nu);
        rb_nhanvien = view.findViewById(R.id.rb_nhanvien);
        rb_quanly = view.findViewById(R.id.rb_quanli);
        rg_gioitinh = view.findViewById(R.id.rg_gioitinh);
        rg_chucvu = view.findViewById(R.id.rg_chucvu);
        quequan = view.findViewById(R.id.edt_quequan);
        rg_gioitinh.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_nam:
                    break;
                case R.id.rb_nu:
                    break;
            }
        });
        rg_chucvu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_nhanvien:
                        break;
                    case R.id.rb_quanli:
                        break;
                }
            }
        });
        builderss.setView(view)
                .setNegativeButton("cancel", (dialogInterface, i) -> {

                })
                .setPositiveButton("ok", (dialogInterface, i) ->
                {
                    boolean add=true;
                    try {
                        if (rb_nam.isChecked()) {
                            gioitinh = "Nam";
                        }
                        if (rb_nu.isChecked()) {
                            gioitinh = "Nữ";
                        }
                        if (rb_nhanvien.isChecked()) {
                            chucvu = "Nhân viên";
                        }
                        if (rb_quanly.isChecked()) {
                            chucvu = "Quản lý";
                        }
                        for (i = 0; i < list3.size(); i++) {
                            final NhanVien nhanVien = list3.get(i);
                            if ( soCMND.getText().toString().trim().equalsIgnoreCase(nhanVien.getSoCMND())) {
                                Toast.makeText(getActivity(), "Trùng số chứng minh thư", Toast.LENGTH_SHORT).show();
                                add = false;
                            }
                            else if (sodienthoai.getText().toString().trim().equalsIgnoreCase(nhanVien.getSodienthoai())) {
                                Toast.makeText(getActivity(), "Trùng số điện thoại", Toast.LENGTH_SHORT).show();
                                add = false;
                            }
                        }
                        if (add==false){
                            Toast.makeText(getActivity(), "Not successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            HomeFragment.sqLiteHelper.insertNV(
                                    hoten.getText().toString().trim(),
                                    ngaysinh.getText().toString().trim(),
                                    sodienthoai.getText().toString().trim(),
                                    soCMND.getText().toString().trim(),
                                    gioitinh,
                                    quequan.getText().toString().trim(),
                                    chucvu
                            );
                            Toast.makeText(getActivity().getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    updateNVList();
                });
        builderss.show();
    }


    public void openDialogEdit(final int position) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_nhanvien, null);

        hoten = view.findViewById(R.id.edt_hoten);
        ngaysinh = view.findViewById(R.id.edt_ngaysinh);
        sodienthoai = view.findViewById(R.id.edt_sdt);
        soCMND = view.findViewById(R.id.edt_soCmnd);
        quequan = view.findViewById(R.id.edt_adress);
        rb_nam = view.findViewById(R.id.rb_nam);
        rb_nu = view.findViewById(R.id.rb_nu);
        rb_nhanvien = view.findViewById(R.id.rb_nhanvien);
        rb_quanly = view.findViewById(R.id.rb_quanli);
        rg_gioitinh = view.findViewById(R.id.rg_gioitinh);
        rg_chucvu = view.findViewById(R.id.rg_chucvu);
        rg_gioitinh.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_nam:
                    break;
                case R.id.rb_nu:
                    break;
            }
        });
        rg_chucvu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_nhanvien:
                        break;
                    case R.id.rb_quanli:
                        break;
                }
            }
        });
        builder.setView(view)
                .setNegativeButton("cancel", (dialogInterface, i) -> {

                })
                .setPositiveButton("ok", (dialogInterface, i) -> {
                    try {
                        if (rb_nam.isChecked()) {
                            gioitinh = "Nam";
                        }
                        if (rb_nu.isChecked()) {
                            gioitinh = "Nữ";
                        }
                        if (rb_nhanvien.isChecked()) {
                            chucvu = "Nhân viên";
                        }
                        if (rb_quanly.isChecked()) {
                            chucvu = "Quản lý";
                        }
                        HomeFragment.sqLiteHelper.updateNV(
                                hoten.getText().toString().trim(),
                                ngaysinh.getText().toString().trim(),
                                sodienthoai.getText().toString().trim(),
                                soCMND.getText().toString().trim(),
                                gioitinh,
                                quequan.getText().toString().trim(),
                                chucvu,
                                position
                        );
                        Toast.makeText(getActivity().getApplicationContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    updateNVList();
                });
        builder.show();
    }

    private void showDialogDelete(final int id) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getActivity());
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete");
        dialogDelete.setPositiveButton("OK", (dialogInterface, i) -> {
            try {
                HomeFragment.sqLiteHelper.deleteNV(id);
                Toast.makeText(getActivity().getApplicationContext(), "Delete successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("error", e.getMessage());
            }
            updateNVList();
        });
        dialogDelete.setNegativeButton("Cancel", (dialogInterface, i) -> {

        });
        dialogDelete.show();
    }

    private void updateNVList() {
        //get all data from sql
        Cursor cursor = HomeFragment.sqLiteHelper.getData("SELECT * FROM NhanVien");
        list3.clear();
        while (cursor.moveToNext()) {
            int ID = cursor.getInt(0);
            String hoten = cursor.getString(1);
            String ngaysinh = cursor.getString(2);
            String sodienthoai = cursor.getString(3);
            String soCMND = cursor.getString(4);
            String gioitinh = cursor.getString(5);
            String quequan = cursor.getString(6);
            String chucvu = cursor.getString(7);
            list3.add(new NhanVien(ID, hoten, ngaysinh, sodienthoai, soCMND, gioitinh, quequan, chucvu));
        }
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter2.getFilter().filter(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
