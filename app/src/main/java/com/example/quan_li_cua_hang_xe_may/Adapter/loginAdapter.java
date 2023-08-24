package com.example.quan_li_cua_hang_xe_may.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quan_li_cua_hang_xe_may.login_tab_fragment;
import com.example.quan_li_cua_hang_xe_may.signup_tab_fragment;

public class loginAdapter extends FragmentStateAdapter {


    public loginAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new login_tab_fragment();
            case 1:
                return new signup_tab_fragment();
            default:
                return new login_tab_fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
