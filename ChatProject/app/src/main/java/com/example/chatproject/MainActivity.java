package com.example.chatproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView Chat = null;
    ImageView Challendar = null;
    Intent chatIntent;
    Intent calendarIntent;
    Intent userintent;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userintent = getIntent(); // 수정할부분 (id받아오기->출력)
        userId = userintent.getExtras().getString("Email");



        Chat = (ImageView)findViewById(R.id.chat);
        Challendar = (ImageView)findViewById(R.id.chllendar);

        Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userintent = new Intent(getApplicationContext(), ChatActivity.class);
                userintent.putExtra("Email",userId);
                startActivity(userintent);

            }
        });

        Challendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarIntent = new Intent(getApplicationContext(), ScheduleActivity.class);
                startActivity(calendarIntent);
            }
        });
    }
}
