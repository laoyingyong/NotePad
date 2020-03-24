package com.example.databasetest.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MySQLiteOpenHelper extends SQLiteOpenHelper
{
    private String sql="create table Notepad(id integer primary key autoincrement,title text,body text,time text)";
    private Context mContext;

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(sql);
        Toast.makeText(mContext,"数据库创建成功！",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
