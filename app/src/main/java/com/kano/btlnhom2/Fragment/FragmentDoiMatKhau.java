package com.kano.btlnhom2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.kano.btlnhom2.R;


public class FragmentDoiMatKhau extends Fragment {
    public FragmentDoiMatKhau() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_doimatkhau, container, false);
    }
}