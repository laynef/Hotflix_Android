package com.example.laynefaler.hotflix.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.laynefaler.hotflix.Data.MovieContract.MovieEntry;
import com.example.laynefaler.hotflix.Data.MovieContract.TrailerEntry;
import com.example.laynefaler.hotflix.Data.MovieContract.ReviewEntry;

/**
 * Created by laynefaler on 5/31/16.
 */
public class MovieDBContract extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "movie.db";

    public MovieDBContract(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
        MovieEntry._ID + " INTEGER PRIMARY KEY," +
                MovieEntry.COLUMN_MOVIE_ID + " INTEGER, " +
                MovieEntry.COLUMN_IS_ADULT + " TEXT, " +
                MovieEntry.COLUMN_BACK_DROP_PATH + " TEXT, " +
                MovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT, " +
                MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT," +
                MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
                MovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +
                MovieEntry.COLUMN_POSTER_PATH + " TEXT, " +
                MovieEntry.COLUMN_POPULARITY + " REAL, " +
                MovieEntry.COLUMN_TITLE + " TEXT, " +
                MovieEntry.COLUMN_IS_VIDEO + " TEXT, " +
                MovieEntry.COLUMN_VOTE_AVERAGE + " FLOAT, " +
                MovieEntry.COLUMN_VOTE_COUNT + " INTEGER, " +
                MovieEntry.COLUMN_DATE + " INTEGER, " +
                MovieEntry.COLUMN_RUNTIME + " REAL, " +
                MovieEntry.COLUMN_STATUS + " TEXT " +
                " );";

        final String SQL_CREATE_TRAILER_TABLE = "CREATE TABLE " + MovieContract.TrailerEntry.TABLE_NAME + " (" +
                MovieContract.TrailerEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContract.TrailerEntry.COLUMN_MOVIE_ID + " INTEGER, " +
                MovieContract.TrailerEntry.COLUMN_TRAILER_ID + " TEXT, " +
                MovieContract.TrailerEntry.COLUMN_ISO_369_1 + " TEXT, " +
                MovieContract.TrailerEntry.COLUMN_KEY + " TEXT, " +
                MovieContract.TrailerEntry.COLUMN_NAME + " TEXT, " +
                MovieContract.TrailerEntry.COLUMN_SITE + " TEXT, " +
                MovieContract.TrailerEntry.COLUMN_SIZE + " TEXT, " +
                MovieContract.TrailerEntry.COLUMN_TYPE + " TEXT, " +
                MovieContract.TrailerEntry.COLUMN_DATE + " INTEGER, " +

                // Set up the movie_id column as a foreign key to movie table.
                " FOREIGN KEY (" + MovieContract.TrailerEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                MovieEntry.TABLE_NAME + " (" + MovieEntry.COLUMN_MOVIE_ID + ") " +

                " UNIQUE (" + MovieContract.TrailerEntry.COLUMN_MOVIE_ID + ", " +
                MovieContract.TrailerEntry.COLUMN_TRAILER_ID + ") ON CONFLICT REPLACE);";


        final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE " + MovieContract.ReviewEntry.TABLE_NAME + " (" +
                MovieContract.ReviewEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContract.ReviewEntry.COLUMN_MOVIE_ID + " INTEGER, " +
                MovieContract.ReviewEntry.COLUMN_REVIEW_ID + " TEXT, " +
                MovieContract.ReviewEntry.COLUMN_AUTHOR + " TEXT, " +
                MovieContract.ReviewEntry.COLUMN_CONTENT + " TEXT, " +
                MovieContract.ReviewEntry.COLUMN_URL + " TEXT, " +
                MovieContract.ReviewEntry.COLUMN_DATE + " INTEGER, " +

                // Set up the movie_id column as a foreign key to movie table.
                " FOREIGN KEY (" + MovieContract.ReviewEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                MovieEntry.TABLE_NAME + " (" + MovieEntry.COLUMN_MOVIE_ID + ") " +

                " UNIQUE (" + MovieContract.ReviewEntry.COLUMN_MOVIE_ID + ", " +
                MovieContract.ReviewEntry.COLUMN_REVIEW_ID + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TRAILER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_REVIEW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TrailerEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ReviewEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
