package com.example.laynefaler.hotflix.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.laynefaler.hotflix.DataTypes.TrailerData;
import com.example.laynefaler.hotflix_android.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by laynefaler on 5/28/16.
 */
public class TrailerAdapter extends ArrayAdapter<TrailerData> {

    public ArrayList<TrailerData> trailerDataArrayList;

    public void updateTrailerData(ArrayList<TrailerData> newData) {
        this.trailerDataArrayList = newData;
        notifyDataSetChanged();
    }

    public TrailerAdapter(Context context, ArrayList<TrailerData> trailer) {
        super(context, 0, trailer);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrailerData trailerData = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_trailer, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.playTrailerButton);
        Picasso.with(getContext()).load(trailerData.getTrailerThumbnail()).into(imageView);

        TextView contentTextView = (TextView) convertView.findViewById(R.id.trailer_title_textview);
        contentTextView.setText(trailerData.getTrailerTitle());

        return convertView;
    }

}
