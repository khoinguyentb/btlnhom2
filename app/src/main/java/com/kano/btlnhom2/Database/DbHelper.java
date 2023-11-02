package com.kano.btlnhom2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String dbName = "LovelyHotel";
    static final int dbVersion = 1;
    public DbHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableManagers = "create table Managers (" +
                "id TEXT PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "password TEXT NOT NULL )";
        db.execSQL(createTableManagers);

        String createTableGuests = "create table Guests (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "phone_number TEXT NOT NULL," +
                "birthday DATE NOT NULL )";
        db.execSQL(createTableGuests);

        String createTableRooms = "create table Rooms (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "room_type_id INTERGER REFERENCES Room_Types(id)," +
                "name TEXT NOT NULL," +
                "price INTERGER NOT NULL ," +
                "status INTERGER NOT NULL)";
        db.execSQL(createTableRooms);

        String createTableRoom_Types = "create table Room_Types (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL)";
        db.execSQL(createTableRoom_Types);


        String createTableBills = "create table Bills (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "manager_id TEXT REFERENCES Managers(id)," +
                "guest_id INTEGER REFERENCES Guests(id)," +
                "room_id INTEGER REFERENCES Rooms(id)," +
                "start_date DATE NOT NULL," +
                "end_date DATE NOT NULL," +
                "status INTEGER NOT NULL," +
                "note TEXT ," +
                "bill_date DATE NOT NULL," +
                "lost_total INTEGER NOT NULL," +
                "service_total INTERGER NOT NULL," +
                "room_total INTERGER NOT NULL," +
                "bill_total INTEGER NOT NULL)";
        db.execSQL(createTableBills);

        String createTableBill_Details = "create table Service_bills (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "bill_id INTEGER REFERENCES Bills(id)," +
                "service_id INTEGER REFERENCES Services(id)," +
                "service_quantity INTEGER NOT NULL," +
                "service_date Date NOT NULL," +
                "total INTEGER NOT NULL)";
        db.execSQL(createTableBill_Details);

        String createTableServices = "create table Services (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "price INTEGER NOT NULL)";
        db.execSQL(createTableServices);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Managers");
        db.execSQL("DROP TABLE IF EXISTS Guests");
        db.execSQL("DROP TABLE IF EXISTS Rooms");
        db.execSQL("DROP TABLE IF EXISTS Room_Types");
        db.execSQL("DROP TABLE IF EXISTS Bills");
        db.execSQL("DROP TABLE IF EXISTS Bill_Details");
        db.execSQL("DROP TABLE IF EXISTS Services");
        onCreate(db);

    }
}
