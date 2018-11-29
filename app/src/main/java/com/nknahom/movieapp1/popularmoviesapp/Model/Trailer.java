package com.nknahom.movieapp1.popularmoviesapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailer implements Parcelable {
    private String trailer_name;
    private String trailer_key;

    public Trailer(){}

    public Trailer (String trailer_name, String trailer_key){
        this.trailer_name = trailer_name;
        this.trailer_key = trailer_key;
    }

    public String getTrailer_key() {
        return trailer_key;
    }

    public void setTrailer_key(String trailer_key) {
        this.trailer_key = trailer_key;
    }

    public String getTrailer_name() {
        return trailer_name;
    }

    public void setTrailer_name(String trailer_name) {
        this.trailer_name = trailer_name;
    }
    private Trailer(Parcel in){
        trailer_name = in.readString();
        trailer_key = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(trailer_name);
        parcel.writeString(trailer_key);
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel parcel) {
            return new Trailer(parcel);
        }

        @Override
        public Trailer[] newArray(int i) {
            return new Trailer[i];
        }

    };

}
