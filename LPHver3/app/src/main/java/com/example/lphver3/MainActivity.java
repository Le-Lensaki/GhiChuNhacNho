package com.example.lphver3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.core.view.MenuItemCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private FloatingActionButton fab;

    ListView lvTask;
    ArrayList<Task> tasks;
    TaskAdapter adapter;

    CalendarView calendarView;
    String selectedDate;


    SQLiteDatabase database;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTask = findViewById(R.id.lvTask);
        tasks = new ArrayList<>();

        adapter = new TaskAdapter(this,R.layout.item_task,tasks);
        lvTask.setAdapter(adapter);

        db = new Database(this,Database.Name_Database,null,Database.Ver_Name_Database);
        database = db.getWritableDatabase();

        //Xem task theo lịch
        calendarView = findViewById(R.id.calendarView);
        XemTasktheolich();





        //database.execSQL("Insert into " + Database.TABLE_Task + " Values (null, 'Làm bài tập 5','08/01/2022','10:55',1)");


        Intent a = new Intent(MainActivity.this, InfoTask.class);

        // Nut them
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(a);
            }
        });
    }

    public void XemTasktheolich(){
        calendarView.setVisibility(View.VISIBLE);
        tasks.clear();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        selectedDate = sdf.format(new Date(calendarView.getDate()));
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String years = String.valueOf(year);
                String months;
                String days = String.valueOf(day);;
                if(month < 10)
                {
                    months = String.valueOf(month + 1);
                    months = "0" + months;
                } else {
                    months = String.valueOf(month + 1);
                }
                if(day < 10)
                {
                    days = "0" + days;
                }
                selectedDate = days + "/" + months + "/" + years;
                getTask();
            }
        });
        getTask();
    }



    public  void Xoa(String nametask, int id){
        AlertDialog.Builder dialogxoa = new AlertDialog.Builder(this);
        dialogxoa.setMessage("Bạn có muốn xoá "+ nametask + " này không?");
        dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.execSQL("Delete from "+ Database.TABLE_Task + " where " + Database._ID + " = '"+ id +"'");
                getTask();
            }
        });
        dialogxoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialogxoa.show();
    }

    public void Sua(int id){
        Intent a = new Intent(MainActivity.this, InfoTask.class);
        a.putExtra("_id",id);
        startActivity(a);
    }

    public void getTask(){
        Cursor datadate = database.rawQuery("Select * from " + Database.TABLE_Task + " where " + Database.Ngay + " = '" + selectedDate +"'",null);
        tasks.clear();
        while (datadate.moveToNext())
        {
            String noidung = datadate.getString(1);
            String ngay = datadate.getString(2);
            String gio = datadate.getString(3);
            int tinhtrang = datadate.getInt(4);
            int id = datadate.getInt(0);
            tasks.add(new Task(id,noidung,ngay,gio, tinhtrang));
        }

        adapter.notifyDataSetChanged();
    }

    public void searchInfoTask(String newtext)
    {
        Cursor datadate = database.rawQuery("Select * from " + Database.TABLE_Task + " where " + Database.NoiDung + " like '%" + newtext +"%'",null);
        tasks.clear();
        while (datadate.moveToNext())
        {
            String noidung = datadate.getString(1);
            String ngay = datadate.getString(2);
            String gio = datadate.getString(3);
            int tinhtrang = datadate.getInt(4);
            int id = datadate.getInt(0);
            tasks.add(new Task(id,noidung,ngay,gio, tinhtrang));
        }

        adapter.notifyDataSetChanged();
    }


    public void getallTask(){
        Cursor data = database.rawQuery("Select * from " + Database.TABLE_Task, null);
        tasks.clear();
        while (data.moveToNext())
        {
            String noidung = data.getString(1);
            String ngay = data.getString(2);
            String gio = data.getString(3);
            int tinhtrang = data.getInt(4);
            int id = data.getInt(0);
            tasks.add(new Task(id,noidung,ngay,gio, tinhtrang));
        }

        adapter.notifyDataSetChanged();
    }



// Tạo Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);


        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView =(SearchView) menuItem.getActionView();
        searchView.setQueryHint("Hãy nhập nội dung cần tìm");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchInfoTask(query);
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                searchInfoTask(newText);
                return false;
            }
        });


        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                XemTasktheolich();
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:

                    calendarView.setVisibility(View.GONE);
                    getallTask();

                return true;
            case R.id.about:
                Intent tat = new Intent(MainActivity.this,Alarm.class);
                tat.putExtra("extra","off");
                sendBroadcast(tat);
                return true;
            case R.id.help:
                Toast.makeText(this, "Chưa cập nhật", Toast.LENGTH_SHORT).show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}