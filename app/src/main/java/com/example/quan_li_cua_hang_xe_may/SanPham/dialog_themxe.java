package com.example.quan_li_cua_hang_xe_may.SanPham;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.quan_li_cua_hang_xe_may.MainActivity;
import com.example.quan_li_cua_hang_xe_may.R;
import com.example.quan_li_cua_hang_xe_may.Sql.HoaDon;
import com.example.quan_li_cua_hang_xe_may.Sql.Xe;
import com.example.quan_li_cua_hang_xe_may.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;


public class dialog_themxe extends AppCompatDialogFragment {
    private FloatingActionButton fab_add_image;
    private ImageView imgphto;
    Spinner spin_loaixe;
    EditText edt_tenxe, edt_gia, edt_mota, edt_soluong;
    static final String[] loaixe = new String[]{"xe ga", "xe so", "xe phan khoi", "xe dien"};

    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_themxe, null);

        edt_tenxe = view.findViewById(R.id.edt_tenxe);
        spin_loaixe = view.findViewById(R.id.spin_loaixe);
        edt_gia = view.findViewById(R.id.edt_gia);
        edt_mota = view.findViewById(R.id.edt_mota);
        edt_soluong = view.findViewById(R.id.edt_soluong);
        imgphto = view.findViewById(R.id.imagehinhanh);
        ArrayAdapter<String> adapterLoaixe = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, loaixe);
        spin_loaixe.setAdapter(adapterLoaixe);

        fab_add_image = view.findViewById(R.id.fab_addImage);
        fab_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePictrure();

            }
        });

        builder.setView(view)
                .setNegativeButton("cancel", (dialogInterface, i) -> {

                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    boolean add=true;
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            for (i = 0; i < HomeFragment.list.size(); i++) {
                                final Xe xe = HomeFragment.list.get(i);
                                if( edt_tenxe.getText().toString().trim().equalsIgnoreCase(xe.getTenxe())){
                                    Toast.makeText(getActivity(), "Trùng tên sản phẩm", Toast.LENGTH_SHORT).show();
                                    add=false;
                                }
                                else if (Arrays.equals(convertImagetobyteArray(imgphto),xe.getHinh())){
                                    Toast.makeText(getActivity(), "Hình ảnh giống nhau", Toast.LENGTH_SHORT).show();
                                    add=false;
                                }
                            }
                            if(add==false)
                            {
                                Toast.makeText(getActivity(), "Not succesfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                HomeFragment.sqLiteHelper.insertData(
                                        edt_tenxe.getText().toString().trim(),
                                        adapterLoaixe.getItem(spin_loaixe.getSelectedItemPosition()),
                                        edt_gia.getText().toString().trim(),
                                        edt_soluong.getText().toString().trim(),
                                        edt_mota.getText().toString().trim(),
                                        convertImagetobyteArray(imgphto)
                                );
                                        Toast.makeText(getActivity().getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        return builder.create();
    }

    private byte[] convertImagetobyteArray(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return byteArray;
    }

    private void takePictrure() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectImageUri = data.getData();
                    imgphto.setImageURI(selectImageUri);
                }
                break;
        }
    }
}
