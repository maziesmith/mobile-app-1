package com.example.oanac.recipes;

public class Recipe {
    public String id,
                  title,
                  subTitle,
                  publisher,
                  publishedDate;
    public String[] authors;

    public Recipe(String id, String title, String subTitle, String publisher, String publishedDate, String[] authors) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.authors = authors;
    }
}
