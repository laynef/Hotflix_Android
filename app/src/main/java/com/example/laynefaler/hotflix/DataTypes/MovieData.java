package com.example.laynefaler.hotflix.DataTypes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by laynefaler on 5/23/16.
 */
public class MovieData implements Parcelable {

    String imagePath;
    String overview;
    String title;
    String date;
    String rating;
    String pop;
    String id;

    public MovieData(String imagePath, String overview, String title, String date, String rating, String pop, String id) {
        this.imagePath = imagePath;
        this.overview = overview;
        this.title = title;
        this.date = date;
        this.rating = rating;
        this.pop = pop;
        this.id = id;
    }

    private MovieData(Parcel tmdb) {
        imagePath = tmdb.readString();
        overview = tmdb.readString();
        title = tmdb.readString();
        date = tmdb.readString();
        rating = tmdb.readString();
        pop = tmdb.readString();
        id = tmdb.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getImagePath() {return imagePath;}
    public void setImagePath(String imagePath) {this.imagePath = imagePath;}
    public String getOverview() {return  overview;}
    public void setOverview(String overview) {this.overview = overview;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}
    public String getRating() {return rating;}
    public void setRating(String rating) {this.rating = rating;}
    public String getPop() {return pop;}
    public void setPop(String pop) {this.pop = pop;}
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imagePath);
        dest.writeString(overview);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(rating);
        dest.writeString(pop);
        dest.writeString(id);
    }

    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel source) {
            return new MovieData(source);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
}
