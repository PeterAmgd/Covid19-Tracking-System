package com.example.covid_19trackingsystem;

public class WarningNotification {
    private String msg,senderID,receiverID,date,id;

    public WarningNotification(String msg, String senderID, String receiverID,String date,String id) {
        this.msg = msg;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.date=date;
        this.id=id;
    }

    public WarningNotification() {
    }

    public WarningNotification(String msg, String receiverID) {
        this.msg = msg;
        this.receiverID = receiverID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public WarningNotification(String msg) {
        this.msg = msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }
}
