package com.example.adminapp;

public class ProfileUser {

    String name,email,phone,dob;

    public ProfileUser(){

    }

    public ProfileUser(String name, String email, String phone, String dob){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
