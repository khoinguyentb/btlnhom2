package com.kano.btlnhom2.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.kano.btlnhom2.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    private SQLiteDatabase db;
    private Context context;

    public ThongKeDAO(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public int getDoanhThuTongTien(String tuNgay, String denNgay){
        String getDoanhThuTongTien = "SELECT SUM(bill_total) as doanhThuTongTien FROM Bills WHERE bill_date BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(getDoanhThuTongTien,new String[]{tuNgay,denNgay});
        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThuTongTien"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }

    @SuppressLint("Range")
    public int getDoanhThuTongTienDichVu(String tuNgay, String denNgay){
        String getDoanhThuTongTienDichVu = "SELECT SUM(total) as doanhThuDichVu FROM Service_bills WHERE service_date BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(getDoanhThuTongTienDichVu,new String[]{tuNgay,denNgay});
        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThuDichVu"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }

    
    @SuppressLint("Range")
    public int getDoanhThuTongTienDenBu(String tuNgay, String denNgay){
        String getDoanhThuTongTienDenBu = "SELECT SUM(lost_total) as tienDenBu FROM Bills WHERE bill_date BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(getDoanhThuTongTienDenBu,new String[]{tuNgay,denNgay});
        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("tienDenBu"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }
}
