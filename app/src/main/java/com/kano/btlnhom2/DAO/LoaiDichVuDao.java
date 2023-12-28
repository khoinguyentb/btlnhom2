package com.kano.btlnhom2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.kano.btlnhom2.DTO.LoaiDichVu;

import com.kano.btlnhom2.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class LoaiDichVuDao {
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public LoaiDichVuDao(Context context){
        dbHelper=new DbHelper(context);
        db=dbHelper.getWritableDatabase();
    }
    public long insert(LoaiDichVu loaiDichVu){
        ContentValues values=new ContentValues();
        values.put("name",loaiDichVu.getName());
        values.put("price",loaiDichVu.getPrice());
        return db.insert("Services",null,values);
    }
    public long Update(LoaiDichVu loaiDichVu){
        ContentValues values=new ContentValues();
        values.put("name",loaiDichVu.getName());
        values.put("price",loaiDichVu.getPrice());
        return db.update("Services",values,"id=?",new String[]{String.valueOf(loaiDichVu.getId())});
    }
//    public long delete(String id){
//        return db.delete("Services","id=?",new String[]{id});
//    }

    public int delete(int id){
        Cursor cursor = db.rawQuery("SELECT * FROM Service_bills WHERE service_id=?",new String[]{String.valueOf(id)});
        if (cursor.getCount()!=0){
            return -1 ;
        }
            long check = db.delete("Services","id=?",new String[]{String.valueOf(id)});
        if(check==-1){
            return  0 ;
        }
        return 1 ;
    }

    public List<LoaiDichVu> getDaTa(String sql, String...selectionArgs){
        List<LoaiDichVu> list=new ArrayList<>();
        Cursor c=db.rawQuery(sql,selectionArgs);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int a = c.getInt(0);
                String b = c.getString(1);
                int price = c.getInt(2);
                list.add(new LoaiDichVu(a,b,price));
                c.moveToNext();
            }
            c.close();
        }
        return list;
    }
    public List<LoaiDichVu> getAll(){
        String sql="select * from Services";
        return getDaTa(sql);
    }

    public LoaiDichVu getID(String id){
        String sql="select * from Services where id=?";
        List<LoaiDichVu> list = getDaTa(sql,id);
        return list.get(0);
    }
}
