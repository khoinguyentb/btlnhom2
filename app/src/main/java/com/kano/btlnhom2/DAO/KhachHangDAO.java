package com.kano.btlnhom2.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kano.btlnhom2.DTO.KhachHang;
import com.kano.btlnhom2.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    DbHelper dbHelper ;
    SQLiteDatabase db ;
    public KhachHangDAO(Context context){
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public long insert(KhachHang khachHang){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",khachHang.getName());
        contentValues.put("phone_number",khachHang.getPhone());
        contentValues.put("birthday",String.valueOf(khachHang.getBirthday()));
        long res = db.insert("Guests",null,contentValues);
        return res ;
    }
    public long update(KhachHang khachHang){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",khachHang.getName());
        contentValues.put("phone_number",khachHang.getPhone());
        contentValues.put("birthday",String.valueOf(khachHang.getBirthday()));
        long res = db.update("Guests",contentValues,"id=?",new String[]{khachHang.getId()+""});
        return res ;
    }
    public int delete(int id){
        Cursor cursor = db.rawQuery("SELECT * FROM Bills WHERE guest_id = ?",new String[]{String.valueOf(id)});
        if (cursor.getCount()!=0){
            return -1 ;
        }
        long  check = db.delete("Guests","id=?",new String[]{String.valueOf(id)});
        if(check==-1){
            return  0 ;
        }
        return 1 ;
    }
    public ArrayList<KhachHang> getAll(){
//        ArrayList<KhachHang> list = new ArrayList<>();
//        Cursor cursor = db.rawQuery("SELECT * FROM Guests",null);
//        if(cursor.getCount()!=0){
//            cursor.moveToFirst();
//            do {
//                list.add(new KhachHang(cursor.getInt(0),cursor.getString(1),
//                        cursor.getInt(2),cursor.getString(3)));
//            }while (cursor.moveToNext());
//        }
//        return list;

        String sql="SELECT * FROM Guests";
        return (ArrayList<KhachHang>) getData(sql);
    }

    public KhachHang getID(String id){
        String sql = "SELECT * FROM Guests WHERE id=?";
        List<KhachHang> list = getData(sql,id);
        return list.get(0);
    }

    @SuppressLint("Range")
    private List<KhachHang> getData(String sql, String...selectionArgs) {

        List<KhachHang> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            KhachHang obj = new KhachHang();
            obj.setId(Integer.parseInt(c.getString(c.getColumnIndex("id"))));
            obj.setName(c.getString(c.getColumnIndex("name")));
            obj.setPhone(c.getString(c.getColumnIndex("phone_number")));
            obj.setBirthday(c.getString(c.getColumnIndex("birthday")));

            list.add(obj);
        }
        return list;
    }
}
