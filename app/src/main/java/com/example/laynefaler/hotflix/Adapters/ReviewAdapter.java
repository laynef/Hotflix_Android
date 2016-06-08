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
 * Created by laynefaler on 5/26/16.
 */
public class ReviewAdapter extends CursorAdapter {

    public ReviewAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_review, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String author = cursor.getString(MovieDetailFragment.COL_AUTHOR);
        viewHolder.authorTextView.setText(author);

        String content = cursor.getString(MovieDetailFragment.COL_CONTENT);
        viewHolder.contentTextView.setText(content);

    }

    public static class ViewHolder {
        public static TextView authorTextView;
        public static TextView contentTextView;

        public ViewHolder(View view) {
            authorTextView = (TextView) view.findViewById(R.id.review_author);
            contentTextView = (TextView) view.findViewById(R.id.review_content);
        }
    }

}
