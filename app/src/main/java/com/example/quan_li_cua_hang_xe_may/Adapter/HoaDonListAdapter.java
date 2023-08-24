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
import com.example.quan_li_cua_hang_xe_may.Sql.HoaDon;

import java.util.ArrayList;
import java.util.List;

public class HoaDonListAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private int layout;
    private List<HoaDon> hoaDonList;
    private List<HoaDon> hoaDonListSearch;

    public HoaDonListAdapter(Context context, int layout, List<HoaDon> hoaDonList) {
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
