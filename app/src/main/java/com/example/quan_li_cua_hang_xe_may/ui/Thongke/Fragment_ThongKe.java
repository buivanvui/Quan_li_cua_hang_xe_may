package com.example.quan_li_cua_hang_xe_may.ui.Thongke;


import android.os.Bundle;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import com.example.quan_li_cua_hang_xe_may.Adapter.thongke_adapter;
import com.example.quan_li_cua_hang_xe_may.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Fragment_ThongKe extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    thongke_adapter thongke_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongke, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        thongke_adapter = new thongke_adapter(getActivity());
        viewPager.setAdapter(thongke_adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Số lượng xe");
                    break;
                case 1:
                    tab.setText("DT ngày");
                    break;
                case 2:
                    tab.setText("DT tháng");
                    break;
                case 3:
                    tab.setText("DT năm");
                    break;
                case 4:
                    tab.setText("Biểu đồ");
                    break;
            }
        }).attach();
        return view;
    }
}

