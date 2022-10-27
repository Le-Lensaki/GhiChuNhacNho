package com.example.lphver3;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlarmManager;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class InfoTask extends AppCompatActivity {

    private EditText edtTask, edtLich, edtTime;
    private Button btn1;

    private CheckBox cbdone;

    private MainActivity context1;

    AlarmManager alarm;

    PendingIntent pendingIntent;



    Calendar cal;



    SQLiteDatabase database;
    Database db;
    Task task123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_task);

        db = new Database(this,Database.Name_Database,null,Database.Ver_Name_Database);
        database = db.getWritableDatabase();

        edtTask = findViewById(R.id.edtTask);
        edtLich = findViewById(R.id.edtLich);
        edtTime = findViewById(R.id.edtTime);
        cbdone = findViewById(R.id.cbDone);

        btn1 = findViewById(R.id.save);

        alarm   = (AlarmManager) getSystemService(ALARM_SERVICE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Intent intentalarm = new Intent(InfoTask.this, Alarm.class);

        Intent getMain = getIntent();
        int id = getMain.getIntExtra("_id",-1);


        Cursor data1234 = database.rawQuery("Select * from " + Database.TABLE_Task + " where "+ Database._ID + " = " + id, null);


        if(data1234 != null) {
            if(data1234.moveToFirst()) {
                edtTask.setText(data1234.getString(1));
                edtLich.setText(data1234.getString(2));
                edtTime.setText(data1234.getString(3));
                if(data1234.getInt(4) == 0)
                {
                    cbdone.setChecked(false);
                }
                else{
                    cbdone.setChecked(true);
                }

            }
        }




        //click chọn ngày
        edtLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });

        //click chọn thời gian
        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonGio();
            }
        });



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noidung = edtTask.getText().toString();
                String lich = edtLich.getText().toString();
                String gio = edtTime.getText().toString();
                int tinhtrang = 0;

                if(cbdone.isChecked())
                {
                    tinhtrang = 1;
                }
                else{
                    tinhtrang = 0;
                }

                if(id == -1) {

                database.execSQL("Insert into " + Database.TABLE_Task + " Values (null, '"+ noidung +"', '"+ lich +"', '"+ gio +"', " + tinhtrang + ")");

                Toast.makeText(getApplication(), "" + cal.getTime() , Toast.LENGTH_SHORT).show();

                intentalarm.putExtra("extra","on");
                intentalarm.putExtra("noidung",noidung);

                //Gọi qua class Alarm
                pendingIntent = PendingIntent.getBroadcast(
                        InfoTask.this,0,intentalarm,PendingIntent.FLAG_UPDATE_CURRENT
                );
                alarm.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);


                //Đóng InfoTask và gọi qua MainActivity cập nhật lại
                Intent refresh = new Intent(InfoTask.this , MainActivity.class);
                startActivity(refresh);
                finish();
                } else {

                    database.execSQL("Update " + Database.TABLE_Task + " set " + Database.NoiDung + " = '"+ noidung + "', " +Database.Ngay+ " = '" +lich+ "', "
                            +Database.Gio+ " = '" + gio + "', "+ Database.TinhTrang+ " = " +tinhtrang+
                        " where " +Database._ID+ " = " + id  );

                    Toast.makeText(getApplication(), "Cập nhật " + noidung  , Toast.LENGTH_SHORT).show();

                    intentalarm.putExtra("extra","on");
                    intentalarm.putExtra("noidung",noidung);
                    intentalarm.putExtra("id", id);
                    //Gọi qua class Alarm
                    pendingIntent = PendingIntent.getBroadcast(
                            InfoTask.this,0,intentalarm,PendingIntent.FLAG_UPDATE_CURRENT
                    );
                    alarm.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);


                    //Đóng InfoTask và gọi qua MainActivity cập nhật lại
                    Intent refresh = new Intent(InfoTask.this , MainActivity.class);
                    startActivity(refresh);
                    finish();
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    // Chọn ngày
     private void ChonNgay(){
         cal = Calendar.getInstance();
         int ngay = cal.get(Calendar.DATE);
         int thang = cal.get(Calendar.MONTH);
         int nam = cal.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtLich.setText(simpleDateFormat.format(cal.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
     }


     // Chọn giờ
    private void ChonGio(){
        cal = Calendar.getInstance();
        int gio = cal.get(Calendar.HOUR_OF_DAY);
        int phut = cal.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),hourOfDay,minute);
                edtTime.setText(simpleDateFormat.format(cal.getTime()));
            }
        },gio,phut, true);
        timePickerDialog.show();
    }




}