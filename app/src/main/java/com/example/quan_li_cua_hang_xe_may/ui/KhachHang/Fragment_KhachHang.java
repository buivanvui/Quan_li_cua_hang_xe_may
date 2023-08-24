package com.example.quan_li_cua_hang_xe_may.ui.KhachHang;

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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.quan_li_cua_hang_xe_may.R;
import com.example.quan_li_cua_hang_xe_may.Sql.HoaDon;
import com.example.quan_li_cua_hang_xe_may.Sql.KhachHang;
import com.example.quan_li_cua_hang_xe_may.Sql.Xe;
import com.example.quan_li_cua_hang_xe_may.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Fragment_KhachHang extends Fragment {

    private GridView gridView1;
    private ArrayList<KhachHang> list1;
    private ArrayList<HoaDon> listHoadon;
    KhachHangListAdapter adapter1;
    FloatingActionButton fab_add;
    private EditText birthday, number, CMND, adress;
    private Spinner spin_name;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khach_hang, container, false);

        setHasOptionsMenu(true);

        HomeFragment.sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS KhachHang (id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,birthday VARCHAR,number VARCHAR,CMND VARCHAR,adress VARCHAR)");

        gridView1 = view.findViewById(R.id.gridview_khachhang);
        list1 = new ArrayList<>();
        listHoadon = new ArrayList<>();
        adapter1 = new KhachHangListAdapter(getActivity(), R.layout.layout_khachhang, list1);
        gridView1.setAdapter(adapter1);
        updateKHList();
        fab_add = view.findViewById(R.id.fab_add);
        fab_add.setOnClickListener(view1 -> OpenDialogAdd());

        ListBill();

        return view;
    }

    public void openDialogView(int id,String ten,String ngaysinh,String sdt,String cmnd,String diachi) {
        androidx.appcompat.app.AlertDialog.Builder builders = new androidx.appcompat.app.AlertDialog.Builder(this.getActivity()); //lấy thông tin tất cả xe

        StringBuffer buffer = new StringBuffer();
        buffer.append("ID: " + id + "\n\n");
        buffer.append("Họ và tên: " + ten + "\n\n");
        buffer.append("Ngày sinh: " + ngaysinh + "\n\n");
        buffer.append("Số điện thoại: " + sdt + "\n\n");
        buffer.append("Số căn cước công dân: " + cmnd + "\n\n");
        buffer.append("Địa chỉ: " + diachi + "\n");

        builders.setCancelable(true);
        builders.setTitle("INFORMATION");
        builders.setMessage(buffer.toString());
        builders.show();

    }

    private void OpenDialogAdd() {
        androidx.appcompat.app.AlertDialog.Builder builderss = new androidx.appcompat.app.AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_khachhang, null);

        spin_name = view.findViewById(R.id.spin_name);
        birthday = view.findViewById(R.id.edt_birthday);
        number = view.findViewById(R.id.edt_number);
        CMND = view.findViewById(R.id.edt_cmnd);
        adress = view.findViewById(R.id.edt_adress);


        ArrayList<String> name = new ArrayList();
        for (int i = 0; i < listHoadon.size(); i++) {
            final HoaDon hoaDon = listHoadon.get(i);
            name.add(hoaDon.getKhachmua());
        }
        ArrayAdapter<String> adapterName = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, name);
        spin_name.setAdapter(adapterName);
        builderss.setView(view)
                .setNegativeButton("cancel", (dialogInterface, i) -> {

                })
                .setPositiveButton("ok", (dialogInterface, i) -> {
                    boolean add=true;
                    try {
                        for (i = 0; i < list1.size(); i++) {
                            final KhachHang khachHang = list1.get(i);
                            if ( CMND.getText().toString().trim().equalsIgnoreCase(khachHang.getSoCMND())) {
                                Toast.makeText(getActivity(), "Trùng số chứng minh thư", Toast.LENGTH_SHORT).show();
                                add = false;
                            }
                            else if (number.getText().toString().trim().equalsIgnoreCase(khachHang.getSodienthoai())) {
                                Toast.makeText(getActivity(), "Trùng số điện thoại", Toast.LENGTH_SHORT).show();
                                add = false;
                            }
                        }
                        if (add==false){
                            Toast.makeText(getActivity(), "Not successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            HomeFragment.sqLiteHelper.insertKH(
                                    adapterName.getItem(spin_name.getSelectedItemPosition()),
                                    birthday.getText().toString().trim(),
                                    number.getText().toString().trim(),
                                    CMND.getText().toString().trim(),
                                    adress.getText().toString().trim()
                            );
                            Toast.makeText(getActivity().getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    updateKHList();
                });
        builderss.show();
    }


    public void openDialogEdit(int id,String ngaysinh,String sdt,String cmnd,String diachi) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_khachhang, null);

        spin_name = view.findViewById(R.id.spin_name);
        birthday = view.findViewById(R.id.edt_birthday);
        number = view.findViewById(R.id.edt_number);
        CMND = view.findViewById(R.id.edt_cmnd);
        adress = view.findViewById(R.id.edt_adress);
        birthday.setText(ngaysinh);
        number.setText(sdt);
        CMND.setText(cmnd);
        adress.setText(diachi);

        ArrayList<String> name = new ArrayList();
        for (int i = 0; i < listHoadon.size(); i++) {
            final HoaDon hoaDon = listHoadon.get(i);
            name.add(hoaDon.getKhachmua());
        }
        ArrayAdapter<String> adapterName = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, name);
        spin_name.setAdapter(adapterName);
        builder.setView(view)
                .setNegativeButton("cancel", (dialogInterface, i) -> {

                })
                .setPositiveButton("ok", (dialogInterface, i) -> {
                    try {
                        HomeFragment.sqLiteHelper.updateKH(
                                adapterName.getItem(spin_name.getSelectedItemPosition()),
                                birthday.getText().toString().trim(),
                                number.getText().toString(),
                                CMND.getText().toString(),
                                adress.getText().toString().trim(),
                                id
                        );
                        Toast.makeText(getActivity().getApplicationContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    updateKHList();
                });
        builder.show();
    }

    private void showDialogDelete(int id) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getActivity());
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete");
        dialogDelete.setPositiveButton("OK", (dialogInterface, i) -> {
            try {
                HomeFragment.sqLiteHelper.deleteKH(id);
                Toast.makeText(getActivity().getApplicationContext(), "Delete successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("error", e.getMessage());
            }
            updateKHList();
        });
        dialogDelete.setNegativeButton("Cancel", (dialogInterface, i) -> {

        });
        dialogDelete.show();
    }

    private void updateKHList() {
        //get all data from sql
        Cursor cursor = HomeFragment.sqLiteHelper.getData("SELECT * FROM KhachHang");
        list1.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String birthday = cursor.getString(2);
            String number = cursor.getString(3);
            String CMND = cursor.getString(4);
            String adress = cursor.getString(5);
            list1.add(new KhachHang(id, name, birthday, number, CMND, adress));
        }
        adapter1.notifyDataSetChanged();
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
                adapter1.getFilter().filter(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void ListBill() {
        Cursor cursor = HomeFragment.sqLiteHelper.getData("SELECT * FROM HoaDon");
        listHoadon.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tensp = cursor.getString(1);
            String ngayban = cursor.getString(2);
            String khachmua = cursor.getString(3);
            String giamua = cursor.getString(4);
            String soluongmua = cursor.getString(5);
            String thanhtien = cursor.getString(6);
            listHoadon.add(new HoaDon(id, tensp, ngayban, khachmua, giamua, soluongmua, thanhtien));
        }
    }
    public class KhachHangListAdapter extends BaseAdapter implements Filterable {
        private Context context;
        private int layout;
        private List<KhachHang> khachHangList;
        private List<KhachHang> khachHangListsearch;

        public KhachHangListAdapter(Context context, int layout, List<KhachHang> khachHangList) {
            this.context = context;
            this.layout = layout;
            this.khachHangList = khachHangList;
            this.khachHangListsearch = khachHangList;
        }

        @Override
        public int getCount() {
            return khachHangList.size();
        }

        @Override
        public Object getItem(int i) {
            return khachHangList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }


        private class ViewHolder {
            TextView txtten, txtsdt;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View row = view;
            ViewHolder holder = new ViewHolder();
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);

                holder.txtten = (TextView) row.findViewById(R.id.txt_name);
                holder.txtsdt = (TextView) row.findViewById(R.id.txt_sdt);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }
            KhachHang khachHang = khachHangList.get(i);
            holder.txtten.setText(khachHang.getHoten());
            holder.txtsdt.setText(khachHang.getSodienthoai());
            row.setOnClickListener(new View.OnClickListener() {
                int id=khachHang.getID();
                String ten=khachHang.getHoten();
                String cmnd=khachHang.getSoCMND();
                String sdt=khachHang.getSodienthoai();
                String birthday=khachHang.getNgaysinh();
                String diachi=khachHang.getDiachi();
                @Override
                public void onClick(View v) {
                    openDialogView(id,ten,cmnd,sdt,birthday,diachi);
                    Toast.makeText(getActivity().getApplicationContext(), "Information", Toast.LENGTH_SHORT).show();
                }
            });
            row.setOnLongClickListener(v -> {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    int id=khachHang.getID();
                    String cmnd=khachHang.getSoCMND();
                    String sdt=khachHang.getSodienthoai();
                    String birthday=khachHang.getNgaysinh();
                    String diachi=khachHang.getDiachi();
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        if (item == 0) {
                            openDialogEdit(id,cmnd,sdt,birthday,diachi);
                            Toast.makeText(getActivity().getApplicationContext(), "Update", Toast.LENGTH_SHORT).show();
                        } else {

                            showDialogDelete(id);
                            Toast.makeText(getActivity().getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
                return true;
            });
            return row;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults filterResults = new FilterResults();
                    String search = charSequence.toString().toLowerCase();

                    if (search == null || search.length() == 0) {
                        filterResults.count = khachHangListsearch.size();
                        filterResults.values = khachHangListsearch;
                    } else {
                        String strSearch = charSequence.toString().toLowerCase().trim();
                        List<KhachHang> resultData = new ArrayList<>();
                        for (KhachHang khachHang : khachHangListsearch) {
                            if (khachHang.getHoten().toLowerCase().contains(strSearch) || khachHang.getSodienthoai().toLowerCase().contains(strSearch)) {
                                resultData.add(khachHang);
                            }
                        }
                        filterResults.count = resultData.size();
                        filterResults.values = resultData;
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    khachHangList = (List<KhachHang>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }
    }

}