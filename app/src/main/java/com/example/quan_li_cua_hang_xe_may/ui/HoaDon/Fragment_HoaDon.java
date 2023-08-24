package com.example.quan_li_cua_hang_xe_may.ui.HoaDon;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.quan_li_cua_hang_xe_may.R;
import com.example.quan_li_cua_hang_xe_may.Sql.HoaDon;
import com.example.quan_li_cua_hang_xe_may.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class Fragment_HoaDon extends Fragment {
    private GridView gridView2;
    public static ArrayList<HoaDon> list2;
    HoaDonListAdapter adapter2;

    private SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoadon, container, false);

        setHasOptionsMenu(true);

        HomeFragment.sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS HoaDon (ID INTEGER PRIMARY KEY AUTOINCREMENT,tensp VARCHAR,ngaymua VARCHAR,khachmua VARCHAR,giamua VARCHAR,soluongmua VARCHAR,thanhtien VARCHAR)");

        gridView2 = view.findViewById(R.id.gribview_hoadon);
        list2 = new ArrayList<>();
        adapter2 = new HoaDonListAdapter(getActivity(), R.layout.layout_hoadon, list2);
        gridView2.setAdapter(adapter2);
        updateBillList();

        return view;
    }

    public void openView(int MaHD,String tensp,String ngaymuasp,String giamuasp,String tenkhachmua,String soluongmua,String thanhtiensp) {

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


    private void showDialogDelete(int id) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getActivity());
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    HomeFragment.sqLiteHelper.deleteBill(id);
                    Toast.makeText(getActivity().getApplicationContext(), "Delete successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                updateBillList();
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogDelete.show();
    }

    private void updateBillList() {
        Cursor cursor = HomeFragment.sqLiteHelper.getData("SELECT * FROM HoaDon");
        list2.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tensp = cursor.getString(1);
            String ngayban = cursor.getString(2);
            String khachmua = cursor.getString(3);
            String giamua = cursor.getString(4);
            String soluongmua = cursor.getString(5);
            String thanhtien = cursor.getString(6);
            list2.add(new HoaDon(id, tensp, ngayban, khachmua, giamua, soluongmua, thanhtien));
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
    public class HoaDonListAdapter extends BaseAdapter implements Filterable {
        private Context context;
        private int layout;
        private List<HoaDon> hoaDonList;
        private List<HoaDon> hoaDonListSearch;

        public  HoaDonListAdapter(Context context, int layout, List<HoaDon> hoaDonList) {
            this.context = context;
            this.layout = layout;
            this.hoaDonList = hoaDonList;
            this.hoaDonListSearch = hoaDonList;
        }

        @Override
        public int getCount() {
            return hoaDonList.size();
        }

        @Override
        public Object getItem(int i) {
            return hoaDonList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }


        private class ViewHolder {
            TextView txtten, txtday;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View row = view;
            ViewHolder holder = new ViewHolder();
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);

                holder.txtten = (TextView) row.findViewById(R.id.txt_name123);
                holder.txtday = (TextView) row.findViewById(R.id.txt_ngaymua);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }
            HoaDon hoaDon = hoaDonList.get(i);
            holder.txtten.setText(hoaDon.getTensp());
            holder.txtday.setText(hoaDon.getNgaymua());
           row.setOnClickListener(new View.OnClickListener() {
                int MaHD=hoaDon.getID();
                String tensp=hoaDon.getTensp();
                String ngaymuasp=hoaDon.getNgaymua();
                String giamuasp=hoaDon.getGiamua();
                String tenkhachmua=hoaDon.getKhachmua();
                String soluongmua=hoaDon.getSoluong();
                String thanhtiensp=hoaDon.getThanhtien();
                @Override
                public void onClick(View v) {
                    openView(MaHD,tensp,ngaymuasp,giamuasp,tenkhachmua,soluongmua,thanhtiensp);
                    Toast.makeText(getActivity().getApplicationContext(), "Information", Toast.LENGTH_SHORT).show();
                }
            });
           row.setOnLongClickListener(v ->{
                CharSequence[] items = {"Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    int MaHD=hoaDon.getID();
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        if (item == 0) {
                            showDialogDelete(MaHD);
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
                        filterResults.count = hoaDonListSearch.size();
                        filterResults.values = hoaDonListSearch;
                    } else {
                        String strSearch = charSequence.toString().toLowerCase().trim();
                        List<HoaDon> resultData = new ArrayList<>();
                        for (HoaDon hoaDon : hoaDonListSearch) {
                            if (hoaDon.getTensp().toLowerCase().contains(strSearch)) {
                                resultData.add(hoaDon);
                            }
                        }
                        filterResults.count = resultData.size();
                        filterResults.values = resultData;
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    hoaDonList = (List<HoaDon>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }
    }

}