package com.kano.btlnhom2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kano.btlnhom2.LanguageActivity;
import com.kano.btlnhom2.LoginActivity;
import com.kano.btlnhom2.R;

public class FragmentSetting extends Fragment {
    private LinearLayout btnDangXuat;
    private LinearLayout btnThongKe;
    private LinearLayout btnDoiMatKhau;
    private LinearLayout btnNgonNgu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnDangXuat = view.findViewById(R.id.btnDX);
        btnThongKe = view.findViewById(R.id.btnThongKe);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);
        btnNgonNgu = view.findViewById(R.id.btn_lang);

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentThongKe());
            }
        });

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentDoiMatKhau());
            }
        });

        btnNgonNgu.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), LanguageActivity.class);
            startActivity(intent);
        });

    }

    private  void replaceFragment(Fragment fragment) {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.content_frag, fragment)
                .commit();
    }
}