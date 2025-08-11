package com.merengones.wiki.models;

public class Commit {
    private String message;
    private String author;
    private String date;

    public Commit(String message, String author, String date) {
        this.message = message;
        this.author = author;
        this.date = date;
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }
}