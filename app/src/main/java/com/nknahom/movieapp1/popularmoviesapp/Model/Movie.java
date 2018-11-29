package com.nknahom.movieapp1.popularmoviesapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private int movie_id;
    private String backdrop_path;
    private String original_title;
    private String poster_path;
    private String title;
    private String release_date;
    private String vote_average;
    private String popularity;
    private String original_language;
    private String movie_overview;

    public Movie() {

    }

    public Movie(int movie_id, String backdrop_path, String original_title, String poster_path, String title, String release_date, String vote_average, String popularity, String original_language, String movie_overview) {
        this.movie_id = movie_id;
        this.backdrop_path = backdrop_path;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.title = title;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.original_language = original_language;
        this.movie_overview = movie_overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getMovie_overview() {
        return movie_overview;
    }

    public void setMovie_overview(String movie_overview) {
        this.movie_overview = movie_overview;
    }

    private Movie(Parcel in){
        movie_id = in.readInt();
        backdrop_path = in.readString();
        original_title = in.readString();
        poster_path = in.readString();
        title = in.readString();
        release_date = in.readString();
        vote_average = in.readString();
        popularity = in.readString();
        original_language = in.readString();
        movie_overview = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(movie_id);
        parcel.writeString(backdrop_path);
        parcel.writeString(original_title);
        parcel.writeString(poster_path);
        parcel.writeString(title);
        parcel.writeString(release_date);
        parcel.writeString(vote_average);
        parcel.writeString(popularity);
        parcel.writeString(original_language);
        parcel.writeString(movie_overview);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

}
