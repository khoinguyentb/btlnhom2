package com.kano.btlnhom2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.kano.btlnhom2.DTO.LoaiPhong;
import com.kano.btlnhom2.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class LoaiPhongDAO {
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public LoaiPhongDAO(Context context){
        dbHelper=new DbHelper(context);
        db=dbHelper.getWritableDatabase();
    }
    public long insert(LoaiPhong loaiphong){
        ContentValues values=new ContentValues();
        values.put("name",loaiphong.getName());
        return db.insert("Room_Types",null,values);
    }
    public int update(LoaiPhong loaiPhong){
        ContentValues values=new ContentValues();
        values.put("name",loaiPhong.getName());
        return db.update("Room_Types",values,"id=?",new String[]{loaiPhong.getId()+""});
    }

//    public int delete(int s){
//        return db.delete("Room_Types","id=?",new String[]{s+""});
//    }

    public int delete(int id){
        Cursor cursor = db.rawQuery("SELECT * FROM Rooms WHERE room_type_id=?",new String[]{String.valueOf(id)});
        if (cursor.getCount()!=0){
            return -1 ;
        }
        long check = db.delete("Room_Types","id=?",new String[]{String.valueOf(id)});
        if(check==-1){
            return  0 ;
        }
        return 1 ;
    }

    public List<LoaiPhong> getDaTa(String sql, String...selectionArgs){
        List<LoaiPhong> list=new ArrayList<>();
        Cursor c=db.rawQuery(sql,selectionArgs);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int a = c.getInt(0);
                String b = c.getString(1);
                list.add(new LoaiPhong(a,b));
                c.moveToNext();
            }
            c.close();
        }
        return list;
    }
    public List<LoaiPhong> getAll(){
        String sql="select * from Room_Types";
        return getDaTa(sql);
    }

    public LoaiPhong getID(String id){
        String sql = "SELECT * FROM Room_Types WHERE id=?";
        List<LoaiPhong> list = getDaTa(sql,id);
        return list.get(0);
    }

}
