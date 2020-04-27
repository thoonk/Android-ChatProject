package com.example.chatproject;

public class ChatData {
    private String userId;
    private String userName;
    private String message;
    private String time;

    public ChatData() { }

    public ChatData(String userName, String userId, String message, String time) {
        this.userName = userName;
        this.userId = userId;
        this.message = message;
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime(){
        return time;
    }

    public String setTime(String time){
        this.time = time;
        return time;
    }
}
