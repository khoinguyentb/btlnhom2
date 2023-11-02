package com.kano.btlnhom2.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.kano.btlnhom2.DAO.ThongKeDAO;
import com.kano.btlnhom2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class FragmentThongKe extends Fragment {

    Button btnDoanhThu;
    ImageView btnTuNgay,btnDenNgay;
    EditText edtTuNgay,edtDenNgay;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    int mYear,mMonth,mDay;
    TextView tv_doanhThuTong,tv_doanhThuDV,tv_tongTienDenBu;
    int temp=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongke, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnTuNgay = view.findViewById(R.id.doanhthu_img_tungay);
        btnDenNgay = view.findViewById(R.id.doanhthu_img_denngay);
        btnDoanhThu = view.findViewById(R.id.doanhthu_btn_tinh);

        edtTuNgay = view.findViewById(R.id.doanhthu_edt_tungay);
        edtDenNgay = view.findViewById(R.id.doanhthu_edt_denngay);

        tv_doanhThuTong = view.findViewById(R.id.doanhthu_tv_doanhthutong);
        tv_doanhThuDV = view.findViewById(R.id.doanhthu_tv_doanhthuDV);
        tv_tongTienDenBu = view.findViewById(R.id.doanhthu_tv_doanhthuDenBu);

        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),0,mDateTuNgay,mYear,mMonth,mDay);
                d.show();
            }
        });

        btnDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),0,mDateDenNDen,mYear,mMonth,mDay);
                d.show();
            }
        });



        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tuNgay = edtTuNgay.getText().toString();
                String denNgay = edtDenNgay.getText().toString();
                if (tuNgay.isEmpty()||denNgay.isEmpty()){
                    Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
                    temp++;
                }else {
                    String[] temptungay = tuNgay.split("/");
                    String[] tempdenngay = denNgay.split("/");

                    String newTungay = "";
                    String newdenngay = "";

                    int inttungay = Integer.parseInt(newTungay.concat(temptungay[0]).concat(temptungay[1]).concat(temptungay[2]));
                    int intdenngay = Integer.parseInt(newdenngay.concat(tempdenngay[0]).concat(tempdenngay[1]).concat(tempdenngay[2]));

//                    int inttungay = Integer.parseInt(newTungay.concat(temptungay[0]));
//                    int intdenngay = Integer.parseInt(newdenngay.concat(tempdenngay[0]));
//                    Tính số ngày
                    Log.d("zzzzz", "số ngày: "+(intdenngay - inttungay));

                    if (inttungay > intdenngay) {
                        Toast.makeText(getActivity(), "Lỗi, từ ngày phải bé hơn đến ngày", Toast.LENGTH_SHORT).show();
                        temp++;
                    }
                }

                if (temp==0){
                    ThongKeDAO dao = new ThongKeDAO(getActivity());
                    Log.d("zzzz", "onClick: "+dao.getDoanhThuTongTien(tuNgay,denNgay));

                    tv_doanhThuTong.setText("Doanh thu tổng: "+dao.getDoanhThuTongTien(tuNgay,denNgay)+" VNĐ");
                    tv_doanhThuDV.setText("Doanh thu dịch vụ: "+dao.getDoanhThuTongTienDichVu(tuNgay,denNgay)+" VNĐ");
                    tv_tongTienDenBu.setText("Tổng đền bù: "+dao.getDoanhThuTongTienDenBu(tuNgay,denNgay)+" VNĐ");

                }else {
                    temp=0;
                }
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
            edtTuNgay.setText(sdf.format(c.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener mDateDenNDen = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfYear) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfYear;
            GregorianCalendar c = new GregorianCalendar(mYear,mMonth,mDay);
            edtDenNgay.setText(sdf.format(c.getTime()));
        }
    };
}
