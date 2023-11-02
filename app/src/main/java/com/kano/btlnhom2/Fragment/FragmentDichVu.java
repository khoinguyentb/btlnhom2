package com.kano.btlnhom2.Fragment;

import android.app.AlertDialog;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.kano.btlnhom2.Adapter.LoaiDichVuAdapter;
import com.kano.btlnhom2.DAO.LoaiDichVuDao;
import com.kano.btlnhom2.DTO.LoaiDichVu;
import com.kano.btlnhom2.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentDichVu extends Fragment {
    private FloatingActionButton button;
    private RecyclerView recyclerView;
    private List<LoaiDichVu> list = new ArrayList<>();
    private LoaiDichVuAdapter adapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dich_vu, container, false);
       button = view.findViewById(R.id.btn_add_dich_vu);
       recyclerView = view.findViewById(R.id.rcv_loai_dich_vu);
       adapter = new LoaiDichVuAdapter(getActivity());

        LoaiDichVuDao dichVuDao = new LoaiDichVuDao(getContext());
        list = dichVuDao.getAll();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        adapter.setList(list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialog();
            }
        });
        return view;
    }

    private void opendialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loai_dich_vu, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowacc = window.getAttributes();
        windowacc.gravity = Gravity.NO_GRAVITY ;
        window.setAttributes(windowacc);
        dialog.show();
        TextInputLayout ed_loaidv =view.findViewById(R.id.ed_loaidichvu);
        TextInputLayout ed_loaidvPrice =view.findViewById(R.id.ed_loaidichvuPrice);
        Button btn_themdv =view.findViewById(R.id.btn_themdv);
        Button btn_huydv =view.findViewById(R.id.btn_huydv);
        LoaiDichVuDao loaiDichVuDao = new LoaiDichVuDao(getActivity());
        btn_themdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_loaidv.getEditText().getText().length() == 0 || ed_loaidvPrice.getEditText().getText().length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    LoaiDichVu loaiDichVu = new LoaiDichVu();
                    loaiDichVu.setName(ed_loaidv.getEditText().getText().toString());
                    loaiDichVu.setPrice(Integer.parseInt(ed_loaidvPrice.getEditText().getText().toString()));
                    if (loaiDichVuDao.insert(loaiDichVu) > 0) {
                        Toast.makeText(getContext(), "Thêm loại dịch vụ thành công", Toast.LENGTH_SHORT).show();
                        ed_loaidv.getEditText().setText("");
                        ed_loaidvPrice.getEditText().setText("");
                        list.clear();
                        list.addAll(loaiDichVuDao.getAll());
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm loại dịch vụ thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btn_huydv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}