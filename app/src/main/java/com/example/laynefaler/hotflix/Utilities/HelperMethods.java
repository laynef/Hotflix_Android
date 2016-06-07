package com.example.laynefaler.hotflix.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.laynefaler.hotflix.DataTypes.MovieData;

import java.util.ArrayList;

/**
 * Created by laynefaler on 6/5/16.
 */
public class HelperMethods {

    public static ArrayList<MovieData> loadFavoriteMovies(Context context) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return null;
    }


}
