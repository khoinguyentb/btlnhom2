package com.kano.btlnhom2.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kano.btlnhom2.Adapter.HoaDonAdapter;
import com.kano.btlnhom2.Adapter.SpinnerKhachHangAdapter;
import com.kano.btlnhom2.Adapter.SpinnerPhongAdapter;
import com.kano.btlnhom2.DAO.HoaDonDAO;
import com.kano.btlnhom2.DAO.HoaDonDichVuDAO;
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
import java.util.GregorianCalendar;
import java.util.List;


public class FragmentHoaDon extends Fragment {


    public FragmentHoaDon() {
        // Required empty public constructor
    }

    public static FragmentHoaDon newInstance(String param1, String param2) {
        FragmentHoaDon fragment = new FragmentHoaDon();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hoa_don, container, false);
    }

    static final String TAG = "KN";

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    FloatingActionButton fabAdd;
    ImageView btnTuNgayHD,btnDenNgayHD;
    EditText edTuNgayHD,edDenNgayHD,edTienMat,edGhiChu;
    CheckBox chkTrangThai;
    HoaDon hoaDon;
    HoaDonAdapter hoaDonAdapter;
    HoaDonDAO hoaDonDAO;
    RecyclerView recyclerView;
    List<HoaDon> listHoaDon;

    String maKhacHang,maPhong;

    Spinner spnKhachHang;
    List<KhachHang> listKhachHang;
    KhachHangDAO khachHangDAO;
    SpinnerKhachHangAdapter spinnerKhachHangAdapter;

    Spinner spnPhong;
    List<Phong> listPhong;
    PhongDao phongDao;
    SpinnerPhongAdapter spinnerPhongAdapter;
    Phong phong;

    int mYear,mMonth,mDay;
    Calendar c = Calendar.getInstance();

    TextView tv_tienPhong,tv_tienDV,tv_tongTien;

    int tienPhong,tongTienPhong,tienDV,tienDenBu,tongTien,soNgay;

    ImageButton btn_refresh;

    String tuNgay,denNgay;

    int temp=0,a;

    Button btn_timPhong;

    Spinner spn_trang_thai;

    List<String> loi;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcv_hoa_don);
        fabAdd = view.findViewById(R.id.btn_add_hoa_don);
        spn_trang_thai = view.findViewById(R.id.spinner_status_hd);

        listKhachHang = new ArrayList<>();
        khachHangDAO = new KhachHangDAO(getActivity());

        listPhong  = new ArrayList<>();
        phongDao = new PhongDao(getActivity());

        hoaDonDAO = new HoaDonDAO(getActivity());
        hoaDonAdapter = new HoaDonAdapter(getActivity());

        loi = new ArrayList<>();


        loadTable();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listKhachHang.size()==0){
                    loi.add("khách hàng");
                }
                if (listPhong.size()==0){
                    loi.add("phòng");
                }
                if (loi.isEmpty()){
                    openDialogHN(getActivity(),0);

                }else{
                    Toast.makeText(getActivity(), "Bạn chưa thêm : "+loi, Toast.LENGTH_SHORT).show();
                    loi = new ArrayList<>();
                }
            }
        });

//        hoaDonAdapter.setHoaDonClick(new HoaDonClick() {
//            @Override
//            public void onClick(View view, int position) {
//                a = position;
//                Log.d(TAG, "onClick: "+position);
//                openDialogHN(getContext(),1);
//            }
//        });



    }

    private void loadTable(){

        listPhong = phongDao.getAll();
        listKhachHang = khachHangDAO.getAll();
        listHoaDon = hoaDonDAO.getAll();

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.trang_thai_hd,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_trang_thai.setAdapter(spinnerAdapter);
        spn_trang_thai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //chưa nhận phòng
                if (position == 0){
                    listHoaDon = hoaDonDAO.getAllstatus3();
                    hoaDonAdapter.setData((ArrayList<HoaDon>) listHoaDon);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(hoaDonAdapter);
                    hoaDonAdapter.setHoaDonClick(new HoaDonClick() {
                        @Override
                        public void onClick(View view, int position) {
                            Toast.makeText(getContext(),"Chưa nhận phòng",Toast.LENGTH_LONG).show();
                        }
                    });
                }else if (position==1){
                    //chưa trả phòng
                    listHoaDon = hoaDonDAO.getAllstatus0();
                    hoaDonAdapter.setData((ArrayList<HoaDon>) listHoaDon);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(hoaDonAdapter);

                    hoaDonAdapter.setHoaDonClick(new HoaDonClick() {
                        @Override
                        public void onClick(View view, int position) {
                            a = position;
                            Log.d(TAG, "onClick: "+position);
                            openDialogHN(getContext(),1);
                        }
                    });
                }else if (position==2){
                    //đã trả phòng
                    listHoaDon = hoaDonDAO.getAllstatus1();
                    hoaDonAdapter.setData((ArrayList<HoaDon>) listHoaDon);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(hoaDonAdapter);

                    hoaDonAdapter.setHoaDonClick(new HoaDonClick() {
                        @Override
                        public void onClick(View view, int position) {
                            a = position;
                            Log.d(TAG, "onClick: "+position);
                            openDialogHN(getContext(),1);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfYear) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfYear;
            GregorianCalendar c = new GregorianCalendar(mYear,mMonth,mDay);
            edTuNgayHD.setText(sdf.format(c.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener mDateDenNDen = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfYear) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfYear;
            GregorianCalendar c = new GregorianCalendar(mYear,mMonth,mDay);
            edDenNgayHD.setText(sdf.format(c.getTime()));
        }
    };



    public void openDialogHN(final Context context, final int type) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_hoa_don);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnAdd = dialog.findViewById(R.id.btn_luuhd);
        Button btnCancel = dialog.findViewById(R.id.btn_huyhd);



        btnTuNgayHD = dialog.findViewById(R.id.img_bill_start_date);
        btnDenNgayHD = dialog.findViewById(R.id.img_bill_end_date);
        edTuNgayHD = dialog.findViewById(R.id.ed_start_date);
        edDenNgayHD = dialog.findViewById(R.id.ed_end_date);
        edTienMat = dialog.findViewById(R.id.ed_lost_total);
        spnKhachHang = dialog.findViewById(R.id.spn_guest);
        spnPhong = dialog.findViewById(R.id.spn_room);
        chkTrangThai = dialog.findViewById(R.id.chk_bill_status);
        edGhiChu = dialog.findViewById(R.id.ed_bill_note);

        tv_tienPhong = dialog.findViewById(R.id.tv_bill_room_total);
        tv_tienDV = dialog.findViewById(R.id.dialog_tv_bill_service_total);
        tv_tongTien = dialog.findViewById(R.id.dialog_tv_bill_total);
        btn_refresh = dialog.findViewById(R.id.btn_refresh);

        btn_timPhong = dialog.findViewById(R.id.btn_tim_phong);

//        listHoaDon = hoaDonDAO.getAll();

        btnTuNgayHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateTuNgay, mYear, mMonth, mDay);
                d.show();
            }
        });
        btnDenNgayHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateDenNDen, mYear, mMonth, mDay);
                d.show();


            }
        });

        spnPhong.setVisibility(View.INVISIBLE);
        spinnerPhongAdapter = new SpinnerPhongAdapter(context, (ArrayList<Phong>) phongDao.getAll());
        spnPhong.setAdapter(spinnerPhongAdapter);




        spinnerKhachHangAdapter = new SpinnerKhachHangAdapter(context, khachHangDAO.getAll());
        spnKhachHang.setAdapter(spinnerKhachHangAdapter);
        spnKhachHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maKhacHang = String.valueOf(listKhachHang.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Intent intent = getActivity().getIntent();
        String user = intent.getStringExtra("user");

        String datetime = sdf.format(c.getTime());

        btn_timPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuNgay = edTuNgayHD.getText().toString();
                denNgay = edDenNgayHD.getText().toString();
                if (tuNgay.length() == 0 || denNgay.length() == 0){
                    Toast.makeText(getActivity(),"Hãy chọn ngày",Toast.LENGTH_LONG).show();
                }else{
//                    spinnerPhongAdapter = new SpinnerPhongAdapter(context, (ArrayList<Phong>) phongDao.getAllDatPhong(tuNgay,denNgay,tuNgay,denNgay));

                    String[] temptungay = tuNgay.split("/");
                    String[] tempdenngay = denNgay.split("/");

                    String newTungay = "";
                    String newdenngay = "";


                    int inttungay = Integer.parseInt(newTungay.concat(temptungay[0]));
                    int intdenngay = Integer.parseInt(newdenngay.concat(tempdenngay[0]));
                    Log.d(TAG, "số ngày: " + (intdenngay - inttungay));
                    soNgay = intdenngay - inttungay;


                    if (inttungay > intdenngay) {
                        Toast.makeText(getActivity(), "Lỗi, từ ngày phải bé hơn đến ngày", Toast.LENGTH_SHORT).show();
                    }else {
                        spnPhong.setVisibility(View.VISIBLE);
                        spnPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                maPhong = String.valueOf(listPhong.get(position).getId());

                                phong = phongDao.getAll().get(position);


                                tienPhong = phong.getPrice();
                                tongTienPhong = tienPhong * soNgay;
                                Log.d(TAG, "onItemSelected: " + soNgay);

                                tv_tienPhong.setText(tongTienPhong + " VNĐ");

                                tongTien = tongTienPhong + tienDV;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                    }

                }
//

            }
        });

        if (type == 0) {
            edTuNgayHD.setEnabled(true);
            edDenNgayHD.setEnabled(true);
            edTienMat.setText("0");

//            tuNgay = edTuNgayHD.getText().toString();
//            denNgay = edDenNgayHD.getText().toString();
//            if (tuNgay.isEmpty() || denNgay.isEmpty()) {
//                Toast.makeText(getActivity(), "Hãy nhập ngày", Toast.LENGTH_SHORT).show();
//
//            } else {
//                String[] temptungay = tuNgay.split("/");
//                String[] tempdenngay = denNgay.split("/");
//
//                String newTungay = "";
//                String newdenngay = "";
//
//
//                int inttungay = Integer.parseInt(newTungay.concat(temptungay[0]));
//                int intdenngay = Integer.parseInt(newdenngay.concat(tempdenngay[0]));
//                Log.d(TAG, "số ngày: " + (intdenngay - inttungay));
//                soNgay = intdenngay - inttungay;
//
//
//                if (inttungay > intdenngay) {
//                    Toast.makeText(getActivity(), "Lỗi, từ ngày phải bé hơn đến ngày", Toast.LENGTH_SHORT).show();
//
//                }
//            }




//        tienDenBu = Integer.parseInt(String.valueOf(edTienMat.getText()));

            btn_refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String tuNgay = edTuNgayHD.getText().toString();
                    String denNgay = edDenNgayHD.getText().toString();
                    if (tuNgay.isEmpty() || denNgay.isEmpty()) {
                        Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();

                    } else {
                        String[] temptungay = tuNgay.split("/");
                        String[] tempdenngay = denNgay.split("/");

                        String newTungay = "";
                        String newdenngay = "";


                        int inttungay = Integer.parseInt(newTungay.concat(temptungay[0]));
                        int intdenngay = Integer.parseInt(newdenngay.concat(tempdenngay[0]));
                        Log.d(TAG, "số ngày: " + (intdenngay - inttungay));
                        soNgay = intdenngay - inttungay;


                        if (inttungay > intdenngay) {
                            Toast.makeText(getActivity(), "Lỗi, từ ngày phải bé hơn đến ngày", Toast.LENGTH_SHORT).show();
                        } else {
                            tienDenBu = Integer.parseInt(String.valueOf(edTienMat.getText()));
                            tongTienPhong = tienPhong * soNgay;
                            tongTien = tongTienPhong + tienDV + tienDenBu;
                            tv_tongTien.setText("Tổng tiền: " + tongTien + " VNĐ");
                            tv_tienPhong.setText(tongTienPhong + " VNĐ");

                        }
                    }


                }
            });

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tienDenBu = Integer.parseInt(String.valueOf(edTienMat.getText()));
                    tongTien = tongTienPhong + tienDV + tienDenBu;
                    tv_tongTien.setText("Tổng tiền: " + tongTien + " VNĐ");

                    if (edTuNgayHD.getText().length() == 0 || edDenNgayHD.getText().length() == 0 || edTienMat.getText().length() == 0) {
                        Toast.makeText(getContext(), "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }else {
                        hoaDon = new HoaDon();
                        hoaDon.setManager_id(user);
                        hoaDon.setGuest_id(Integer.parseInt(maKhacHang));
                        maPhong = String.valueOf(((Phong)spnPhong.getSelectedItem()).getId());
                        hoaDon.setRoom_id(Integer.parseInt(maPhong));
                        hoaDon.setStart_date(edTuNgayHD.getText().toString());
                        hoaDon.setEnd_date(edDenNgayHD.getText().toString());
                        hoaDon.setService_total(tienDV);
                        hoaDon.setRoom_total(tongTienPhong);
                        hoaDon.setLost_total(Integer.parseInt(String.valueOf(edTienMat.getText())));
//                        if (chkTrangThai.isChecked()) {
//                            hoaDon.setStatus(1);
//                        } else {
//                            hoaDon.setStatus(0);
//                        }
                        hoaDon.setStatus(3);

                        hoaDon.setNote(String.valueOf(edGhiChu.getText()));
                        hoaDon.setBill_date(datetime);
                        hoaDon.setBill_total(tongTien);

                        if (hoaDonDAO.insert(hoaDon) > 0) {
                            Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_LONG).show();
                            listHoaDon.clear();
                            listHoaDon.addAll(hoaDonDAO.getAll());
                            hoaDonAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                            loadTable();
                        } else {
                            Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Bấm hủy", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                }
            });
        } else if (type == 1) {
            btn_refresh.performClick();
            btnAdd.setText("Cập nhật");
            btn_timPhong.performClick();
            btn_timPhong.setEnabled(false);
            btnDenNgayHD.setEnabled(false);
            btnTuNgayHD.setEnabled(false);
            HoaDonDichVuDAO hoaDonDichVuDAO = new HoaDonDichVuDAO(context);
            hoaDon = hoaDonDAO.getAll().get(a);
            int maHD = listHoaDon.get(a).getId();
            Log.d(TAG, "openDialogHN: ma hoa don "+maHD);

            for (int i = 0; i < spnKhachHang.getCount(); i++) {
                if (listKhachHang.get(i).getId() == listHoaDon.get(a).getGuest_id()) {
                    Log.d(TAG, "openDialogHN kh: "+i);
                    spnKhachHang.setSelection(i);
                    maKhacHang = String.valueOf(listKhachHang.get(i).getId());
                }
            }

            for (int i = 0; i < spnPhong.getCount(); i++) {
                if (listPhong.get(i).getId() == listHoaDon.get(a).getRoom_id()) {
                    Log.d(TAG, "openDialogHN p: "+i);
                    spnPhong.setSelection(i);
                    maPhong = String.valueOf(listPhong.get(i).getId());
                }
            }

            tienDV = hoaDonDichVuDAO.getTienDV(String.valueOf(maHD));
            Log.d(TAG, "tien dv = "+tienDV);
            tv_tienDV.setText("Tiền dịch vụ: " + tienDV + " VNĐ");


            edTuNgayHD.setText(listHoaDon.get(a).getStart_date());
            edDenNgayHD.setText(listHoaDon.get(a).getEnd_date());

            spnKhachHang.setEnabled(false);
            spnPhong.setEnabled(false);

            btn_timPhong.performClick();

            if (listHoaDon.get(a).getStatus()==1){
                chkTrangThai.setChecked(true);
            }else {
                chkTrangThai.setChecked(false);
            }

            edTienMat.setText(listHoaDon.get(a).getLost_total() + "");
            edGhiChu.setText(listHoaDon.get(a).getNote());
            tv_tienPhong.setText(listHoaDon.get(a).getRoom_total() + " VNĐ");
            tv_tongTien.setText("Tổng tiền: " + listHoaDon.get(a).getBill_total() + " VNĐ");



            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Bấm hủy", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            btn_refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String tuNgay = edTuNgayHD.getText().toString();
                    String denNgay = edDenNgayHD.getText().toString();
                    if (tuNgay.isEmpty() || denNgay.isEmpty()) {
                        Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();

                    } else {
                        String[] temptungay = tuNgay.split("/");
                        String[] tempdenngay = denNgay.split("/");

                        String newTungay = "";
                        String newdenngay = "";


                        int inttungay = Integer.parseInt(newTungay.concat(temptungay[0]));
                        int intdenngay = Integer.parseInt(newdenngay.concat(tempdenngay[0]));
                        Log.d(TAG, "số ngày: " + (intdenngay - inttungay));
                        soNgay = intdenngay - inttungay;


                        if (inttungay > intdenngay) {
                            Toast.makeText(getActivity(), "Lỗi, từ ngày phải bé hơn đến ngày", Toast.LENGTH_SHORT).show();
                        } else {
                            tienDenBu = Integer.parseInt(String.valueOf(edTienMat.getText()));
                            tongTienPhong = tienPhong * soNgay;
                            tongTien = tongTienPhong + tienDV + tienDenBu;
                            tv_tongTien.setText("Tổng tiền: " + tongTien + " VNĐ");
//                            tv_tienPhong.setText(tongTienPhong + " VNĐ");

                        }
                    }


                }
            });

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tienDenBu = Integer.parseInt(String.valueOf(edTienMat.getText()));
                    tongTien = tongTienPhong + tienDV + tienDenBu;
                    tv_tongTien.setText("Tổng tiền: " + tongTien + " VNĐ");
                    if (edTuNgayHD.getText().length() == 0 || edDenNgayHD.getText().length() == 0 || edTienMat.getText().length() == 0) {
                        Toast.makeText(getContext(), "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    }else {
                        hoaDon = new HoaDon();
                        hoaDon.setId(listHoaDon.get(a).getId());
                        hoaDon.setManager_id(user);
                        hoaDon.setGuest_id(Integer.parseInt(maKhacHang));
                        hoaDon.setRoom_id(Integer.parseInt(maPhong));
                        hoaDon.setStart_date(edTuNgayHD.getText().toString());
                        hoaDon.setEnd_date(edDenNgayHD.getText().toString());
                        hoaDon.setService_total(tienDV);
                        hoaDon.setRoom_total(tongTienPhong);
                        hoaDon.setLost_total(Integer.parseInt(String.valueOf(edTienMat.getText())));
                        if (chkTrangThai.isChecked()) {
                            hoaDon.setStatus(1);
                        } else {
                            hoaDon.setStatus(0);
                        }
                        hoaDon.setNote(String.valueOf(edGhiChu.getText()));
                        hoaDon.setBill_date(listHoaDon.get(a).getBill_date());
                        hoaDon.setBill_total(tongTien);

//                        Log.d(TAG, "id ql: "+user);
//                        Log.d(TAG, "ma kh: "+maKhacHang);
//                        Log.d(TAG, "ma phong: "+maPhong);
//                        Log.d(TAG, "start: "+edTuNgayHD.getText().toString());
//                        Log.d(TAG, "end: "+edDenNgayHD.getText().toString());
//                        Log.d(TAG, "tien dv: "+tienDV);
//                        Log.d(TAG, "tien phong: "+tongTienPhong);
//                        Log.d(TAG, "tien den bu: "+edTienMat.getText());
//                        Log.d(TAG, "note: "+edGhiChu.getText());
//                        Log.d(TAG, "ngay tao hd: "+listHoaDon.get(a).getBill_date());
//                        Log.d(TAG, "tong tien: "+tongTien);

                        if (hoaDonDAO.update(hoaDon) > 0) {
                            Toast.makeText(getActivity(), "Sửa thành công", Toast.LENGTH_LONG).show();
                            listHoaDon.clear();
                            listHoaDon.addAll(hoaDonDAO.getAll());
                            hoaDonAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                            loadTable();
                        } else {
                            Toast.makeText(getActivity(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }

        dialog.show();

    }
}

