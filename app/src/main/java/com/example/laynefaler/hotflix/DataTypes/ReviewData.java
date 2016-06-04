package com.example.laynefaler.hotflix.DataTypes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by laynefaler on 5/26/16.
 */
public class ReviewData implements Parcelable {

    private String author;
    private String content;

    public ReviewData(String author, String content) {
        this.author = author;
        this.content = content;
    }

    private ReviewData(Parcel in) {
        author = in.readString();
        content = in.readString();
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
    }

    public static final Creator<ReviewData> CREATOR = new Creator<ReviewData>() {
        @Override
        public ReviewData createFromParcel(Parcel parcel) {
            return new ReviewData(parcel);

        }
        @Override
        public ReviewData[] newArray(int i) {
            return new ReviewData[i];
        }
    };
}
