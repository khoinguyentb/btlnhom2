package com.kano.btlnhom2.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.textfield.TextInputEditText;
import com.kano.btlnhom2.DAO.LoaiPhongDAO;
import com.kano.btlnhom2.DAO.PhongDao;
import com.kano.btlnhom2.DTO.LoaiPhong;
import com.kano.btlnhom2.DTO.Phong;
import com.kano.btlnhom2.Fragment.FragmentHoaDon;
import com.kano.btlnhom2.R;

import java.util.ArrayList;
import java.util.List;



public class PhongAdapter extends RecyclerView.Adapter<PhongAdapter.phongViewHolder> {
    private Context context;
    private List<Phong> list;
    private PhongDao phongDAO;
    private ViewPager viewPager;

    private LoaiPhong loaiphong;
    private LoaiPhongDAO loaiphongDAO;

    public PhongAdapter(Context context, List<Phong> list) {
        this.context = context;
        this.list = list;
        phongDAO=new PhongDao(context);
        loaiphongDAO=new LoaiPhongDAO(context);
    }
    @NonNull
    @Override
    public phongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phong, parent, false);
        return new phongViewHolder(view);
    }
    private TextInputEditText dialog_ed_tenphong,dialog_ed_giaphong;
    private Spinner dialog_spn_loaiphong;
    Button dialog_btn_themphong,dialog_btn_huythemphong;
    SpinnerLoaiPhongAdapter spinnerLoaiPhongAdapter;

    int loaiPhong;
    List<LoaiPhong> listLoaiPhong;


    static final String TAG = "zzzz";

    FragmentHoaDon fragmentHoaDon = new FragmentHoaDon();


    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull phongViewHolder holder, int position) {
        Phong phong=list.get(position);
        holder.item_phong_ten.setText("Phòng: "+phong.getName());

        loaiphongDAO =new LoaiPhongDAO(context);
        listLoaiPhong = loaiphongDAO.getAll();
        try {
            loaiphong = loaiphongDAO.getID(String.valueOf(phong.getRoom_type_id()));
        }catch (Exception e){
            Log.d("zzzz", "onBindViewHolder phong adapter");
        }
        holder.item_phong_loaiphong.setText( context.getString(R.string.tv_loai_phong)+" : \n"+loaiphong.getName());

        holder.item_phong_giaphong.setText( context.getString(R.string.tv_gia_phong)+"  \n"+phong.getPrice()+" VNĐ");

        PhongDao dao = new PhongDao( context);
        holder. imageView. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Bạn có chắc muốn xóa ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int check = dao.delete(list.get(holder.getAdapterPosition()).getId());
                        switch (check){
                            case  1 :
                                list.clear();
                                list.addAll(dao.getAll());
                                notifyDataSetChanged();
                                Toast.makeText(context,"Xóa thành công",Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context,"Không thể xóa vì có khách hàng trong hóa đơn",Toast.LENGTH_SHORT).show();
                                break;
                            case 0 :
                                Toast.makeText(context,"Xóa không thành công",Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }

                    }
                });
                builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });

//        holder.btn_datphong.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    fragmentHoaDon.openDialogHN(context,0);
//                }catch (Exception e){
//                    Log.d(TAG, "onClick dat phong: error "+ e);
//                }
//            }
//        });




        holder.item_phong_ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_them_phong, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                Window window = dialog.getWindow();
                if(window==null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog_ed_tenphong=dialog.findViewById(R.id.dialog_ed_tenphong);
                dialog_ed_tenphong.setText(phong.getName());
                dialog_ed_giaphong=dialog.findViewById(R.id.dialog_ed_giaphong);
                dialog_ed_giaphong.setText(phong.getPrice()+"");
                dialog_spn_loaiphong=dialog.findViewById(R.id.dialog_spn_loaiphong);

                dialog_btn_themphong=dialog.findViewById(R.id.dialog_btn_themphong);
                dialog_btn_huythemphong=dialog.findViewById(R.id.dialog_btn_huythemphong);

                spinnerLoaiPhongAdapter = new SpinnerLoaiPhongAdapter(context, (ArrayList<LoaiPhong>) loaiphongDAO.getAll());
                dialog_spn_loaiphong.setAdapter(spinnerLoaiPhongAdapter);
                for (int i=0;i<dialog_spn_loaiphong.getCount();i++){
                    listLoaiPhong = loaiphongDAO.getAll();
                    if (list.get(position).getRoom_type_id() == listLoaiPhong.get(i).getId()){
                        dialog_spn_loaiphong.setSelection(i);
                    }
                }

                dialog_spn_loaiphong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        listLoaiPhong = new ArrayList<>();
                        loaiphongDAO = new LoaiPhongDAO(context);
                        listLoaiPhong = loaiphongDAO.getAll();
                        loaiPhong = listLoaiPhong.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                dialog_btn_themphong.setText(context.getString(R.string.btn_cap_nhat));
                dialog_btn_themphong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog_ed_tenphong.getText().length() == 0) {
                            Toast.makeText(context, context.getString(R.string.toast_value_date), Toast.LENGTH_SHORT).show();
                        } else {

                            phong.setName(dialog_ed_tenphong.getText().toString());
                            phong.setRoom_type_id(loaiPhong);
                            phong.setPrice(Integer.parseInt(dialog_ed_giaphong.getText().toString()));
                            phong.setStatus(0);
                            if (dao.update(phong) > 0) {
                                Toast.makeText(context, context.getString(R.string.toast_cap_nhat_thanh_cong), Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(dao.getAll());
                                notifyDataSetChanged();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, context.getString(R.string.toast_cap_nhat_khong_thanh_cong), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                dialog_btn_huythemphong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class phongViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView item_phong_ten,item_phong_loaiphong,item_phong_giaphong,item_phong_trangthai;
        Button btn_datphong;
        public phongViewHolder(@NonNull View itemView) {
            super(itemView);
            item_phong_ten=itemView.findViewById(R.id.item_phong_ten);
            item_phong_giaphong=itemView.findViewById(R.id.item_phong_giaphong);
            item_phong_loaiphong=itemView.findViewById(R.id.item_phong_loaiphong);
            imageView = itemView.findViewById(R.id.id_delete);
//            btn_datphong=itemView.findViewById(R.id.btn_datphong);
        }
    }
}

