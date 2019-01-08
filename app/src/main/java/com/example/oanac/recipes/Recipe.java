package com.example.oanac.recipes;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class Recipe implements Parcelable {
    public String id,
                  title,
                  subTitle,
                  publisher,
                  publishedDate,
                  description,
                  authors;

    public Recipe(String id, String title, String subTitle, String publisher, String publishedDate, String[] authors, String description) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.authors = TextUtils.join(", ", authors);
        this.description = description;
    }

    protected Recipe(Parcel in) {
        id = in.readString();
        title = in.readString();
        subTitle = in.readString();
        publisher = in.readString();
        publishedDate = in.readString();
        authors = in.readString();
        description = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(publisher);
        dest.writeString(publishedDate);
        dest.writeString(authors);
        dest.writeString(description);
    }
}
