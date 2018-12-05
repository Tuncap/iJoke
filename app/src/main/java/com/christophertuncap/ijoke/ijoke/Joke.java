package com.christophertuncap.ijoke.ijoke;

public class Joke {
    private String date;
    private String verbiage;
    private String title;
    private String hyperlink;

    public Joke(String title, String date, String verbiage) {
        this.title = title;
        this.date = date;
        this.verbiage = verbiage;
    }

    public Joke(String title, String date, String verbiage, String hyperlink) {
        this.title = title;
        this.date = date;
        this.verbiage = verbiage;
        this.hyperlink = hyperlink;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getVerbiage() {
        return verbiage;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setVerbiage(String verbiage) {
        this.verbiage = verbiage;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }


    @Override
    public String toString() {
        return title + "\n" + date;
    }
}