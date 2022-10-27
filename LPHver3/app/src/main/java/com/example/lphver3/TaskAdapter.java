package com.example.lphver3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<Task>  TaskList;
    SQLiteDatabase database;
    Database db;


    public TaskAdapter(MainActivity context, int layout, List<Task> taskList) {
        this.context = context;
        this.layout = layout;
        TaskList = taskList;
        db = new Database(context,Database.Name_Database,null,Database.Ver_Name_Database);
        database = db.getWritableDatabase();
    }

    @Override
    public int getCount() {
        return TaskList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtNoidung, txtNgay,txtGio;
        ImageView imgb1, imgb2;
        CheckBox cbdone;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;


        if(convertView == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.txtNoidung = convertView.findViewById(R.id.tvTask);
            holder.txtNgay = convertView.findViewById(R.id.tvNgay);
            holder.txtGio = convertView.findViewById(R.id.tvGio);
            holder.imgb1 = convertView.findViewById(R.id.ibDel);
            holder.imgb2 = convertView.findViewById(R.id.ibEdit);
            holder.cbdone = convertView.findViewById(R.id.cbdone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Task task = TaskList.get(position);

        holder.txtNoidung.setText(task.getNOIDUNG());
        holder.txtNgay.setText(task.getNGAY());
        holder.txtGio.setText(task.getGIO());
        if(task.getTINHTRANG() == 0)
        {
            holder.cbdone.setChecked(false);
        }
        else{
            holder.cbdone.setChecked(true);
            holder.txtNoidung.setPaintFlags(holder.txtNoidung.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.cbdone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    holder.txtNoidung.setPaintFlags(holder.txtNoidung.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    database.execSQL("Update " + Database.TABLE_Task + " set "+ Database.TinhTrang+ " = 1 where " +Database._ID+ " = " + task.get_Id()  );
                    //Toast.makeText(context, "Xoá " + task.get_Id(), Toast.LENGTH_SHORT).show();
                }else {
                    holder.txtNoidung.setPaintFlags(0);
                    database.execSQL("Update " + Database.TABLE_Task + " set "+ Database.TinhTrang+ " = 0 where " +Database._ID+ " = " + task.get_Id()  );
                }
            }
        });


        


        holder.imgb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Sửa " + task.getNOIDUNG(), Toast.LENGTH_SHORT).show();
                    context.Sua(task.get_Id());
            }
        });

        holder.imgb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Xoá " + task.getNOIDUNG(), Toast.LENGTH_SHORT).show();
                context.Xoa(task.getNOIDUNG(),task.get_Id());
            }
        });

        return convertView;
    }
}
