package com.example.quan_li_cua_hang_xe_may.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quan_li_cua_hang_xe_may.ui.Thongke.DoanhThuNam_tab_fragment;
import com.example.quan_li_cua_hang_xe_may.ui.Thongke.DoanhThuNgay_tab_fragment;
import com.example.quan_li_cua_hang_xe_may.ui.Thongke.DoanhThuThang_tab_fragment;
import com.example.quan_li_cua_hang_xe_may.ui.Thongke.Doanhthu_barchart_fragment;
import com.example.quan_li_cua_hang_xe_may.ui.Thongke.SoluongXe_tab_fragment;

public class thongke_adapter extends FragmentStateAdapter {


    public thongke_adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SoluongXe_tab_fragment();
            case 1:
                return new DoanhThuNgay_tab_fragment();
            case 2:
                return new DoanhThuThang_tab_fragment();
            case 3:
                return new DoanhThuNam_tab_fragment();
            case 4:
                return new Doanhthu_barchart_fragment();
            default:
                return new SoluongXe_tab_fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
