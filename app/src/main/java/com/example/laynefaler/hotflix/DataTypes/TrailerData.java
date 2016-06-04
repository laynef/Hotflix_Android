package com.example.laynefaler.hotflix.DataTypes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by laynefaler on 5/26/16.
 */
public class TrailerData implements Parcelable {

    private String trailerTitle;
    private String trailerURL;
    private String trailerThumbnail;

    public TrailerData(String trailerTitle, String trailerURL, String trailerThumbnail) {
        this.trailerTitle = trailerTitle;
        this.trailerURL = trailerURL;
        this.trailerThumbnail = trailerThumbnail;
    }

    private TrailerData(Parcel in) {
        trailerTitle = in.readString();
        trailerURL = in.readString();
        trailerThumbnail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trailerTitle);
        dest.writeString(trailerURL);
        dest.writeString(trailerThumbnail);
    }

    public String getTrailerTitle() {
        return trailerTitle;
    }
    public void setTrailerTitle(String trailerTitle) {
        this.trailerTitle = trailerTitle;
    }
    public String getTrailerURL() {
        return trailerURL;
    }
    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }
    public String getTrailerThumbnail() {
        return trailerThumbnail;
    }
    public void  setTrailerThumbnail(String trailerThumbnail) {
        this.trailerThumbnail = trailerThumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrailerData> CREATOR = new Creator<TrailerData>() {

        @Override
        public TrailerData createFromParcel(Parcel parcel) {
            return new TrailerData(parcel);
        }

        @Override
        public TrailerData[] newArray(int i) {
            return new TrailerData[i];
        }
    };
}
