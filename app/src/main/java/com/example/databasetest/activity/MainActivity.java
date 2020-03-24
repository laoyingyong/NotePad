package com.example.databasetest.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Process;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.databasetest.R;
import com.example.databasetest.util.ActivityCollector;
import com.example.databasetest.util.BaseActivity;
import com.example.databasetest.util.Utils;

import java.util.Date;

public class MainActivity extends BaseActivity implements View.OnClickListener
{
    private EditText titleEt;
    private  EditText bodyEt;
    private Button saveBtn;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =findViewById(R.id.toolbar);
        toolbar.setTitle("新建日记");
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.look:
                        Intent intent=new Intent(MainActivity.this,ViewActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.exit:
                        ActivityCollector.finishAll();
                        Process.killProcess(Process.myPid());//杀掉当前进程
                        break;
                    default:
                }
                return false;
            }
        });

        titleEt=findViewById(R.id.titleEt);
        bodyEt=findViewById(R.id.bodyEt);
        saveBtn=findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
        sqLiteDatabase = Utils.getSQLiteDatabase(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.saveBtn:
                String title = titleEt.getText().toString();
                String body = bodyEt.getText().toString();
                Date date = new Date();
                String timeStr = Utils.getTimeStr(date);
                sqLiteDatabase.execSQL("insert into Notepad values (?,?,?,?)",new String [] {null,title,body,timeStr});
                Toast.makeText(this,"保存成功！",Toast.LENGTH_SHORT).show();
                break;
                default:
        }
    }

}
