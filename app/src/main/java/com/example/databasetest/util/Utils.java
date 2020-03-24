package com.example.databasetest.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.databasetest.dao.MySQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils
{
    private static SQLiteDatabase sqLiteDatabase;

    public static SQLiteDatabase getSQLiteDatabase(Context context)
    {
        MySQLiteOpenHelper mydb = new MySQLiteOpenHelper(context, "mydb", null, 1);
        sqLiteDatabase = mydb.getReadableDatabase();
        return sqLiteDatabase;
    }

    public static String getTimeStr(Date date)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }

    /**
     * 把字符串解析成Date对象
     * @param str
     * @return
     */
    public static Date parseDate(String str)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse=null;
        try
        {
            parse = simpleDateFormat.parse(str);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return parse;
    }
}
