package com.example.quan_li_cua_hang_xe_may;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.quan_li_cua_hang_xe_may.Adapter.loginAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class login extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    loginAdapter loginAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

    }

    private void init() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        loginAdapter = new loginAdapter(this);
        viewPager.setAdapter(loginAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("LOGIN");
                    break;
                case 1:
                    tab.setText("SIGNUP");
                    break;
            }
        }).attach();

    }
}