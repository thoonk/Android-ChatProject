package com.example.chatproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class JoinActivity extends AppCompatActivity {

    int version =2;
    dbHelper helper;
    SQLiteDatabase db;
    String sql;
    Cursor cursor;


    private Button joinbtn;
    private Button cancel;
    private EditText email;
    private EditText pw;
    private EditText pwchk;
    private EditText name;
    private RadioGroup rdGroup;
    private RadioButton mBtn;
    private RadioButton fmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        helper = new dbHelper(JoinActivity.this, dbHelper.tableName,null,version);
        cancel=(Button)findViewById(R.id.cancel);
        email=(EditText) findViewById(R.id.email);
        pw=(EditText) findViewById(R.id.pw);
        pwchk=(EditText) findViewById(R.id.pwchk);
        name = (EditText)findViewById(R.id.name);
        rdGroup = (RadioGroup)findViewById(R.id.rdgrp);
        mBtn = (RadioButton)findViewById(R.id.maleRadio);
        fmBtn = (RadioButton)findViewById(R.id.femaleRadio);

        try{
            db= helper.getWritableDatabase();
        }catch(SQLiteException ex){
            db = helper.getReadableDatabase();
        }

        pwchk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = pw.getText().toString();
                String check = pwchk.getText().toString();

                if(password.equals(check)){
                    pw.setBackgroundColor(Color.GREEN);
                    pwchk.setBackgroundColor(Color.GREEN);
                }else{
                    pw.setBackgroundColor(Color.RED);
                    pwchk.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        joinbtn=(Button)findViewById(R.id.join);
        joinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().length()==0){
                    Toast.makeText(JoinActivity.this, "Name을 입력하세요.", Toast.LENGTH_SHORT).show();
                    name.requestFocus();
                    return;
                }
                if(email.getText().toString().length()==0){
                    Toast.makeText(JoinActivity.this, "Email을 입력하세요.", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                    return;
                }
                if(pw.getText().toString().length()==0){
                    Toast.makeText(JoinActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    pw.requestFocus();
                    return;
                }
                if(pwchk.getText().toString().length()==0){
                    Toast.makeText(JoinActivity.this, "비밀번호 확인을 입력하세요.", Toast.LENGTH_SHORT).show();
                    pwchk.requestFocus();
                    return;
                }
                if(!pw.getText().toString().equals(pwchk.getText().toString())){
                    Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    pw.setText("");
                    pwchk.setText("");
                    pw.requestFocus();
                    return;
                }

                if(!mBtn.isChecked()&&!fmBtn.isChecked()){
                    Toast.makeText(JoinActivity.this, "성별을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return ;
                }

                int rb = rdGroup.getCheckedRadioButtonId();

                String sex=null;
                switch(rb){
                    case R.id.maleRadio:
                        sex = mBtn.getText().toString();
                        break;
                    case R.id.femaleRadio:
                        sex = fmBtn.getText().toString();
                        break;
                }
                String sName = name.getText().toString();
                String sId = email.getText().toString();
                String sPw = pw.getText().toString();
                String sSex = sex;


                sql = "SELECT id FROM " + helper.tableName + " WHERE id = '"+ sId + "'";
                cursor = db.rawQuery(sql, null);


                if(cursor.getCount()!=0){
                    Toast toast = Toast.makeText(JoinActivity.this, "존재하는 아이디입니다.",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    helper.insertUser(db,sId,sPw,sName, sSex);
                    Toast toast = Toast.makeText(JoinActivity.this, "가입이 완료되었습니다. 로그인을 해주세요.",
                            Toast.LENGTH_SHORT);
                    toast.show();


                    Intent result = new Intent();
                    result.putExtra("email", sId);

                    setResult(RESULT_OK,result);
                    finish();

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
