package com.example.quan_li_cua_hang_xe_may.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.quan_li_cua_hang_xe_may.R;
import com.example.quan_li_cua_hang_xe_may.Sql.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class NhanvienListAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private int layout;
    private List<NhanVien> nhanVienList;
    private List<NhanVien> nhanVienListsearch;

    public NhanvienListAdapter(Context context, int layout, List<NhanVien> nhanVienList) {
        this.context = context;
        this.layout = layout;
        this.nhanVienList = nhanVienList;
        this.nhanVienListsearch = nhanVienList;
    }

    @Override
    public int getCount() {
        return nhanVienList.size();
    }

    @Override
    public Object getItem(int i) {
        return nhanVienList.get(i);
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

            holder.txtten = (TextView) row.findViewById(R.id.txt_ten);
            holder.txtsdt = (TextView) row.findViewById(R.id.txt_sodienthoai);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        NhanVien nhanVien = nhanVienList.get(i);
        holder.txtten.setText(nhanVien.getHoten());
        holder.txtsdt.setText(nhanVien.getSodienthoai());

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
                    filterResults.count = nhanVienListsearch.size();
                    filterResults.values = nhanVienListsearch;
                } else {
                    String strSearch = charSequence.toString().toLowerCase().trim();
                    List<NhanVien> resultData = new ArrayList<>();
                    for (NhanVien nhanVien : nhanVienListsearch) {
                        if (nhanVien.getHoten().toLowerCase().contains(strSearch) || nhanVien.getSodienthoai().toLowerCase().contains(strSearch)) {
                            resultData.add(nhanVien);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                nhanVienList = (List<NhanVien>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
