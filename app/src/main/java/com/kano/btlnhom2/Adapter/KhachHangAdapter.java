package com.kano.btlnhom2.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.kano.btlnhom2.DAO.KhachHangDAO;
import com.kano.btlnhom2.DTO.KhachHang;
import com.kano.btlnhom2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.KhachHangViewHolder> {
    private Context context;
    private ArrayList<KhachHang> list;

    public KhachHangAdapter(Context context, ArrayList<KhachHang> list){
        this.context= context;
        this.list = list;
    }
    public void setFilteredList(ArrayList<KhachHang> filteredList){
        this.list=filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KhachHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_khach_hang,parent,false);
        return new KhachHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangViewHolder holder, int position) {
        KhachHang khachHang = list.get(position);
        holder.txtName.setText("Khách hàng : "+list.get(position).getName());
        holder.txtPhone.setText("SĐT : "+list.get(position).getPhone());
        holder.txtBirtday.setText("Ngày sinh : "+list.get(position).getBirthday());

        Calendar calendar =Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        KhachHangDAO dao = new KhachHangDAO(context);
        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_add_khach_hang);
                Window window = dialog.getWindow();
                if(window==null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams windowacc = window.getAttributes();
                windowacc.gravity = Gravity.NO_GRAVITY ;
                window.setAttributes(windowacc);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);

                Button btnAdd = dialog.findViewById(R.id.btnAdd_KH);
                btnAdd.setText("CẬP NHẬT");
                EditText ed_name = dialog.findViewById(R.id.edName);
                EditText ed_phone = dialog.findViewById(R.id.edPhone);
                EditText ed_birthday = dialog.findViewById(R.id.edBirthday);
                TextView txt = dialog.findViewById(R.id.txtTitel);
                txt.setText("SỬA KHÁCH HÀNG");
                ed_name.setText(khachHang.getName());
                ed_phone.setText(khachHang.getPhone()+"");
                ed_birthday.setText(khachHang.getBirthday()+"");
                ImageView img = dialog.findViewById(R.id.imgdate);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog dialog1 = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                int myear = year ;
                                int mmonth = month ;
                                int mdayOfMonth = dayOfMonth ;
                                GregorianCalendar c = new GregorianCalendar(myear,mmonth,mdayOfMonth);
                                ed_birthday.setText(sdf.format(c.getTime()));
                            }
                        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
                        dialog1.show();
                    }
                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ed_name.getText().length()==0||
                                ed_phone.getText().length()==0||
                                ed_birthday.getText().length()==0){
                            Toast.makeText(context,"Không được để trống",Toast.LENGTH_SHORT).show();
                        }else if(!(isValidFormat("dd/MM/yyyy",ed_birthday.getText().toString()))){
                            Toast.makeText(context,"Không đúng định dạng ngày",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            khachHang.setName(ed_name.getText().toString());
                            khachHang.setPhone(ed_phone.getText().toString());
                            khachHang.setBirthday(ed_birthday.getText().toString());
                            long res = dao.update(khachHang);
                            if (res>0){
                                Toast.makeText(context,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(dao.getAll());
                                notifyDataSetChanged();
                            }else {
                                Toast.makeText(context,"Cập nhật thất bại ",Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        holder.imgDel.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class KhachHangViewHolder extends RecyclerView.ViewHolder{
        TextView txtName , txtPhone , txtBirtday ;
        ImageView imgUp ,imgDel;
        public KhachHangViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtBirtday = itemView.findViewById(R.id.txtBirthday);

            imgDel = itemView.findViewById(R.id.imgdel);
        }
    }
    public boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = (Date) sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }
}
