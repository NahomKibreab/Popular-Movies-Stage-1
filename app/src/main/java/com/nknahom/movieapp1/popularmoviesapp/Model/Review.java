package com.nknahom.movieapp1.popularmoviesapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {
    String review_author;
    String review_content;
    String review_url;

    public Review(String review_author, String review_content, String review_url) {
        this.review_author = review_author;
        this.review_content = review_content;
        this.review_url = review_url;
    }

    protected Review(Parcel in) {
        review_author = in.readString();
        review_content = in.readString();
        review_url = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getReview_author() {
        return review_author;
    }

    public String getReview_content() {
        return review_content;
    }

    public String getReview_url() {
        return review_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(review_author);
        dest.writeString(review_content);
        dest.writeString(review_url);
    }
}
