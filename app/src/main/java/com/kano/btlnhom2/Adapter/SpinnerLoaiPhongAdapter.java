package com.kano.btlnhom2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.kano.btlnhom2.DTO.LoaiPhong;
import com.kano.btlnhom2.R;

import java.util.ArrayList;

public class SpinnerLoaiPhongAdapter extends ArrayAdapter<LoaiPhong> {
    private Context context;
    private ArrayList<LoaiPhong> objects;
    TextView tvspnloaiphong;

    public SpinnerLoaiPhongAdapter(Context context, ArrayList<LoaiPhong> objects) {
        super(context, 0, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View holder = convertView;
        if (holder==null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            holder = inflater.inflate(R.layout.item_loai_phong_spinner,null);

        }
        final LoaiPhong obj = objects.get(position);
        if (obj != null){

            tvspnloaiphong = holder.findViewById(R.id.item_spn_loai_phong);
            tvspnloaiphong.setText(obj.getName());
        }
        return holder;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View holder = convertView;
        if (holder==null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            holder = inflater.inflate(R.layout.item_loai_phong_spinner,null);

        }
        final LoaiPhong obj = objects.get(position);
        if (obj != null){

            tvspnloaiphong = holder.findViewById(R.id.item_spn_loai_phong);
            tvspnloaiphong.setText(obj.getName());
        }
        return holder;
    }
}
