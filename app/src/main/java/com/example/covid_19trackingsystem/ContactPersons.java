package com.example.covid_19trackingsystem;

public class ContactPersons {

private String date, time ,devicename ,macaddress  , myMac ;


    public ContactPersons() {
    }

    public ContactPersons(String date, String time, String devicename, String macaddress, String myMac) {
        this.date = date;
        this.time = time;
        this.devicename = devicename;
        this.macaddress = macaddress;

        this.myMac = myMac;
    }



    public String getMymacc() {
        return myMac;
    }

    public void setMymacc(String myMac) {
        this.myMac = myMac;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getMacaddress() {
        return macaddress;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }
}
