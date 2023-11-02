package com.kano.btlnhom2.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.kano.btlnhom2.DAO.HoaDonDAO;
import com.kano.btlnhom2.DAO.HoaDonDichVuDAO;
import com.kano.btlnhom2.DAO.KhachHangDAO;
import com.kano.btlnhom2.DAO.LoaiDichVuDao;
import com.kano.btlnhom2.DAO.PhongDao;
import com.kano.btlnhom2.DTO.HoaDon;
import com.kano.btlnhom2.DTO.HoaDonDichVu;
import com.kano.btlnhom2.DTO.KhachHang;
import com.kano.btlnhom2.DTO.LoaiDichVu;
import com.kano.btlnhom2.DTO.Phong;
import com.kano.btlnhom2.R;

import java.util.ArrayList;


public class DonDichVuAdapter extends RecyclerView.Adapter<DonDichVuAdapter.HoaDonViewHolder>{
    private Context context;
    private ArrayList<HoaDonDichVu> list;
    private HoaDonDichVuDAO hoaDonDichVuDAO;

    public DonDichVuAdapter(Context context, ArrayList<HoaDonDichVu> list){
        this.context= context;
        this.list = list;
        hoaDonDichVuDAO = new HoaDonDichVuDAO(context);
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_don_dich_vu,parent,false);
        return new HoaDonViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {
        HoaDonDichVu hoaDonDichVu = list.get(position);
        holder.tv_ngayDatDV.setText("Ngày tạo hóa đơn: "+hoaDonDichVu.getService_date());

        HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
        HoaDon hoaDon = hoaDonDAO.getId(String.valueOf(hoaDonDichVu.getBill_id()));
        PhongDao phongDao = new PhongDao(context);
        Phong phong = phongDao.getID(String.valueOf(hoaDon.getRoom_id()));
        LoaiDichVuDao loaiDichVuDao = new LoaiDichVuDao(context);
        LoaiDichVu loaiDichVu = loaiDichVuDao.getID(String.valueOf(hoaDonDichVu.getService_id()));
        KhachHangDAO khachHangDAO = new KhachHangDAO(context);
        KhachHang khachHang = khachHangDAO.getID(String.valueOf(hoaDon.getGuest_id()));

        holder.tv_tenPhong.setText("Phòng: "+phong.getName());
        holder.tv_ten_khach.setText("Khách hàng: "+khachHang.getName());
        holder.tv_tuNgay.setText("Từ: "+hoaDon.getStart_date());
        holder.tv_denNgay.setText("đến ngày: "+hoaDon.getEnd_date());
        holder.tv_loaiDV.setText("Loại dịch vụ: "+loaiDichVu.getName());

        holder.tv_soLuong.setText("Số lượng dịch vụ: "+hoaDonDichVu.getService_quantity());
        holder.tv_tongTienDV.setText("Tổng tiền: "+hoaDonDichVu.getTotal()+" VNĐ");

        holder.btn_xoa_hddv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                        .setTitle("Bạn có muốn hủy đơn không")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (hoaDonDichVuDAO.delete(hoaDonDichVu.getId()) > 0) {
                                    Toast.makeText(context, "Hủy thành công", Toast.LENGTH_SHORT).show();
                                    list.clear();
                                    list.addAll(hoaDonDichVuDAO.getAll());
                                    notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, "Hủy thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("CANNEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HoaDonViewHolder extends RecyclerView.ViewHolder{
        TextView tv_ngayDatDV,tv_tenPhong,tv_loaiDV,tv_soLuong,tv_tongTienDV,tv_tuNgay,tv_denNgay,tv_ten_khach;
        ImageView btn_xoa_hddv;
        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ngayDatDV = itemView.findViewById(R.id.hddv_tv_ngay);
            tv_tenPhong = itemView.findViewById(R.id.hddv_tv_phong);
            tv_loaiDV = itemView.findViewById(R.id.hddv_tv_loai_dv);
            tv_soLuong = itemView.findViewById(R.id.hddv_tv_so_luong_dv);
            tv_tongTienDV = itemView.findViewById(R.id.hddv_tv_tong_tien);
            tv_tuNgay = itemView.findViewById(R.id.hddv_tv_tu_ngay);
            tv_denNgay = itemView.findViewById(R.id.hddv_tv_den_ngay);
            tv_ten_khach = itemView.findViewById(R.id.hddv_tv_ten_khach);
            btn_xoa_hddv = itemView.findViewById(R.id.btn_delete_hddv);
        }
    }
}
