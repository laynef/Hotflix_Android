package com.example.laynefaler.hotflix.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.laynefaler.hotflix.Fragments.MovieImageFragment;
import com.example.laynefaler.hotflix.Utilities.Utility;
import com.example.laynefaler.hotflix_android.R;
import com.squareup.picasso.Picasso;

public class ImageAdapter extends CursorAdapter {

    public ImageAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public static class ViewHolder {
        public final ImageView imageView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.movie_grid_imageview);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_grid_image, parent, false);
        // or fragment_movie_image which doesn't work either
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String posterPath = cursor.getString(MovieImageFragment.COL_POSTER_PATH);
        String moviePoster = Utility.getImageURL(posterPath);

        Picasso.with(context).load(moviePoster).into(viewHolder.imageView);

    }
}

