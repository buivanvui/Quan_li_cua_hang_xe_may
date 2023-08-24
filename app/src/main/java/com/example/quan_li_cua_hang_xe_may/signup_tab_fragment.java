package com.example.quan_li_cua_hang_xe_may;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quan_li_cua_hang_xe_may.CSDL.database;


public class signup_tab_fragment extends Fragment {
    EditText edt_taikhoan, edt_matkhau, edt_nhaplaimatkhau;
    Button btn_dangky;
    public static database database;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        database = new database(getActivity());

        edt_taikhoan = root.findViewById(R.id.edit_taikhoan_dangky);
        edt_nhaplaimatkhau = root.findViewById(R.id.edit_nhaplaimatkhau_dangky);
        edt_matkhau = root.findViewById(R.id.edit_nhapmatkhau_dangky);

        btn_dangky = root.findViewById(R.id.btn_dangky);
        eventOnclick();
        return root;
    }


    private void eventOnclick() {
        //sự kiện click đăng ký
        btn_dangky.setOnClickListener(v -> {
            String taikhoan = edt_taikhoan.getText().toString();
            String matkhau = edt_matkhau.getText().toString();
            String repass = edt_nhaplaimatkhau.getText().toString();
            if (TextUtils.isEmpty(taikhoan) || TextUtils.isEmpty(matkhau) || TextUtils.isEmpty(repass))
                Toast.makeText(getActivity().getApplicationContext(), "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            else {
                if (matkhau.equals(repass)) {
                    Boolean checkuser = database.checkusername(taikhoan);
                    if (checkuser == false) {
                        Boolean insert = database.insert(taikhoan, matkhau);
                        if (insert == true) {
                            Toast.makeText(getActivity().getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Đã tồn tại tên tài khoản", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Mật khẩu không khớp nhau", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

}
