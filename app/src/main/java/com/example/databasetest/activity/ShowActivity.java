package com.example.databasetest.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databasetest.R;
import com.example.databasetest.util.BaseActivity;
import com.example.databasetest.util.Utils;

public class ShowActivity extends BaseActivity
{
    private EditText titleEt,bodyEt;
    private TextView timeTv;
    private Button button;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("日记详情");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(v.getContext(),ViewActivity.class);
                startActivity(intent);
            }
        });
        timeTv=findViewById(R.id.timeTv);
        titleEt=findViewById(R.id.titleEt);
        bodyEt=findViewById(R.id.bodyEt);
        button=findViewById(R.id.changeBtn);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        String time = intent.getStringExtra("time");
        final int id = intent.getIntExtra("id",1);
        timeTv.setText("创建日期："+time);
        titleEt.setText(title);
        bodyEt.setText(body);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String til = titleEt.getText().toString();
                String bod = bodyEt.getText().toString();
                SQLiteDatabase sqLiteDatabase = Utils.getSQLiteDatabase(v.getContext());
                String sql="update Notepad set title=?,body=? where id=?";
                sqLiteDatabase.execSQL(sql,new String[]{til,bod,""+id});
                Toast.makeText(v.getContext(),"修改成功！",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
