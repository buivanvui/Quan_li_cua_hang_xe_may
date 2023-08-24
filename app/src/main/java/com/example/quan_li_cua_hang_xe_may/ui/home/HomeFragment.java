package com.example.quan_li_cua_hang_xe_may.ui.home;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.quan_li_cua_hang_xe_may.R;
import com.example.quan_li_cua_hang_xe_may.SanPham.dialog_themxe;
import com.example.quan_li_cua_hang_xe_may.Sql.SQLiteHelper;
import com.example.quan_li_cua_hang_xe_may.Sql.Xe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    ImageButton ib_all, ib_xega, ib_xeso, ib_xe_phankhoi, ib_xedien;
    FloatingActionButton fab_add, fab_add_image;
    public static GridView gridView;
    public static ArrayList<Xe> list;
    private ImageView imgphto1, pay,imgphto2;
    private ViewFlipper viewFlipper;
    Spinner spin_loaixe;
    EditText edt_tenxe1, edt_gia1, edt_soluong1, edt_mota1, ten, ngaymua, tenkhach, giamua, soluong;
    TextView thanhtien,txt_id, txt_ten, txt_loai, txt_gia, txt_mota, txt_sl;
    public static SearchView searchView;
    XelistAdapter adapter;
    public static SQLiteHelper sqLiteHelper;
    static final String[] loaixe = new String[]{"xe ga", "xe so", "xe phan khoi", "xe dien"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        int images[] = {R.drawable.honda_hot, R.drawable.yamaha_hot, R.drawable.sirius_hot, R.drawable.exciter_hot, R.drawable.wave_hot};
        viewFlipper = view.findViewById(R.id.viewFLipper);
        for (int image : images) {
            flipperImage(image);
        }

        sqLiteHelper = new SQLiteHelper(getActivity(), "DB_shopMotor", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS XE (Id INTEGER PRIMARY KEY AUTOINCREMENT,tenxe VARCHAR,loaixe VARCHAR,gia VARCHAR,soluong VARCHAR,mota VARCHAR,image BLOG)");

        gridView = view.findViewById(R.id.gribview_xe);
        list = new ArrayList<>();
        adapter = new XelistAdapter(getActivity(), R.layout.layout_sanpham, list);
        gridView.setAdapter(adapter);

        updateXeList();

        fab_add = view.findViewById(R.id.fab_add);
        fab_add.setOnClickListener(v -> openDialogthem());
        ib_all = view.findViewById(R.id.ib_All_xe);
        ib_all.setOnClickListener(v -> {
            ib_all.setBackgroundResource(R.drawable.click_all);
            ib_xedien.setBackgroundResource(R.drawable.electric_scooter);
            ib_xega.setBackgroundResource(R.drawable.motorcycle);
            ib_xe_phankhoi.setBackgroundResource(R.drawable.motorbike);
            ib_xeso.setBackgroundResource(R.drawable.xeso);
            adapter.getFilter().filter("");
        });
        ib_xega =view.findViewById(R.id.ib_xe_ga);
        ib_xega.setOnClickListener(v -> {
            ib_xega.setBackgroundResource(R.drawable.motorcycle_click);
            ib_all.setBackgroundResource(R.drawable.all);
            ib_xe_phankhoi.setBackgroundResource(R.drawable.motorbike);
            ib_xeso.setBackgroundResource(R.drawable.xeso);
            ib_xedien.setBackgroundResource(R.drawable.electric_scooter);
            adapter.getFilter().filter("xe ga");

        });
        ib_xe_phankhoi =view.findViewById(R.id.ib_xe_phankhoilon);
        ib_xe_phankhoi.setOnClickListener(v -> {
            ib_xe_phankhoi.setBackgroundResource(R.drawable.motorbike_click);
            ib_xega.setBackgroundResource(R.drawable.motorcycle);
            ib_all.setBackgroundResource(R.drawable.all);
            ib_xeso.setBackgroundResource(R.drawable.xeso);
            ib_xedien.setBackgroundResource(R.drawable.electric_scooter);
            adapter.getFilter().filter("xe phan khoi");
        });
        ib_xeso = view.findViewById(R.id.ib_xe_so);
        ib_xeso.setOnClickListener(v -> {
            ib_xeso.setBackgroundResource(R.drawable.xeso_click);
            ib_xega.setBackgroundResource(R.drawable.motorcycle);
            ib_all.setBackgroundResource(R.drawable.all);
            ib_xe_phankhoi.setBackgroundResource(R.drawable.motorbike);
            ib_xedien.setBackgroundResource(R.drawable.electric_scooter);
            adapter.getFilter().filter("xe so");
        });
        ib_xedien = view.findViewById(R.id.ib_xe_dien);
        ib_xedien.setOnClickListener(v -> {
            ib_xedien.setBackgroundResource(R.drawable.electric_scooter_click);
            ib_xega.setBackgroundResource(R.drawable.motorcycle);
            ib_all.setBackgroundResource(R.drawable.all);
            ib_xe_phankhoi.setBackgroundResource(R.drawable.motorbike);
            ib_xeso.setBackgroundResource(R.drawable.xeso);
            adapter.getFilter().filter("xe dien");
        });
        return view;
    }

    private void openDialogPay(String tenxe, String loai, String giaxe, String sl, String mota, byte[] image, int id) {
        androidx.appcompat.app.AlertDialog.Builder builderss = new androidx.appcompat.app.AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_thanhtoan, null);

        ten = view.findViewById(R.id.edt_ten);
        ngaymua = view.findViewById(R.id.edt_ngayban);
        tenkhach = view.findViewById(R.id.edt_khachmua);
        giamua = view.findViewById(R.id.edt_giamua);
        soluong = view.findViewById(R.id.edt_sl);
        pay = view.findViewById(R.id.img_pay);
        thanhtien = view.findViewById(R.id.txt_thanhtien);
        pay.setOnClickListener(v -> {
            if (TextUtils.isEmpty(soluong.getText().toString()) || TextUtils.isEmpty(tenkhach.getText().toString())) {
                Toast.makeText(getActivity().getApplicationContext(), "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                if (Integer.parseInt(soluong.getText().toString()) > Integer.parseInt(sl)) {
                    soluong.setText("");
                    Toast.makeText(getActivity().getApplicationContext(), "Số lượng yêu cầu quá lớn", Toast.LENGTH_SHORT).show();
                } else {
                    long tinhtien = 0;
                    String str =giamua.getText().toString().replaceAll(",","");
                    tinhtien = Long.parseLong(str) * Long.parseLong(soluong.getText().toString());
                    DecimalFormat formatter = new DecimalFormat("###,###,###");
                    String str1 = formatter.format(tinhtien);
                    thanhtien.setText(str1);
                }
            }
        });

        String ngaygio;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        ngaygio = simpleDateFormat.format(date);

        ten.setText(tenxe);
        ngaymua.setText(ngaygio);
        giamua.setText(giaxe);
        builderss.setView(view)
                .setNegativeButton("cancel", (dialogInterface, i) -> {

                })
                .setPositiveButton("ok", (dialogInterface, i) -> {
                    try {
                        if (TextUtils.isEmpty(thanhtien.getText().toString())) {
                            Toast.makeText(getActivity().getApplicationContext(), "Not successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            sqLiteHelper.insertBill(
                                    ten.getText().toString().trim(),
                                    ngaymua.getText().toString().trim(),
                                    tenkhach.getText().toString().trim(),
                                    giamua.getText().toString().trim(),
                                    soluong.getText().toString().trim(),
                                    thanhtien.getText().toString().trim()
                            );
                            int kq = 0;
                            kq = Integer.parseInt(sl) - Integer.parseInt(soluong.getText().toString());
                            String a = String.valueOf(kq);
                            sqLiteHelper.updateData(
                                    tenxe,
                                    loai,
                                    giaxe,
                                    a,
                                    mota,
                                    image,
                                    id
                            );
                            Toast.makeText(getActivity().getApplicationContext(), "Pay successfully", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    updateXeList();
                });
        builderss.show();
    }

    public void flipperImage(int image) {
        ImageView imageView = new ImageView(this.getActivity());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this.getActivity(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this.getActivity(), android.R.anim.slide_out_right);
    }

    public void openDialogthem() {
        dialog_themxe dialog = new dialog_themxe();
        dialog.show(getActivity().getSupportFragmentManager(), " ");
    }

    public void openDialogView(int id,String tenxe,String loai,String gia,String sl,String mota,byte[] hinh) {


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_viewxe, null);

        txt_id = view.findViewById(R.id.txt_id);
        txt_ten = view.findViewById(R.id.txt_tenxe);
        txt_gia = view.findViewById(R.id.txt_gia);
        txt_loai = view.findViewById(R.id.txt_loaixe);
        txt_mota = view.findViewById(R.id.txt_mota);
        txt_sl=view.findViewById(R.id.txt_sl);
        imgphto2 = view.findViewById(R.id.imagehinhanh2);
        txt_id.setText(String.valueOf(id));
        txt_ten.setText(tenxe);
        txt_gia.setText(gia);
        txt_loai.setText(loai);
        txt_sl.setText(sl);
        txt_mota.setText(mota);
        byte[] image= hinh;
        Bitmap bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
        imgphto2.setImageBitmap(bitmap);
        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        builder.show();
    }
    public void openDialogEdit(String ten,String gia,String sl,String mota,int id) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this.getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog_suaxe, null);

        edt_tenxe1 = view.findViewById(R.id.edt_ten);
        spin_loaixe = view.findViewById(R.id.spin_loaixe);
        edt_gia1 = view.findViewById(R.id.edt_price);
        edt_mota1 = view.findViewById(R.id.edt_mta);
        edt_soluong1 = view.findViewById(R.id.edt_soluong);
        imgphto1 = view.findViewById(R.id.image);
        edt_tenxe1.setText(ten);
        edt_gia1.setText(gia);
        edt_soluong1.setText(sl);
        edt_mota1.setText(mota);

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
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            sqLiteHelper.updateData(
                                    edt_tenxe1.getText().toString().trim(),
                                    adapterLoaixe.getItem(spin_loaixe.getSelectedItemPosition()),
                                    edt_gia1.getText().toString().trim(),
                                    edt_soluong1.getText().toString(),
                                    edt_mota1.getText().toString().trim(),
                                    convertImagetobyteArray(imgphto1),
                                    id
                            );
                            Toast.makeText(getActivity().getApplicationContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        updateXeList();
                    }
                });
        builder.show();
    }

    private void showDialogDelete(int idXe) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getActivity());
        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    sqLiteHelper.deleteData(idXe);
                    Toast.makeText(getActivity().getApplicationContext(), "Delete successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                updateXeList();
            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogDelete.show();
    }

    private void updateXeList() {
        //get all data from sql
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM XE");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenxe = cursor.getString(1);
            String loaixe = cursor.getString(2);
            String gia = cursor.getString(3);
            String soluong = cursor.getString(4);
            String mota = cursor.getString(5);
            byte[] image = cursor.getBlob(6);
            list.add(new Xe(id, tenxe, loaixe, gia, soluong, mota, image));
        }
        adapter.notifyDataSetChanged();
    }
    public class XelistAdapter extends BaseAdapter implements Filterable {
        private Context context;
        private int layout;
        private List<Xe> xeList;
        private List<Xe> xelistSearch;

        public XelistAdapter(Context context, int layout, List<Xe> xeList) {
            this.context = context;
            this.layout = layout;
            this.xeList = xeList;
            this.xelistSearch = xeList;
        }

        @Override
        public int getCount() {
            return xeList.size();
        }

        @Override
        public Object getItem(int i) {
            return xeList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }


        private class ViewHolder {
            ImageView imageView;
            TextView txtten, txtgia, txtsoluong;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View row = view;
            ViewHolder holder = new ViewHolder();
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);

                holder.txtten = (TextView) row.findViewById(R.id.name);
                holder.txtgia = (TextView) row.findViewById(R.id.mota);
                holder.txtsoluong = (TextView) row.findViewById(R.id.soluong);
                holder.imageView = (ImageView) row.findViewById(R.id.imagehinh);
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }
            Xe xe = xeList.get(i);
            holder.txtten.setText(xe.getTenxe());
            holder.txtgia.setText(xe.getGia());
            holder.txtsoluong.setText(xe.getSoluong());
            byte[] image = xe.getHinh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            holder.imageView.setImageBitmap(bitmap);
            row.setOnClickListener(new View.OnClickListener() {
                int Ma=xe.getId();
                String ten=xe.getTenxe();
                String loaixe=xe.getLoaixe();
                String gia=xe.getGia();
                String soluong=xe.getSoluong();
                String mota=xe.getMota();
                byte[] image = xe.getHinh();
                @Override
                public void onClick(View v) {
                    openDialogPay(ten,loaixe,gia,soluong,mota,image,Ma);
                    Toast.makeText(getActivity().getApplicationContext(), "Pay", Toast.LENGTH_SHORT).show();
                }
            });
            row.setOnLongClickListener(v -> {
                CharSequence[] items = {"View", "Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    int Ma=xe.getId();
                    String ten=xe.getTenxe();
                    String loaixe=xe.getLoaixe();
                    String gia=xe.getGia();
                    String soluong=xe.getSoluong();
                    String mota=xe.getMota();
                    byte[] image = xe.getHinh();
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        if (item == 0) {
                            openDialogView(Ma,ten,loaixe,gia,soluong,mota,image);
                            Toast.makeText(getActivity().getApplicationContext(), "Information", Toast.LENGTH_SHORT).show();
                        } else if (item == 1) {
                            //update
                           openDialogEdit(ten,gia,soluong,mota,Ma);
                            Toast.makeText(getActivity().getApplicationContext(), "Update", Toast.LENGTH_SHORT).show();
                        } else {
                            //delete
                            showDialogDelete(Ma);
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
                        filterResults.count = xelistSearch.size();
                        filterResults.values = xelistSearch;
                    } else {
                        String strSearch = charSequence.toString().toLowerCase().trim();
                        List<Xe> resultData = new ArrayList<>();
                        for (Xe xe : xelistSearch) {
                            if (xe.getTenxe().toLowerCase().contains(strSearch) || xe.getLoaixe().toLowerCase().contains(strSearch)) {
                                resultData.add(xe);
                            }
                        }
                        filterResults.count = resultData.size();
                        filterResults.values = resultData;
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    xeList = (List<Xe>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }


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
                    imgphto1.setImageURI(selectImageUri);
                }
                break;
        }
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
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
