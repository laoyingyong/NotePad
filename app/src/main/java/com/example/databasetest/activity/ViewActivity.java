package com.example.databasetest.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.databasetest.R;
import com.example.databasetest.domain.Note;
import com.example.databasetest.util.BaseActivity;
import com.example.databasetest.util.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewActivity extends BaseActivity
{

    private List<Note> noteList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("日记一览");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(v.getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        initNotes();//初始化日记
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter adapter = new MyAdapter(noteList);
        recyclerView.setAdapter(adapter);
    }

    private void initNotes()
    {
        SQLiteDatabase sqLiteDatabase = Utils.getSQLiteDatabase(this);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Notepad", null);
        while (cursor.moveToNext())
        {
            Note note=new Note();
            String string1 = cursor.getString(0);
            String string2 = cursor.getString(1);//标题
            String string3 = cursor.getString(2);
            String string4 = cursor.getString(3);//时间

            note.setTitle(string2);
            Date parse=Utils.parseDate(string4);
            note.setTime(parse);
            note.setBody(string3);
            note.setId(Integer.parseInt(string1));
            noteList.add(note);
        }
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private List<Note> noteList;
    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View noteView;
        TextView noteName;
        TextView timeTv;
        public ViewHolder(View view)
        {
            super(view);
            noteView=view;
            timeTv =  view.findViewById(R.id.timeTv);
            noteName = (TextView) view.findViewById(R.id.note_name);
        }
    }
    public MyAdapter(List<Note> list)
    {
        noteList = list;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.note_item, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.noteView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = holder.getAdapterPosition();
                Note note = noteList.get(position);
                int id=note.getId();
                SQLiteDatabase sqLiteDatabase = Utils.getSQLiteDatabase(v.getContext());
                Cursor cursor = sqLiteDatabase.rawQuery("select * from Notepad where id=?", new String[]{"" + id});
                cursor.moveToNext();
                int id2 = cursor.getInt(0);
                Intent intent=new Intent(v.getContext(),ShowActivity.class);
                intent.putExtra("title",cursor.getString(1));
                intent.putExtra("body",cursor.getString(2));
                intent.putExtra("time",Utils.getTimeStr(note.getTime()));
                intent.putExtra("id",id2);
                v.getContext().startActivity(intent);

            }
        });
        holder.timeTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                int position = holder.getAdapterPosition();
                Note note = noteList.get(position);
                final int id = note.getId();
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("警告");
                builder.setMessage("您确定要删除该日记吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        SQLiteDatabase sqLiteDatabase = Utils.getSQLiteDatabase(v.getContext());
                        sqLiteDatabase.execSQL("delete from Notepad where id=?",new String[]{""+id});
                        Toast.makeText(v.getContext(),"删除成功！",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                builder.setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position)
    {
        Note note = noteList.get(position);
        holder.noteName.setText(note.getTitle());
        Date date=note.getTime();
        String timeStr = Utils.getTimeStr(date);
        holder.timeTv.setText(timeStr);

    }

    @Override
    public int getItemCount()
    {
        return noteList.size();
    }
}
