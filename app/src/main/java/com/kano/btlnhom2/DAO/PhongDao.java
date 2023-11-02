package com.kano.btlnhom2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.kano.btlnhom2.DTO.Phong;
import com.kano.btlnhom2.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class PhongDao {
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    public PhongDao(Context context) {
        dbHelper=new DbHelper(context);
        db=dbHelper.getWritableDatabase();
    }
    public long insert(Phong phong){
        ContentValues values=new ContentValues();
        values.put("name",phong.getName());
        values.put("room_type_id",phong.getRoom_type_id());
        values.put("price",phong.getPrice());
        values.put("status",phong.getStatus());
        return db.insert("Rooms",null,values);
    }
    public long update(Phong phong) {
        ContentValues values = new ContentValues();
        values.put("name", phong.getName());
        values.put("room_type_id", phong.getRoom_type_id());
        values.put("price", phong.getPrice());
        values.put("status", phong.getStatus());
        return db.update("Rooms", values,"id= ?",new String[]{phong.getId()+""});
    }






    public List<Phong> getDaTa(String sql, String...selectionArgs){
        List<Phong> list=new ArrayList<>();
        Cursor c=db.rawQuery(sql,selectionArgs);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int a = c.getInt(0);
                int d= c.getInt(1);
                String b = c.getString(2);
                int g=c.getInt(3);
                int p = c.getInt(4);
                list.add(new Phong(a,b,d,g,p));
                c.moveToNext();
            }
            c.close();
        }
        return list;
    }
    public List<Phong> getAll(){
        String sql="select * from Rooms";
        return getDaTa(sql);
    }
    public Phong getID(String id){
        String sql="select * from Rooms where id=?";
        List<Phong> list=getDaTa(sql,id);
        return list.get(0);
    }



//    String sql="SELECT * FROM Rooms WHERE name = " +
//            "(SELECT name FROM Rooms " +
//            "WHERE id = (SELECT room_id FROM Bills  " +
//            "WHERE (start_date NOT BETWEEN ? AND ?) " +
//            "AND (end_date NOT BETWEEN ? AND ?)))";

//    public List<Phong> getAllIdDaDatPhong(String tuNgayBD, String denNgayBD,String tuNgayKT,String denNgayKT){
//        String sql="SELECT * FROM Bills "+
//                "WHERE (start_date NOT BETWEEN ? AND ?) " +
//                "AND (end_date NOT BETWEEN ? AND ?) )";
//        List<Phong> list = getDaTa(sql,tuNgayBD,denNgayBD,tuNgayKT,denNgayKT);
//        return list;
//    }

    public int delete(int id){
        Cursor cursor = db.rawQuery("SELECT * FROM Bills WHERE room_id=?",new String[]{String.valueOf(id)});
        if (cursor.getCount()!=0){
            return -1 ;
        }
        long  check = db.delete("Rooms","id=?",new String[]{String.valueOf(id)});
        if(check==-1){
            return  0 ;
        }
        return 1 ;
    }





}
