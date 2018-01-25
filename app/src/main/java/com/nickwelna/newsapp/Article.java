package com.nickwelna.newsapp;

public class Article {

    private String title;
    private String section;
    private String author;
    private String date;
    private String articleUrl;

    public Article(String section, String title, String author, String date, String articleUrl) {

        this.section = section;
        this.title = title;
        this.author = author;
        this.date = date;
        this.articleUrl = articleUrl;

    }

    public String getTitle() {

        return title;

    }

    public String getAuthor() {

        return author;

    }

    public String getArticleUrl() {

        return articleUrl;

    }

    public String getSection() {

        return section;

    }

    public String getDate() {

        return date;

    }

}
