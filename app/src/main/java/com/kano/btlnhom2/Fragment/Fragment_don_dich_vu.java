package com.kano.btlnhom2.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kano.btlnhom2.Adapter.DonDichVuAdapter;
import com.kano.btlnhom2.Adapter.SpinnerDichVuAdapter;
import com.kano.btlnhom2.Adapter.SpinnerHoaDonAdapter;
import com.kano.btlnhom2.DAO.HoaDonDAO;
import com.kano.btlnhom2.DAO.HoaDonDichVuDAO;
import com.kano.btlnhom2.DAO.LoaiDichVuDao;
import com.kano.btlnhom2.DTO.HoaDon;
import com.kano.btlnhom2.DTO.HoaDonDichVu;
import com.kano.btlnhom2.DTO.LoaiDichVu;
import com.kano.btlnhom2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class Fragment_don_dich_vu extends Fragment {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private FloatingActionButton fab_them_don_dv;
    private RecyclerView recyclerView;
    private Spinner dialog_spn_hd_phong,dialog_spn_loai_dv;
    private Button btn_add,btn_cancel;

    HoaDonDichVu hoaDonDichVu;
    HoaDonDichVuDAO hoaDonDichVuDAO;
    List<HoaDonDichVu> listHoaDonDV;
    DonDichVuAdapter donDichVuAdapter;

    private HoaDonDAO hoaDonDAO;
    private List<HoaDon> listHoaDon;
    SpinnerHoaDonAdapter spinnerHoaDonAdapter;
    HoaDon hoaDon;

    String maHoaDon,maDichVu;

    private LoaiDichVuDao loaiDichVuDao;
    private List<LoaiDichVu> listLoaiDichVu;
    SpinnerDichVuAdapter spinnerDichVuAdapter;
    LoaiDichVu loaiDichVu;
    TextView tv_tongTienDV;
    int tienDV,tongTienDV,soLuong;
    EditText ed_soLuong;

    Calendar c = Calendar.getInstance();

    TextView tv_ngayhddv;

    ImageButton btn_refresh;

    List<String> loi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_don_dich_vu, container, false);
        fab_them_don_dv = view.findViewById(R.id.btn_add_dich_vu);
        recyclerView = view.findViewById(R.id.rcv_dich_vu);
        hoaDonDAO = new HoaDonDAO(getActivity());
        loaiDichVuDao = new LoaiDichVuDao(getActivity());
        hoaDonDichVuDAO = new HoaDonDichVuDAO(getActivity());
        loi = new ArrayList<>();


        loadTable();

        fab_them_don_dv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listLoaiDichVu = loaiDichVuDao.getAll();
                listHoaDon = hoaDonDAO.getAllstatus0();
                if (listLoaiDichVu.size()==0){
                    loi.add(" Dịch vụ ");
                }
                if (listHoaDon.size()==0){
                    loi.add(" Hóa đơn đặt được dịch vụ ");
                }
                if (loi.isEmpty()){
                    openDialog();
                }else{
                    Toast.makeText(getActivity(), "Bạn chưa thêm : "+loi, Toast.LENGTH_SHORT).show();
                    loi = new ArrayList<>();
                }


            }
        });
        return view;
    }

    private void loadTable(){
        hoaDonDichVuDAO = new HoaDonDichVuDAO(getActivity());
        listHoaDonDV = hoaDonDichVuDAO.getAll();
        donDichVuAdapter = new DonDichVuAdapter(getActivity(), (ArrayList<HoaDonDichVu>) listHoaDonDV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(donDichVuAdapter);
    }

    private void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_don_dich_vu, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        dialog_spn_hd_phong= dialog.findViewById(R.id.dialog_spn_hoa_don);
        dialog_spn_loai_dv = dialog.findViewById(R.id.dialog_spn_dich_vu);
        tv_ngayhddv = dialog.findViewById(R.id.dialog_tv_ngay_tao_don_dv);
        ed_soLuong = dialog.findViewById(R.id.ed_so_luong_dv);
        ed_soLuong.setText("1");
        tv_tongTienDV = dialog.findViewById(R.id.dialog_tv_tong_tien_don_dv);
        btn_refresh = dialog.findViewById(R.id.btn_refresh_don_dv);
        btn_add = dialog.findViewById(R.id.dialog_btn_them_don_dv);
        btn_cancel = dialog.findViewById(R.id.dialog_btn_huy_don_dv);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        spinnerHoaDonAdapter = new SpinnerHoaDonAdapter(getContext(), (ArrayList<HoaDon>) hoaDonDAO.getAllstatus0());
        dialog_spn_hd_phong.setAdapter(spinnerHoaDonAdapter);
        dialog_spn_hd_phong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listHoaDon = hoaDonDAO.getAllstatus0();
                maHoaDon = String.valueOf(listHoaDon.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerDichVuAdapter = new SpinnerDichVuAdapter(getContext(), (ArrayList<LoaiDichVu>) loaiDichVuDao.getAll());
        dialog_spn_loai_dv.setAdapter(spinnerDichVuAdapter);
        dialog_spn_loai_dv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listLoaiDichVu = loaiDichVuDao.getAll();
                maDichVu = String.valueOf(listLoaiDichVu.get(position).getId());
                loaiDichVu = loaiDichVuDao.getAll().get(position);
                tienDV = loaiDichVu.getPrice();
                try {
                    soLuong = Integer.parseInt(ed_soLuong.getText().toString());
                }catch (Exception e){
                    Log.d("zzzzz", "onItemSelected: so luong dv");
                }
                tongTienDV = tienDV * soLuong;
                tv_tongTienDV.setText("Tổng tiền: "+tongTienDV+" VNĐ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String datetime = sdf.format(c.getTime());
        tv_ngayhddv.setText("Ngày tạo hóa đơn: "+datetime);

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    soLuong = Integer.parseInt(ed_soLuong.getText().toString());
                }catch (Exception e){
                    Log.d("zzzzz", "onItemSelected: so luong dv");
                    Toast.makeText(getContext(),"Nhập số lượng dịch vụ",Toast.LENGTH_LONG).show();
                }
                tongTienDV = tienDV * soLuong;
                tv_tongTienDV.setText("Tổng tiền: "+tongTienDV+" VNĐ");
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_soLuong.getText().length()>0){
                    soLuong = Integer.parseInt(ed_soLuong.getText().toString());
                    tongTienDV = tienDV * soLuong;
                    tv_tongTienDV.setText("Tổng tiền: "+tongTienDV+" VNĐ");
                    hoaDonDichVu=new HoaDonDichVu();
                    hoaDonDichVu.setBill_id(Integer.parseInt(maHoaDon));
                    hoaDonDichVu.setService_id(Integer.parseInt(maDichVu));
                    hoaDonDichVu.setService_quantity(soLuong);
                    hoaDonDichVu.setService_date(datetime);
                    hoaDonDichVu.setTotal(tongTienDV);

                    if (hoaDonDichVuDAO.insert(hoaDonDichVu)>0){
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        loadTable();
                    }else {
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Thêm số lượng dịch vụ", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}