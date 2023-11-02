package com.kano.btlnhom2.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.kano.btlnhom2.DTO.QuanLy;
import com.kano.btlnhom2.Database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class QuanLyDAO {
    private SQLiteDatabase db;

    public QuanLyDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(QuanLy odj){
        ContentValues values = new ContentValues();
        values.put("id",odj.getId());
        values.put("name",odj.getName());
        values.put("password",odj.getPassword());
        return db.insert("Managers",null,values);
    }
    public long insertadmin(){
        ContentValues values = new ContentValues();
        values.put("id","admin");
        values.put("name","ADMIN");
        values.put("password","admin");
        return db.insert("Managers",null,values);
    }

    public long update(QuanLy odj){
        ContentValues values = new ContentValues();
        values.put("id",odj.getId());
        values.put("name",odj.getName());
        values.put("password",odj.getPassword());
        return db.update("ThuThu",values,"id=?",new String[]{String.valueOf(odj.getId())});
    }


    public List<QuanLy> getAll(){
        String sql = "SELECT * FROM Managers";
        return getData(sql);
    }

    public QuanLy getID(String id){
        String sql = "SELECT * FROM Managers WHERE id=?";
        List<QuanLy> list = getData(sql,id);
        return list.get(0);
    }

    public Boolean checkUsername(String id){
        String sql = "SELECT * FROM Managers WHERE id =? ";
        List<QuanLy> list = getData(sql, id);

        if (list.size() == 0)
            return false;
        else
            return true;

    }

    @SuppressLint("Range")
    private List<QuanLy> getData(String sql, String...selectionArgs){
        List<QuanLy> list = new ArrayList<QuanLy>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            QuanLy obj = new QuanLy();
            obj.setId(c.getString(c.getColumnIndex("id")));
            obj.setName(c.getString(c.getColumnIndex("name")));
            obj.setPassword(c.getString(c.getColumnIndex("password")));
            list.add(obj);
        }
        return list;
    }

    public int checkLogin(String id, String password){
        String sql = "SELECT * FROM Managers WHERE id=? AND password=?";
        List<QuanLy> list = getData(sql,id,password);
        if (list.size()==0){
            return -1;
        }
        return 1;
    }
}
