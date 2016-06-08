package com.example.laynefaler.hotflix.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.laynefaler.hotflix.Fragments.MovieDetailFragment;
import com.example.laynefaler.hotflix_android.R;

/**
 * Created by laynefaler on 5/28/16.
 */
public class TrailerAdapter extends CursorAdapter {

    public TrailerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_trailer, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String trailer = cursor.getString(MovieDetailFragment.COL_NAME);
        viewHolder.trailerTextView.setText(trailer);

    }

    public static class ViewHolder {
        public static TextView trailerTextView;

        public ViewHolder(View view) {
            trailerTextView = (TextView) view.findViewById(R.id.trailer_title_textview);
        }
    }



}
