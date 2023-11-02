package com.kano.btlnhom2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kano.btlnhom2.LoginActivity;
import com.kano.btlnhom2.R;

public class FragmentSetting extends Fragment {
    Button btnDangXuat;
    Button btnThongKe;
    Button btnDoiMatKhau;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnDangXuat = view.findViewById(R.id.btnDX);
        btnThongKe = view.findViewById(R.id.btnThongKe);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);


        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentThongKe fragmentThongKe = new FragmentThongKe();
                getChildFragmentManager().beginTransaction()
                        .add(R.id.fragment_setting, fragmentThongKe)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentDoiMatKhau exampleFragment = new FragmentDoiMatKhau();
                fragmentTransaction.add(R.id.fragment_setting, exampleFragment);
                fragmentTransaction.addToBackStack("example_fragment");
                fragmentTransaction.commit();
            }
        });

    }
}