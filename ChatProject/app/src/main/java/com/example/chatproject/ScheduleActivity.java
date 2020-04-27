package com.example.chatproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ScheduleActivity extends AppCompatActivity {

    Toolbar toolBar;
    CalendarView cal;
    ListView lv;
    ScheduleAdapter s_adapter;
    String s_year, s_month, s_day, s_ymd, s_content;
    String date, content;
    ArrayList<S_item> items;
    S_item item;

    int token;
    long nowDate;
    Date nDate;
    String s_nDate;
    SimpleDateFormat fDate = new SimpleDateFormat("yyyy년 MM월 dd일");

    Map<String, Object> taskMap = new HashMap<String, Object>();


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        toolBar = (Toolbar) findViewById(R.id.my_toolbar);
       // setSupportActionBar(toolBar);

        items = new ArrayList<S_item>();
        item = new S_item(date, content);
        cal = (CalendarView) findViewById(R.id.Calendar);
        lv = (ListView) findViewById(R.id.listview);
        s_adapter = new ScheduleAdapter();


        nowDate = System.currentTimeMillis();
        nDate = new Date(nowDate);
        s_nDate = fDate.format(nDate);

        lv.setAdapter(s_adapter);

        final EditText edit = new EditText(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "항목을 길게 누르면 삭제 할 수 있습니다!",
                        Toast.LENGTH_LONG).show();
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                s_adapter.remove(position);
                S_item item = new S_item(s_ymd,s_content);
                date = item.getDate();
                content = item.getContent();
                ref.child("schedule").removeValue();

                return true;
            }
        });


        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                s_year = Integer.toString(year);
                if(month < 10){
                    s_month = "0"+Integer.toString(month+1);
                }
                else
                    s_month = Integer.toString(month+1);
                if (dayOfMonth < 10) {

                    s_day = "0"+Integer.toString(dayOfMonth);

                }else{
                    s_day = Integer.toString(dayOfMonth);
                }
                s_ymd = s_year + "년" + " "+ s_month + "월" + " " +
                        s_day + "일";
            }
        });


        ref.child("schedule").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                S_item item = dataSnapshot.getValue(S_item.class);
                date = item.getDate();
                content = item.getContent();
                s_adapter.add(date, content);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                S_item item = dataSnapshot.getValue(S_item.class);
                date = item.getDate();
                content = item.getContent();
                s_adapter.modify(item);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final EditText editText = new EditText(this);
        switch (item.getItemId()) {
            case R.id.menu_plus:
                AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
                adBuilder.setMessage("내용을 입력하세요!");
                adBuilder.setView(editText);
                adBuilder.setPositiveButton("추가",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(s_ymd==null)
                                    s_ymd = s_nDate;
                                s_content = editText.getText().toString();
                                S_item item = new S_item(s_ymd, s_content);
                                item.setDate(s_ymd);
                                item.setContent(s_content);
                                ref.child("schedule").push().setValue(item);

                            }
                        });
                adBuilder.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                adBuilder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}



