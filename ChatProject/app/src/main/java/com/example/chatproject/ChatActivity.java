package com.example.chatproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class ChatActivity extends AppCompatActivity {

    ListView m_ListView;
    CustomAdapter m_Adapter;
    EditText edit;
    Button btn;
    String sendMsg;
    Intent userintent;    // 수정할부분 (id받아오기->출력)
    Intent usrName;
    Intent mainIntent;

    private String userId=null;
    private String userName =null;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        btn=(Button)findViewById(R.id.button1);
        edit=(EditText)findViewById(R.id.editText1);
        m_Adapter = new CustomAdapter();
        m_ListView = (ListView) findViewById(R.id.listView1);
        m_ListView.setAdapter(m_Adapter);


        userintent = getIntent(); // 수정할부분 (id받아오기->출력)
        userId = userintent.getExtras().getString("Email");

        usrName = getIntent();
        userName = usrName.getExtras().getString("name");

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {    // send 버튼 눌렀을 시
                String time = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date());
                ChatData chatData = new ChatData(userName, userId ,edit.getText().toString(), time);
                chatData.setTime(time); // 메세지 보낼때의 시간 설정
                chatData.setUserId(userId);
                chatData.setUserName(userName);
                sendMsg=edit.getText().toString();  // 메세지 받아오기
                databaseReference.child("message").push().setValue(chatData); // firebase에서 message 차일드 생성
                edit.setText("");   // 메세지 보낸 후 텍스트창 비움
                m_Adapter.notifyDataSetChanged();   // 메세지 전송 후 바로 refresh
            }
        });

        databaseReference.child("message").addChildEventListener(new ChildEventListener() { // message 차일드 항목으로 firebase에 저장
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                String str = chatData.getMessage();
                String time = chatData.getTime();
                String getName = chatData.getUserName();
                String getId = chatData.getUserId();

                if(userId.equals(getId)) {  // 메세지 보냄
                    m_Adapter.add(getName, getId, str, 1, time);
                    m_Adapter.notifyDataSetChanged();
                }
                else {
                    m_Adapter.add(getName, getId, str, 0, time);
                    m_Adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
}