package com.kano.btlnhom2.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.kano.btlnhom2.Adapter.LoaiPhongAdapter;
import com.kano.btlnhom2.DAO.LoaiPhongDAO;
import com.kano.btlnhom2.DTO.LoaiPhong;
import com.kano.btlnhom2.R;
import com.kano.btlnhom2.click_interface.LoaiphongClick;

import java.util.ArrayList;
import java.util.List;


public class FragmentLoaiPhong extends Fragment {
    private RecyclerView rcv_loai_phong;
    private LoaiPhongDAO loaiphongDAO;
    private List<LoaiPhong> list=new ArrayList<>();
    private LoaiPhongAdapter adapter;
    private TextInputLayout ed_loaiphong;
    private Button btn_themlp,btn_huylp;
    private FloatingActionButton button;
    private LoaiPhong loaiphong;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_loai_phong, container, false);
        rcv_loai_phong=view.findViewById(R.id.rcv_loai_phong);
        button=view.findViewById(R.id.btn_add_loai_phong);
        loaiphongDAO= new LoaiPhongDAO(getContext());
        list=loaiphongDAO.getAll();
        adapter=new LoaiPhongAdapter(getContext(),list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialog();
            }
        });
        adapter.setLoaiphongClick(new LoaiphongClick() {
            @Override
            public void onClick(LoaiPhong loaiPhong) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loai_phong, null);
                builder.setView(view1);
                Dialog dialog = builder.create();
                Window window = dialog.getWindow();
                if(window==null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                ed_loaiphong=view1.findViewById(R.id.ed_loaiphong);
                btn_themlp=view1.findViewById(R.id.btn_themlp);
                btn_themlp.setText("Cập nhật");
                btn_huylp=view1.findViewById(R.id.btn_huylp);
                ed_loaiphong.getEditText().setText(loaiPhong.getName());
                btn_themlp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ed_loaiphong.getEditText().getText().length() == 0) {
                            Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                        } else {
                            loaiPhong.setName(ed_loaiphong.getEditText().getText().toString());
                            if (loaiphongDAO.update(loaiPhong) > 0) {
                                Toast.makeText(getContext(), "Cập nhật loại sách thành công", Toast.LENGTH_SHORT).show();
                                ed_loaiphong.getEditText().setText("");
                                list.clear();
                                list.addAll(loaiphongDAO.getAll());
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "Cập nhật loại sách thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                btn_huylp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
        rcv_loai_phong.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_loai_phong.setAdapter(adapter);
        return view;
    }
    private void opendialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loai_phong, null);
        builder.setView(view);

        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        ed_loaiphong=view.findViewById(R.id.ed_loaiphong);
        btn_themlp=view.findViewById(R.id.btn_themlp);
        btn_huylp=view.findViewById(R.id.btn_huylp);
        btn_themlp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_loaiphong.getEditText().getText().length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    loaiphong = new LoaiPhong();
                    loaiphong.setName(ed_loaiphong.getEditText().getText().toString());
                    if (loaiphongDAO.insert(loaiphong) > 0) {
                        Toast.makeText(getContext(), "Thêm loại phòng thành công", Toast.LENGTH_SHORT).show();
                        list.clear();
                        list.addAll(loaiphongDAO.getAll());
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm loại phòng thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btn_huylp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}