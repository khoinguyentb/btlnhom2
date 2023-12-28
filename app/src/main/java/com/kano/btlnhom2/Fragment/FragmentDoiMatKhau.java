package com.kano.btlnhom2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.kano.btlnhom2.DAO.QuanLyDAO;
import com.kano.btlnhom2.DTO.QuanLy;
import com.kano.btlnhom2.LoginActivity;
import com.kano.btlnhom2.MainActivity;
import com.kano.btlnhom2.R;


public class FragmentDoiMatKhau extends Fragment {
    public FragmentDoiMatKhau() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doimatkhau, container, false);
    }

    private EditText ed_TenDN;
    private EditText ed_MKCu;
    private EditText ed_MKMoi;
    private Button btn_DoiMk;
    private QuanLyDAO dao;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ed_TenDN = view.findViewById(R.id.ed_TenDN);
        ed_MKCu = view.findViewById(R.id.ed_mkcu);
        ed_MKMoi = view.findViewById(R.id.ed_mkmoi);
        btn_DoiMk = view.findViewById(R.id.btn_doimk);
        dao = new QuanLyDAO(getActivity());
        btn_DoiMk.setOnClickListener(v->{
            int temp = 0;
            if (ed_TenDN.getText().toString().trim().isEmpty()){
                ed_TenDN.setError(getString(R.string.toast_value_date));
                temp++;
            }if (ed_MKCu.getText().toString().trim().isEmpty()){
                ed_MKCu.setError(getString(R.string.toast_value_date));
                temp++;
            }if (ed_MKMoi.getText().toString().trim().isEmpty()){
                ed_MKMoi.setError(getString(R.string.toast_value_date));
                temp++;
            }
            if (temp ==0){
                if (dao.checkLogin(ed_TenDN.getText().toString().trim(),ed_MKCu.getText().toString().trim()) > 0){
                    QuanLy quanLy = dao.getID(ed_TenDN.getText().toString().trim());
                    quanLy.setPassword(ed_MKMoi.getText().toString().trim());
                    dao.update(quanLy);
                    Toast.makeText(getActivity(), "Đổi Mật Khẩu Thành Công", Toast.LENGTH_SHORT).show();
                    replaceFragment(new FragmentSetting());
                }else{
                    Toast.makeText(getActivity(), "Tài Khoản Hoặc Mật Khẩu Không Đúng", Toast.LENGTH_SHORT).show();
                }
            }else {
                temp =0;
            }
        });

    }

    private  void replaceFragment(Fragment fragment) {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.viewpager, fragment)
                .commit();
    }
}