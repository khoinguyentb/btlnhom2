package com.kano.btlnhom2.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kano.btlnhom2.DAO.HoaDonDAO;
import com.kano.btlnhom2.DAO.KhachHangDAO;
import com.kano.btlnhom2.DAO.PhongDao;
import com.kano.btlnhom2.DTO.HoaDon;
import com.kano.btlnhom2.DTO.KhachHang;
import com.kano.btlnhom2.DTO.Phong;
import com.kano.btlnhom2.R;
import com.kano.btlnhom2.click_interface.HoaDonClick;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder>{
    private Context context;
    private ArrayList<HoaDon> arrayList;
    private HoaDonDAO hoaDonDAO;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private HoaDonClick hoaDonClick;
    public void setHoaDonClick(HoaDonClick hoaDonClick) {
        this.hoaDonClick = hoaDonClick;
    }

    public HoaDonAdapter(Context context){
        this.context= context;
        hoaDonDAO = new HoaDonDAO(context);
    }

    public void setData(ArrayList<HoaDon> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_hoadon,parent,false);
        return new HoaDonViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {
        HoaDon hoaDon = arrayList.get(position);

        String datetime = sdf.format(c.getTime());

        KhachHangDAO khachHangDAO = new KhachHangDAO(context);
        KhachHang khachHang = khachHangDAO.getID(String.valueOf(hoaDon.getGuest_id()));
        holder.tv_tenKhach.setText("Tên khách hàng: "+khachHang.getName());

        PhongDao phongDao = new PhongDao(context);
        Phong phong = phongDao.getID(String.valueOf(hoaDon.getRoom_id()));
        holder.tv_tenPhong.setText("Phòng: "+phong.getName());

        holder.tv_tienPhong.setText("Tiền phòng: "+hoaDon.getRoom_total()+" VNĐ");
        holder.tv_ngayBD.setText("Từ: "+ hoaDon.getStart_date());
        holder.tv_ngayKT.setText("Đến: "+hoaDon.getEnd_date());
        holder.tv_ngayHD.setText("Ngày tạo hóa đơn: "+hoaDon.getBill_date());

        holder.tv_tienMat.setText("Tiền đền bù: \n"+hoaDon.getLost_total()+" VNĐ");
        holder.tv_tienDV.setText("Tiền dịch vụ: \n"+hoaDon.getService_total()+" VNĐ");
        holder.tv_ghiChu.setText("Ghi chú: "+hoaDon.getNote());
        holder.tv_tongTien.setText("Tổng tiền: "+hoaDon.getBill_total()+" VNĐ");

        if (hoaDon.getStatus()==1){
            holder.tv_trangThai.setVisibility(View.VISIBLE);
            holder.btn_check_in.setVisibility(View.GONE);
            holder.tv_trangThai.setText("Đã trả phòng");
            holder.tv_trangThai.setTextColor(Color.GREEN);
        }else if (hoaDon.getStatus()==0){
            holder.tv_trangThai.setVisibility(View.VISIBLE);
            holder.btn_check_in.setVisibility(View.GONE);
            holder.tv_trangThai.setText("Chưa Trả Phòng");
            holder.tv_trangThai.setTextColor(Color.RED);
        }

        holder.btn_check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("zzz", "onClick: ngày "+datetime);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận nhận phòng")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (datetime.equals(hoaDon.getStart_date())){
                                    hoaDon.setId(hoaDon.getId());
                                    hoaDon.setManager_id(hoaDon.getManager_id());
                                    hoaDon.setGuest_id(hoaDon.getGuest_id());
                                    hoaDon.setRoom_id(hoaDon.getRoom_id());
                                    hoaDon.setStart_date(hoaDon.getStart_date());
                                    hoaDon.setEnd_date(hoaDon.getEnd_date());
                                    hoaDon.setService_total(hoaDon.getService_total());
                                    hoaDon.setRoom_total(hoaDon.getRoom_total());
                                    hoaDon.setLost_total(hoaDon.getLost_total());
                                    hoaDon.setStatus(0);
                                    hoaDon.setNote(hoaDon.getNote());
                                    hoaDon.setBill_date(hoaDon.getBill_date());
                                    hoaDon.setBill_total(hoaDon.getBill_total());

                                    if (hoaDonDAO.update(hoaDon) > 0) {
                                        Toast.makeText(context, "Nhận phòng thành công", Toast.LENGTH_LONG).show();
                                        arrayList.clear();
                                        arrayList.addAll(hoaDonDAO.getAllstatus3());
                                        notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(context, "Lỗi nhận phòng", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                Toast.makeText(context,"Chưa đến ngày đặt phòng",Toast.LENGTH_LONG).show();

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
        return arrayList.size();
    }

    public class HoaDonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_tenPhong,tv_tenKhach,tv_ngayBD,tv_ngayKT,tv_ngayHD,
                tv_trangThai,tv_tienMat,tv_tienDV,tv_tienPhong,tv_ghiChu,tv_tongTien;
        Button btn_check_in;
        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenPhong = itemView.findViewById(R.id.tv_bill_room);
            tv_tenKhach = itemView.findViewById(R.id.tv_bill_guest);
            tv_ngayBD = itemView.findViewById(R.id.tv_bill_start_date);
            tv_ngayKT = itemView.findViewById(R.id.tv_bill_end_date);
            tv_ngayHD = itemView.findViewById(R.id.tv_bill_date);
            tv_trangThai = itemView.findViewById(R.id.tv_bill_status);
            tv_tienPhong = itemView.findViewById(R.id.tv_room_total);
            tv_tienMat = itemView.findViewById(R.id.tv_bill_lost_total);
            tv_tienDV = itemView.findViewById(R.id.tv_bill_service_total);
            tv_ghiChu = itemView.findViewById(R.id.tv_bill_note);
            tv_tongTien = itemView.findViewById(R.id.tv_bill_total);
            btn_check_in = itemView.findViewById(R.id.btn_check_in);
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            try {
                hoaDonClick.onClick(v,getAdapterPosition());
            }catch (Exception e){
                Log.d("zzzzz", "click hoa don: "+ e);
            }
        }
    }
}
