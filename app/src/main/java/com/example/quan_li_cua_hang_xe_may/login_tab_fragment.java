package com.example.quan_li_cua_hang_xe_may;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;



public class login_tab_fragment extends Fragment {
    EditText edt_taikhoan, edt_matkhau;
    Button btn_dangnhap;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);


        edt_taikhoan = root.findViewById(R.id.edit_taikhoan_dangnhap);
        edt_matkhau = root.findViewById(R.id.edit_matkhau_dangnhap);
        btn_dangnhap = root.findViewById(R.id.btn_dangnhap);

        eventOnClick();
        return root;
    }


    private void eventOnClick() {

        //khi click vào nút đăng nhập
        btn_dangnhap.setOnClickListener(v -> {
            String taikhoan = edt_taikhoan.getText().toString();
            String matkhau = edt_matkhau.getText().toString();
            if (TextUtils.isEmpty(taikhoan) || TextUtils.isEmpty(matkhau))
                Toast.makeText(getActivity().getApplicationContext(), "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            else {
                Boolean checkuserpass = signup_tab_fragment.database.checkusernamepassword(taikhoan, matkhau);
                if (checkuserpass == true) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getActivity().getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
