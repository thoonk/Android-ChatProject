package com.example.chatproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class S_item {
    private String date;
    private String content;

    public S_item(){}

    public S_item(String s_date, String s_content)
    {
        this.date = s_date;
        this.content = s_content;
    }

    public String getDate() {
        return date;
    }
    public String getContent() {
        return content;
    }
    public void setDate(String s_date){
        this.date = s_date;
    }
    public void setContent(String s_content){
        this.content = s_content;
    }

}
