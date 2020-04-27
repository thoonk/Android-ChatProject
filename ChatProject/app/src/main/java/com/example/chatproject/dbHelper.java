package com.example.chatproject;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbHelper extends SQLiteOpenHelper {

    public static final String tableName = "Users";

    public dbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name , factory, version);
    }

    @Override
    public  void onCreate(SQLiteDatabase db){
        Log.i("tag", "db생성_db가 없을때만 최초로 실행함");
        createTable(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }

    public void createTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + tableName + "(id text, pw text, name text, sex text)";
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
        }
    }
    public void insertUser(SQLiteDatabase db, String id, String pw, String name, String sex){
        Log.i("tag","회원가입을 했을 때 실행함");
        db.beginTransaction();
        try{
            String sql = " INSERT INTO " + tableName + "(id,pw,name,sex)" +"VALUES('" + id +"', '" + pw + "','" + name + "', '" + sex +"')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }


}
