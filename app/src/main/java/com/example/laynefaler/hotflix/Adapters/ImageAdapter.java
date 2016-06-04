package com.example.laynefaler.hotflix.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.laynefaler.hotflix.DataTypes.MovieData;
import com.example.laynefaler.hotflix_android.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends ArrayAdapter<MovieData> {

    ArrayList<MovieData> movieDataList;

    public ImageAdapter(Context context, ArrayList<MovieData> images) {
        super(context, 0, images);
    }

    public void updateData(ArrayList<MovieData> newData) {
        this.movieDataList = newData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieData movieData1 = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_grid_image, parent, false);
        }

        ImageView movieImage = (ImageView) convertView.findViewById(R.id.movie_grid_imageview);
        Picasso.with(getContext()).load(movieData1.getImagePath()).into(movieImage);
        return convertView;
    }
}

