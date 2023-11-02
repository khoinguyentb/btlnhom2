package com.kano.btlnhom2.Adapter;

import android.annotation.SuppressLint;
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

import com.kano.btlnhom2.DAO.LoaiPhongDAO;
import com.kano.btlnhom2.DTO.LoaiPhong;
import com.kano.btlnhom2.R;
import com.kano.btlnhom2.click_interface.LoaiphongClick;

import java.util.List;


public class LoaiPhongAdapter extends RecyclerView.Adapter<LoaiPhongAdapter.viewholder> {
    private Context context;
    private List<LoaiPhong> list;
    private LoaiPhongDAO loaiphongDAO;
    private LoaiphongClick loaiphongClick;
    public LoaiPhongAdapter(Context context, List<LoaiPhong> list) {
        this.context = context;
        this.list = list;
        loaiphongDAO=new LoaiPhongDAO(context);
    }
    public void setLoaiphongClick(LoaiphongClick loaiphongClick) {
        this.loaiphongClick = loaiphongClick;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loai_phong,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        LoaiPhong loaiphong=list.get(position);
        holder.tv_namelp.setText(""+loaiphong.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiphongClick.onClick(list.get(position));
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                        .setTitle("Bạn có muốn xóa không")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                if (loaiphongDAO.delete(loaiphong.getId()) > 0) {
//                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
//                                    list.clear();
//                                    list.addAll(loaiphongDAO.getAll());
//                                    notifyDataSetChanged();
//                                } else {
//                                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
//                                }

                                int check = loaiphongDAO.delete(list.get(holder.getAdapterPosition()).getId());
                                switch (check){
                                    case  1 :
                                        list.clear();
                                        list.addAll(loaiphongDAO.getAll());
                                        notifyDataSetChanged();
                                        Toast.makeText(context,"Xóa thành công",Toast.LENGTH_SHORT).show();
                                        break;
                                    case -1:
                                        Toast.makeText(context,"Không thể xóa vì có phòng với loại phòng này",Toast.LENGTH_SHORT).show();
                                        break;
                                    case 0 :
                                        Toast.makeText(context,"Xóa không thành công",Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        break;
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

    public class viewholder  extends RecyclerView.ViewHolder{
        TextView tv_namelp;
        ImageView btn_delete;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            tv_namelp=itemView.findViewById(R.id.tv_namelp);
            btn_delete=itemView.findViewById(R.id.btn_delete);
        }
    }
}
