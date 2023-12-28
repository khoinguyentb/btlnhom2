package com.kano.btlnhom2.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kano.btlnhom2.Adapter.KhachHangAdapter;
import com.kano.btlnhom2.DAO.KhachHangDAO;
import com.kano.btlnhom2.DTO.KhachHang;
import com.kano.btlnhom2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Fragment_khachhang extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_khachhang, container, false);
    }
    RecyclerView rcv_kh ;
    LinearLayoutManager linearLayoutManager;
    KhachHangDAO dao ;
    KhachHangAdapter adapter;
    ArrayList<KhachHang> list ;
    FloatingActionButton btnADD;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private SearchView searchView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv_kh = view.findViewById(R.id.rcv_khach_hang);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rcv_kh.setLayoutManager(linearLayoutManager);
        dao = new KhachHangDAO(getContext());
        list = dao.getAll();
        searchView=view.findViewById(R.id.sv_tim_kh);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FinterList(newText);
                return true;
            }
        });
        adapter = new KhachHangAdapter(getContext(),list);
        rcv_kh.setAdapter(adapter);
        btnADD = view.findViewById(R.id.btn_add_khach_hang);
        Calendar calendar =Calendar.getInstance();
        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
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
                EditText ed_name = dialog.findViewById(R.id.edName);
                EditText ed_phone = dialog.findViewById(R.id.edPhone);
                EditText ed_birthday = dialog.findViewById(R.id.edBirthday);
                ImageView img = dialog.findViewById(R.id.imgdate);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog dialog1 = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                            Toast.makeText(getContext(),getActivity().getString(R.string.toast_value_date),Toast.LENGTH_SHORT).show();
                        }else if(!(isValidFormat("dd/MM/yyyy",ed_birthday.getText().toString()))){
                            Toast.makeText(getContext(),getActivity().getString(R.string.toast_value_date_date),Toast.LENGTH_SHORT).show();
                        }else  if(!(checkPhone(ed_phone.getText().toString()))){
                            Toast.makeText(getContext(),"Không đúng định dạng điện thoại",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            KhachHang khachHang = new KhachHang();
                            khachHang.setName(ed_name.getText().toString());
                            khachHang.setPhone(ed_phone.getText().toString());
                            khachHang.setBirthday(ed_birthday.getText().toString());
                            long res = dao.insert(khachHang);
                            if (res>0){
                                Toast.makeText(getContext(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(dao.getAll());
                                adapter.notifyDataSetChanged();
                            }else {
                                Toast.makeText(getContext(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
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
    }
    public boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }
    private void FinterList(String text) {
        ArrayList<KhachHang> filteredList=new ArrayList<>();

        for (KhachHang khachHang: list){
            if (khachHang.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(khachHang);
            }

        }
        if (filteredList.isEmpty()){
            Toast.makeText(this.getContext(), "no data", Toast.LENGTH_SHORT).show();
        }else {
            adapter.setFilteredList(filteredList);
        }
    }
            public boolean checkPhone(String str){
                // Bieu thuc chinh quy mo ta dinh dang so dien thoai
                String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";

                // Kiem tra dinh dang
                boolean kt = str.matches(reg);

                if (kt == false) {
                    return  false ;
                } else {
                    return  true ;
                }
            }
}