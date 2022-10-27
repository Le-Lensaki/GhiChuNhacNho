package com.example.lphver3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final int Ver_Name_Database = 1;

    public static final String Name_Database = "taskdb";

    public static final String TABLE_Task = "tbl_task";

    public static final String _ID = "_id";
    public static final String Loai = "loai";
    public static final String NoiDung = "noidung";
    public static final String Ngay = "ngay";
    public static final String Gio = "gio";
    public static final String TinhTrang = "tinhtrang";

    private static final String TAO_BANG_TASK =
            "create table if not exists " + TABLE_Task  + "("
            + _ID + " integer primary key autoincrement,"
            + NoiDung + " text not null,"
            + Ngay + " text not null,"
            + Gio + " text not null,"
            + TinhTrang + " INTEGER not null ); " ;
    private static final String TAOTASK = "create table if not exists tbl_task(_id integer primary key autoincrement, noidung text not null, ngay text not null, gio text not null)";


    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Name_Database, null, Ver_Name_Database);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TAO_BANG_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
