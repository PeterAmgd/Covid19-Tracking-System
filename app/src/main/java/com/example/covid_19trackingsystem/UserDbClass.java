package com.example.covid_19trackingsystem;

public class UserDbClass {
    private String Firstname, Lastname, Email, Password, PhoneNumber, Age, Address,Myuserid,MyMacAddress;

    public UserDbClass(){}

        public UserDbClass(String firstname, String lastname, String email, String password, String phoneNumber, String age, String address,String myuserid,String mymacaddress) {
        Firstname = firstname;
        Lastname = lastname;
        Email = email;
        Password = password;
        PhoneNumber = phoneNumber;
        Age = age;
        Address = address;
        Myuserid= myuserid;
        MyMacAddress= mymacaddress;
    }

    public String getMyMacAddress() {
        return MyMacAddress;
    }

    public void setMyMacAddress(String myMacAddress) {
        MyMacAddress = myMacAddress;
    }

    public String getMyuserid() {
        return Myuserid;
    }

    public void setMyuserid(String myuserid) {
        Myuserid = myuserid;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
