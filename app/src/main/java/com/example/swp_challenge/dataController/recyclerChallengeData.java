package com.example.swp_challenge.dataController;

import java.util.Date;

public class recyclerChallengeData {
    private float rating;
    private String content;
    private Date date;

    public float getrRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}