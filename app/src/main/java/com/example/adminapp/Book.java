package com.example.adminapp;

public class Book {

    private String bookname;
    private String authorname;
    private String edition;
    private String isdn;
    private String preface;
    private String yop;

    public Book(String bookname, String authorname, String edition, String isdn, String preface, String yop){
        this.bookname = bookname;
        this.authorname = authorname;
        this.edition = edition;
        this.isdn = isdn;
        this.preface = preface;
        this.yop = yop;
    }

    public Book(){

    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getPreface() {
        return preface;
    }

    public void setPreface(String preface) {
        this.preface = preface;
    }

    public String getYop() {
        return yop;
    }

    public void setYop(String yop) {
        this.yop = yop;
    }



}
